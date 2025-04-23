package main.java.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.file.Path;
import java.text.Collator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;

import main.java.actions_set.EditorActions;
import main.java.actions_set.LanguageActions;
import main.java.components.EditorToolbar;
import main.java.components.Menu;
import main.java.components.StatusBar;
import main.java.listener.NotepadDocumentListener;
import main.java.local.FormLocalizationProvider;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizationProvider;
import main.java.util.Constants;

public class JNotepadPP extends JFrame {

	private final DefaultMultipleDocumentModel tabs;
	private JTextArea editor;
	private final JPanel textEditor;
	private String copiedText;

	private final EditorActions editorActions;
	private final LanguageActions languageActions;

    private StatusBar statusBar;

	private final ILocalizationProvider provider = new FormLocalizationProvider(LocalizationProvider.getProvider(), this);
	private Collator collator;

	public JNotepadPP() {
		initGUI();

		this.getContentPane().setLayout(new BorderLayout());
		this.textEditor = new JPanel(new BorderLayout());
		this.tabs = new DefaultMultipleDocumentModel(this, provider);
		this.editor = this.tabs.getCurrentDocument().getTextComponent();
		this.copiedText = "";

		this.textEditor.add(this.tabs, BorderLayout.CENTER);
		this.getContentPane().add(textEditor, BorderLayout.CENTER);

		this.collator = Collator.getInstance(new Locale("en"));

		this.editorActions = new EditorActions(this, tabs, editor, provider);

		this.languageActions = new LanguageActions(provider);

		createMenu();
		createToolbar();
		createStatusBar();
		addListeners();
	}

	private void initGUI() {
		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();
		setLocation((int) screenWidth / 2 - (Constants.WINDOW_WIDTH / 2), (int) screenHeight / 2 - (Constants.WINDOW_HEIGHT / 2));
		setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setTitle("Notepad++ clone");

	}

	private void createMenu() {
		JMenuBar menuBar = new Menu(editorActions, languageActions, provider);

		this.setJMenuBar(menuBar);
	}

	private void createToolbar() {
        EditorToolbar editorToolbar = new EditorToolbar(editorActions, provider);

		this.textEditor.add(editorToolbar, BorderLayout.PAGE_START);
	}


	private void addListeners() {

		this.collator = Collator.getInstance(new Locale("en"));
		provider.addLocalizationListener(() -> collator = provider.getCollator());

		this.editor.getCaret().addChangeListener(this::setupCaretChangeListener);

		this.editor.getDocument().addDocumentListener(new NotepadDocumentListener(editor, statusBar));

		this.tabs.addChangeListener(e -> {
            JTabbedPane source = (JTabbedPane) e.getSource();
            int counter = 0;
            int index = source.getSelectedIndex();
            Iterator<SingleDocumentModel> iterator = tabs.iterator();
            while (iterator.hasNext()) {
                if (counter == index) {
                    editor = iterator.next().getTextComponent();
                    editor.getCaret().addChangeListener(this::setupCaretChangeListener);
					editorActions.setEditor(editor);
                    setSavePath(tabs.getDocument(index).getFilePath());
                    break;
                }
                counter++;
                iterator.next();
            }

			setSaveOptions(tabs.getDocument(tabs.getSelectedIndex()).isModified());

            if (tabs.getNumberOfDocuments() != 0) {
                statusBar.updateDocumentLength(editor.getText().length());
                statusBar.updateCaretInfo(1, 1, 0);
            } else {
                statusBar.setEmptyInfo();
            }

            editor.getDocument().addDocumentListener(new NotepadDocumentListener(editor, statusBar));
        });

		WindowListener wl = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closingApplication(tabs.getNumberOfDocuments(), true);
			}
		};

		this.addWindowListener(wl);
	}

	private void setupCaretChangeListener(ChangeEvent e) {
		Caret c = (Caret) e.getSource();
		int selectionLength = Math.abs(c.getDot() - c.getMark());
		enableCaretActions(selectionLength != 0);

		int line = 1;
		int column = 1;

		try {
			int caretPosition = editor.getCaretPosition();
			line = editor.getLineOfOffset(caretPosition);
			column = caretPosition - editor.getLineStartOffset(line);
		} catch (BadLocationException ex) {
			System.err.println("Bad location: " + ex.getMessage());
		}

		if (tabs.getNumberOfDocuments() != 0) {
			statusBar.updateCaretInfo(line + 1, column + 1, selectionLength);
		} else {
			statusBar.setEmptyInfo();
		}
	}

	private void createStatusBar() {
		statusBar = new StatusBar(provider);

		this.getContentPane().add(statusBar, BorderLayout.PAGE_END);

		statusBar.startClock();
	}

	public String getCopiedText() {
		return this.copiedText;
	}

	public void setCopiedText(String text) {
		this.copiedText = text;
		editorActions.getPasteAction().setEnabled(true);
	}

	public Collator getCollator() {
		return this.collator;
	}

	public void setSaveOptions(boolean enabled) {
		if(editorActions != null) {
			editorActions.setSaveActionsEnabled(enabled);
		}
	}

	public void setSavePath(Path path) {
		editorActions.setSavePath(path);
	}

	public void closeWindow(SingleDocumentModel model) {
		if (model.isModified()) {
			model.setFirstSave(true);

			int resultOfAsk = askForSave(model);

			if (resultOfAsk == JOptionPane.YES_OPTION) {
				tabs.saveDocument(model, model.getFilePath(), true);
			}
		}
	}

	public void closingApplication(int numberOfDocuments, boolean exitAction) {
		boolean unsavedData = false;
		for (int i = 0; i < tabs.getNumberOfDocuments(); i++) {
			if (tabs.getDocument(i).isModified()) {
				unsavedData = true;
				break;
			}
		}

		if (!unsavedData) {
			System.exit(1);
		}

		String[] options = new String[] { provider.getString("yes"), provider.getString("no"),
				provider.getString("cancel") };

		int result = JOptionPane.showOptionDialog(JNotepadPP.this, provider.getString("unsavedData"),
				provider.getString("warning"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[0]);

		switch (result) {
			case JOptionPane.YES_OPTION: {
				List<SingleDocumentModel> allModels = tabs.getDocuments();
				for (int i = 0; i < allModels.size(); i++) {
					tabs.setSelectedIndex(i);
					if (tabs.getDocument(tabs.getSelectedIndex()).isModified()) {
						allModels.get(i).setFirstSave(true);
						int resultOfAsk = askForSave(allModels.get(i));

						if (resultOfAsk == JOptionPane.YES_OPTION) {
							tabs.saveDocument(allModels.get(i), allModels.get(i).getFilePath(), true);
						}
					}
				}
				if (numberOfDocuments == 1 || exitAction) {
					System.exit(1);
				}
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				break;
			}
			case JOptionPane.NO_OPTION: {
				if (numberOfDocuments == 1 || exitAction) {
					System.exit(1);
				}
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				break;
			}
			case JOptionPane.CANCEL_OPTION: {
				setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				break;
			}

		}
	}

	private int askForSave(SingleDocumentModel model) {
		String[] askForSave = new String[] { provider.getString("yes"), provider.getString("no") };

		return JOptionPane.showOptionDialog(JNotepadPP.this,
				provider.getString("saveDocument") + model.getFilePath().toString() + "?",
				provider.getString("warning"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				askForSave, askForSave[0]);
	}

	private void enableCaretActions(boolean enabled) {
		editorActions.setCaretDependentActionsEnabled(enabled, editor);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}
}
package main.java.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;

import main.java.actions.AscendingSortAction;
import main.java.actions.CloseDocumentAction;
import main.java.actions.CopyDocumentAction;
import main.java.actions.CutDocumentAction;
import main.java.actions.DeLanguageAction;
import main.java.actions.DescendingSortAction;
import main.java.actions.EnLanguageAction;
import main.java.actions.ExitDocumentAction;
import main.java.actions.HrLanguageAction;
import main.java.actions.InfoDocumentAction;
import main.java.actions.NewDocumentAction;
import main.java.actions.OpenDocumentAction;
import main.java.actions.PasteDocumentAction;
import main.java.actions.SaveAsDocumentAction;
import main.java.actions.SaveDocumentAction;
import main.java.actions.ToLowerAction;
import main.java.actions.ToUpperAction;
import main.java.actions.ToggleCaseAction;
import main.java.local.FormLocalizationProvider;
import main.java.local.ILocalizationListener;
import main.java.local.ILocalizationProvider;
import main.java.local.LJButton;
import main.java.local.LJLabel;
import main.java.local.LJMenu;
import main.java.local.LocalizableAction;
import main.java.local.LocalizationProvider;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 700;

	private static final String redDiskette = System.getProperty("user.dir") + "/main/resources/redDiskette.png";
	private static final String hrFlag = System.getProperty("user.dir") + "/main/resources/hr.png";
	private static final String enFlag = System.getProperty("user.dir") + "/main/resources/en.png";
	private static final String deFlag = System.getProperty("user.dir") + "/main/resources/de.png";

	private DefaultMultipleDocumentModel tabs;
	private JTextArea editor;
	private JPanel textEditor;
	private String copiedText;
	private JPanel statusBar;
	private JLabel labelLeft;
	private JPanel labelRight;
	private JLabel info;
	private JLabel timer;
	private String time;
	private SimpleDateFormat dateAndTime;

	private Action newDocumentAction;
	private Action openDocumentAction;
	private Action saveDocumentAction;
	private Action saveAsDocumentAction;
	private Action closeDocumentAction;
	private Action toggleCaseAction;
	private Action toUpperCaseAction;
	private Action toLowerCaseAction;
	private Action copyAction;
	private Action pasteAction;
	private Action cutAction;
	private Action ascendingSortAction;
	private Action descendingSortAction;
	private Action infoAction;
	private Action exitDocumentAction;

	private LocalizableAction hrLanguageAction;
	private LocalizableAction enLanguageAction;
	private LocalizableAction deLanguageAction;

	private JButton newDocumentButton;
	private JButton openDocumentButton;
	private JButton saveDocumentButton;
	private JButton saveAsDocumentButton;
	private JButton toggleCaseButton;
	private JButton toUpperButton;
	private JButton toLowerButton;
	private JButton copyButton;
	private JButton pasteButton;
	private JButton cutButton;
	private JButton infoButton;

	private ILocalizationProvider provider = new FormLocalizationProvider(LocalizationProvider.getProvider(), this);
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

		createActions();
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
		setLocation((int) screenWidth / 2 - (WINDOW_WIDTH / 2), (int) screenHeight / 2 - (WINDOW_HEIGHT / 2));
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setTitle("Notepad++ clone");

	}

	private void createActions() {
		newDocumentAction = new NewDocumentAction(tabs, "new", provider, KeyStroke.getKeyStroke("control N"),
				KeyEvent.VK_N, true);
		
		openDocumentAction = new OpenDocumentAction(tabs, JNotepadPP.this, "open", provider,
				KeyStroke.getKeyStroke("control O"), KeyEvent.VK_O, true);

		saveDocumentAction = new SaveDocumentAction(tabs,
				tabs.getNumberOfDocuments() == 0 ? Paths.get("") : tabs.getCurrentDocument().getFilePath(), "save",
				provider, KeyStroke.getKeyStroke("control S"), KeyEvent.VK_S, false);

		saveAsDocumentAction = new SaveAsDocumentAction(tabs,
				tabs.getNumberOfDocuments() == 0 ? Paths.get("") : tabs.getCurrentDocument().getFilePath(), "saveAs",
				provider, KeyStroke.getKeyStroke("control alt S"), KeyEvent.VK_S, false);

		closeDocumentAction = new CloseDocumentAction(tabs, "close", provider, KeyStroke.getKeyStroke("control W"),
				KeyEvent.VK_W, true);

		toggleCaseAction = new ToggleCaseAction(editor, "toggle", provider, KeyStroke.getKeyStroke("control T"),
				KeyEvent.VK_T, false);

		toUpperCaseAction = new ToUpperAction(editor, "toUpper", provider, KeyStroke.getKeyStroke("control U"),
				KeyEvent.VK_U, false);

		toLowerCaseAction = new ToLowerAction(editor, "toLower", provider, KeyStroke.getKeyStroke("control L"),
				KeyEvent.VK_L, false);

		copyAction = new CopyDocumentAction(editor, this, "copy", provider, KeyStroke.getKeyStroke("control C"),
				KeyEvent.VK_C, false);

		pasteAction = new PasteDocumentAction(editor, this, "paste", provider,
				KeyStroke.getKeyStroke("control shift V"), KeyEvent.VK_V, false);

		cutAction = new CutDocumentAction(editor, this, "cut", provider, KeyStroke.getKeyStroke("control X"),
				KeyEvent.VK_X, false);

		ascendingSortAction = new AscendingSortAction(tabs, "asc", provider, KeyStroke.getKeyStroke("control shift A"),
				KeyEvent.VK_A, false);

		descendingSortAction = new DescendingSortAction(tabs, "dsc", provider,
				KeyStroke.getKeyStroke("control shift D"), KeyEvent.VK_D, false);

		infoAction = new InfoDocumentAction(tabs, "info", provider, KeyStroke.getKeyStroke("control I"), KeyEvent.VK_I,
				true);

		exitDocumentAction = new ExitDocumentAction(this, tabs, "exit", provider, KeyStroke.getKeyStroke("control E"),
				KeyEvent.VK_E, true);

		hrLanguageAction = new HrLanguageAction("hr", provider, null, 0, true);
		enLanguageAction = new EnLanguageAction("en", provider, null, 0, true);
		deLanguageAction = new DeLanguageAction("de", provider, null, 0, true);
	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new LJMenu("file", provider);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitDocumentAction));

		JMenu toolMenu = new LJMenu("tools", provider);
		JMenu changeCase = new LJMenu("changeCase", provider);
		JMenu textMenu = new LJMenu("textMenu", provider);

		changeCase.add(new JMenuItem(toggleCaseAction));
		changeCase.add(new JMenuItem(toUpperCaseAction));
		changeCase.add(new JMenuItem(toLowerCaseAction));

		textMenu.add(new JMenuItem(copyAction));
		textMenu.add(new JMenuItem(pasteAction));
		textMenu.add(new JMenuItem(cutAction));
		toolMenu.add(changeCase);
		toolMenu.add(textMenu);

		menuBar.add(toolMenu);

		JMenu infoMenu = new LJMenu("info", provider);
		infoMenu.add(new JMenuItem(infoAction));

		menuBar.add(infoMenu);

		JMenu sortMenu = new LJMenu("sort", provider);
		JMenuItem ascending = new JMenuItem(ascendingSortAction);
		JMenuItem descending = new JMenuItem(descendingSortAction);
		sortMenu.add(ascending);
		sortMenu.add(descending);

		menuBar.add(sortMenu);

		JMenu languageMenu = new LJMenu("languages", provider);
		JMenuItem hrAction = new JMenuItem(hrLanguageAction);
		JMenuItem enAction = new JMenuItem(enLanguageAction);
		JMenuItem deAction = new JMenuItem(deLanguageAction);
		hrAction.setIcon(new ImageIcon(hrFlag));
		enAction.setIcon(new ImageIcon(enFlag));
		deAction.setIcon(new ImageIcon(deFlag));
		languageMenu.add(hrAction);
		languageMenu.add(enAction);
		languageMenu.add(deAction);

		menuBar.add(languageMenu);

		this.setJMenuBar(menuBar);
	}

	private void createToolbar() {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);

		createButtons();

		toolBar.add(newDocumentButton);
		toolBar.add(openDocumentButton);
		toolBar.add(saveDocumentButton);
		toolBar.add(saveAsDocumentButton);
		toolBar.addSeparator();
		toolBar.add(toggleCaseButton);
		toolBar.add(toUpperButton);
		toolBar.add(toLowerButton);
		toolBar.addSeparator();
		toolBar.add(copyButton);
		toolBar.add(pasteButton);
		toolBar.add(cutButton);
		toolBar.addSeparator();
		toolBar.add(infoButton);

		this.textEditor.add(toolBar, BorderLayout.PAGE_START);
	}

	private void createButtons() {
		newDocumentButton = new LJButton(newDocumentAction, "new", provider);
		openDocumentButton = new LJButton(openDocumentAction, "open", provider);
		saveDocumentButton = new LJButton(saveDocumentAction, "save", provider);
		saveAsDocumentButton = new LJButton(saveAsDocumentAction, "saveAs", provider);
		toggleCaseButton = new LJButton(toggleCaseAction, "toggle", provider);
		toUpperButton = new LJButton(toUpperCaseAction, "toUpper", provider);
		toLowerButton = new LJButton(toLowerCaseAction, "toLower", provider);
		copyButton = new LJButton(copyAction, "copy", provider);
		pasteButton = new LJButton(pasteAction, "paste", provider);
		cutButton = new LJButton(cutAction, "cut", provider);
		infoButton = new LJButton(infoAction, "info", provider);
	}

	private void addListeners() {

		this.collator = Collator.getInstance(new Locale("en"));
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				collator = provider.getCollator();
			}
		});

		this.editor.getCaret().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
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
					ex.printStackTrace();
				}

				if (tabs.getNumberOfDocuments() != 0) {
					info.setText(String.format(provider.getString("infoLine"), line + 1, column + 1, selectionLength));
				} else {
					info.setText(provider.getString("infoLineEmpty"));
				}
			}
		});

		this.editor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				labelLeft.setText(String.format(provider.getString("length"), editor.getText().length()));
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				labelLeft.setText(String.format(provider.getString("length"), editor.getText().length()));
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				labelLeft.setText(String.format(provider.getString("length"), editor.getText().length()));
			}
		});

		this.tabs.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane source = (JTabbedPane) e.getSource();
				int counter = 0;
				int index = source.getSelectedIndex();
				Iterator<SingleDocumentModel> iterator = tabs.iterator();
				while (iterator.hasNext()) {
					if (counter == index) {
						editor = iterator.next().getTextComponent();
						editor.getCaret().addChangeListener(new ChangeListener() {
							@Override
							public void stateChanged(ChangeEvent e) {
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
									ex.printStackTrace();
								}

								if (tabs.getNumberOfDocuments() != 0) {
									info.setText(String.format(provider.getString("infoLine"), line + 1, column + 1,
											selectionLength));
								} else {
									info.setText(provider.getString("infoLineEmpty"));
								}
							}
						});
						setSavePath(tabs.getDocument(index).getFilePath());
						break;
					}
					counter++;
					iterator.next();
				}

				if (!isItSaved(tabs.getSelectedIndex())) {
					setSaveOptions(true);
				} else {
					setSaveOptions(false);
				}

				if (tabs.getNumberOfDocuments() != 0) {
					labelLeft.setText(String.format(provider.getString("length"), editor.getText().length()));
					info.setText(String.format(provider.getString("infoLine"), 1, 1, 0));
				} else {
					labelLeft.setText(provider.getString("lengthEmpty"));
					info.setText(provider.getString("infoLineEmpty"));
				}

				editor.getDocument().addDocumentListener(new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent e) {
						labelLeft.setText(String.format(provider.getString("length"), editor.getText().length()));
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						labelLeft.setText(String.format(provider.getString("length"), editor.getText().length()));
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						labelLeft.setText(String.format(provider.getString("length"), editor.getText().length()));
					}
				});
			}
		});

		WindowListener wl = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closingApplication(tabs.getNumberOfDocuments(), false);
			}
		};

		this.addWindowListener(wl);
	}

	private void createStatusBar() {
		statusBar = new JPanel(new GridLayout(1, 2));
		labelLeft = new LJLabel("lengthEmpty", provider);
		info = new LJLabel("infoLineEmpty", provider);

		dateAndTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		timer = new JLabel();
		timer.setHorizontalAlignment(SwingConstants.RIGHT);

		labelRight = new JPanel(new GridLayout(1, 2));

		statusBar.add(labelLeft);
		labelRight.add(info);
		labelRight.add(timer);
		statusBar.add(labelRight);

		this.getContentPane().add(statusBar, BorderLayout.PAGE_END);

		getTime();
	}

	private void getTime() {
		updateTime();

		Thread t = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (Exception ex) {
				}
				SwingUtilities.invokeLater(() -> {
					updateTime();
				});
			}
		});
		t.setDaemon(true);
		t.start();
	}

	private void updateTime() {
		time = dateAndTime.format(new Date());
		timer.setText(time);
		repaint();
	}

	public String getCopiedText() {
		return this.copiedText;
	}

	public void setCopiedText(String text) {
		this.copiedText = text;
		pasteAction.setEnabled(true);
	}

	public Collator getCollator() {
		return this.collator;
	}

	public void setSaveOptions(boolean enabled) {
		if (saveDocumentAction != null && saveAsDocumentAction != null) {
			saveDocumentAction.setEnabled(enabled);
			saveAsDocumentAction.setEnabled(enabled);
		}
	}

	public void setSavePath(Path path) {
		((SaveDocumentAction) this.saveDocumentAction).setPath(path);
	}

	public void closeWindow(SingleDocumentModel model, int index) {
		if (!isItSaved(index)) {
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
			if (!isItSaved(i)) {
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
				if (!isItSaved(i)) {
					allModels.get(i).setFirstSave(true);
					int resultOfAsk = askForSave(allModels.get(i));

					if (resultOfAsk == JOptionPane.YES_OPTION) {
						tabs.saveDocument(allModels.get(i), allModels.get(i).getFilePath(), true);
					}
				}
			}
			if (numberOfDocuments == 1 || exitAction) {
				System.exit(0);
			}
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			break;
		}
		case JOptionPane.NO_OPTION: {
			if (numberOfDocuments == 1 || exitAction) {
				System.exit(0);
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

	private boolean isItSaved(int index) {
		return !tabs.getIconAt(index).toString().substring(tabs.getIconAt(index).toString().lastIndexOf("\\") + 1)
				.equals(redDiskette.substring(redDiskette.lastIndexOf("/") + 1));
	}

	private int askForSave(SingleDocumentModel model) {
		String[] askForSave = new String[] { provider.getString("yes"), provider.getString("no") };

		return JOptionPane.showOptionDialog(JNotepadPP.this,
				provider.getString("saveDocument") + model.getFilePath().toString() + "?",
				provider.getString("warning"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				askForSave, askForSave[0]);
	}

	private void enableCaretActions(boolean enabled) {
		toggleCaseAction.setEnabled(enabled);
		toUpperCaseAction.setEnabled(enabled);
		toLowerCaseAction.setEnabled(enabled);
		copyAction.setEnabled(enabled);
		cutAction.setEnabled(enabled);
		ascendingSortAction.setEnabled(enabled);
		descendingSortAction.setEnabled(enabled);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}
}

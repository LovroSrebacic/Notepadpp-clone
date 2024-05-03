package main.java.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Caret;

import main.java.actions.AscendingSortAction;
import main.java.actions.CloseDocumentAction;
import main.java.actions.CopyDocumentAction;
import main.java.actions.CutDocumentAction;
import main.java.actions.DescendingSortAction;
import main.java.actions.ExitDocumentAction;
import main.java.actions.InfoDocumentAction;
import main.java.actions.NewDocumentAction;
import main.java.actions.OpenDocumentAction;
import main.java.actions.PasteDocumentAction;
import main.java.actions.SaveAsDocumentAction;
import main.java.actions.SaveDocumentAction;
import main.java.actions.ToLowerAction;
import main.java.actions.ToUpperAction;
import main.java.actions.ToggleCaseAction;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 700;

	private static final String redDiskette = "./src/main/resources/redDiskette.png";

	private DefaultMultipleDocumentModel tabs;
	private JTextArea editor;
	private JPanel textEditor;
	private String copiedText;

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

	private Collator collator;

	public JNotepadPP() {
		initGUI();

		this.getContentPane().setLayout(new BorderLayout());
		this.textEditor = new JPanel(new BorderLayout());
		this.tabs = new DefaultMultipleDocumentModel(this);
		this.editor = this.tabs.getCurrentDocument().getTextComponent();
		this.copiedText = "";

		this.textEditor.add(this.tabs, BorderLayout.CENTER);
		this.getContentPane().add(textEditor, BorderLayout.CENTER);

		this.collator = Collator.getInstance(new Locale("en"));

		createActions();
		createMenu();
		createToolbar();
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
		newDocumentAction = new NewDocumentAction(tabs);
		newDocumentAction.putValue(Action.NAME, "New");
		newDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to create new file.");
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		openDocumentAction = new OpenDocumentAction(tabs, JNotepadPP.this);
		openDocumentAction.putValue(Action.NAME, "Open");
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file.");
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		saveDocumentAction = new SaveDocumentAction(tabs,
				tabs.getNumberOfDocuments() == 0 ? Paths.get("") : tabs.getCurrentDocument().getFilePath());
		saveDocumentAction.putValue(Action.NAME, "Save");
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save existing file.");
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.setEnabled(false);

		saveAsDocumentAction = new SaveAsDocumentAction(tabs,
				tabs.getNumberOfDocuments() == 0 ? Paths.get("") : tabs.getCurrentDocument().getFilePath());
		saveAsDocumentAction.putValue(Action.NAME, "Save As");
		saveAsDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save existing file in any format.");
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAsDocumentAction.setEnabled(false);

		closeDocumentAction = new CloseDocumentAction(tabs);
		closeDocumentAction.putValue(Action.NAME, "Close [W]");
		closeDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to close current document.");
		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);

		toggleCaseAction = new ToggleCaseAction(editor);
		toggleCaseAction.putValue(Action.NAME, "ToggleCase");
		toggleCaseAction.putValue(Action.SHORT_DESCRIPTION, "Used to toggle selected text.");
		toggleCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		toggleCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleCaseAction.setEnabled(false);

		toUpperCaseAction = new ToUpperAction(editor);
		toUpperCaseAction.putValue(Action.NAME, "ToUpper");
		toUpperCaseAction.putValue(Action.SHORT_DESCRIPTION, "Used to change selected text to upper case.");
		toUpperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUpperCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		toUpperCaseAction.setEnabled(false);

		toLowerCaseAction = new ToLowerAction(editor);
		toLowerCaseAction.putValue(Action.NAME, "ToLower");
		toLowerCaseAction.putValue(Action.SHORT_DESCRIPTION, "Used to change selected text to lower case.");
		toLowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		toLowerCaseAction.setEnabled(false);

		copyAction = new CopyDocumentAction(editor, this);
		copyAction.putValue(Action.NAME, "Copy");
		copyAction.putValue(Action.SHORT_DESCRIPTION, "Used to copy selected text.");
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.setEnabled(false);

		pasteAction = new PasteDocumentAction(editor, this);
		pasteAction.putValue(Action.NAME, "Paste [V]");
		pasteAction.putValue(Action.SHORT_DESCRIPTION, "Used to paste text into document.");
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteAction.setEnabled(false);

		cutAction = new CutDocumentAction(editor, this);
		cutAction.putValue(Action.NAME, "Cut [X]");
		cutAction.putValue(Action.SHORT_DESCRIPTION, "Used to cut selected text.");
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutAction.setEnabled(false);

		ascendingSortAction = new AscendingSortAction(tabs);
		ascendingSortAction.putValue(Action.NAME, "Ascending");
		ascendingSortAction.putValue(Action.SHORT_DESCRIPTION, "Sort selected lines in ascending order.");
		ascendingSortAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift A"));
		ascendingSortAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		ascendingSortAction.setEnabled(false);

		descendingSortAction = new DescendingSortAction(tabs);
		descendingSortAction.putValue(Action.NAME, "Descending");
		descendingSortAction.putValue(Action.SHORT_DESCRIPTION, "Sort selected lines in descending order.");
		descendingSortAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift D"));
		descendingSortAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		descendingSortAction.setEnabled(false);

		infoAction = new InfoDocumentAction(tabs);
		infoAction.putValue(Action.NAME, "Info");
		infoAction.putValue(Action.SHORT_DESCRIPTION, "Get info about opened document.");
		infoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		infoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);

		exitDocumentAction = new ExitDocumentAction(this, tabs);
		exitDocumentAction.putValue(Action.NAME, "Exit");
		exitDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Exit application.");
		exitDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exitDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitDocumentAction));

		JMenu toolMenu = new JMenu("Tools");
		JMenu changeCase = new JMenu("Change case");
		JMenu textMenu = new JMenu("Text Menu");

		changeCase.add(new JMenuItem(toggleCaseAction));
		changeCase.add(new JMenuItem(toUpperCaseAction));
		changeCase.add(new JMenuItem(toLowerCaseAction));

		textMenu.add(new JMenuItem(copyAction));
		textMenu.add(new JMenuItem(pasteAction));
		textMenu.add(new JMenuItem(cutAction));
		toolMenu.add(changeCase);
		toolMenu.add(textMenu);

		menuBar.add(toolMenu);

		JMenu infoMenu = new JMenu("Info");
		infoMenu.add(new JMenuItem(infoAction));

		menuBar.add(infoMenu);

		JMenu sortMenu = new JMenu("Sort");
		JMenuItem ascending = new JMenuItem(ascendingSortAction);
		JMenuItem descending = new JMenuItem(descendingSortAction);
		sortMenu.add(ascending);
		sortMenu.add(descending);

		menuBar.add(sortMenu);

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
		newDocumentButton = new JButton(newDocumentAction);
		newDocumentButton.setText("New");
		openDocumentButton = new JButton(openDocumentAction);
		openDocumentButton.setText("Open");
		saveDocumentButton = new JButton(saveDocumentAction);
		saveDocumentButton.setText("Save");
		saveAsDocumentButton = new JButton(saveAsDocumentAction);
		saveAsDocumentButton.setText("Save As");
		toggleCaseButton = new JButton(toggleCaseAction);
		toggleCaseButton.setText("ToggleCase");
		toUpperButton = new JButton(toUpperCaseAction);
		toUpperButton.setText("ToUpper");
		toLowerButton = new JButton(toLowerCaseAction);
		toLowerButton.setText("ToLower");
		copyButton = new JButton(copyAction);
		copyButton.setText("Copy");
		pasteButton = new JButton(pasteAction);
		pasteButton.setText("Paste");
		cutButton = new JButton(cutAction);
		cutButton.setText("Cut");
		infoButton = new JButton(infoAction);
		infoButton.setText("Info");
	}

	private void addListeners() {

		this.editor.getCaret().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Caret c = (Caret) e.getSource();
				int selectionLength = Math.abs(c.getDot() - c.getMark());
				enableCaretActions(selectionLength != 0);
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

		String[] options = new String[] { "Yes", "No", "Cancel" };

		int result = JOptionPane.showOptionDialog(JNotepadPP.this, "There is unsaved data. Do you want to save it?",
				"Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

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
		String[] askForSave = new String[] { "Yes", "No" };

		return JOptionPane.showOptionDialog(JNotepadPP.this,
				"Save file " + model.getFilePath().toString() + "?", "Warning",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, askForSave, askForSave[0]);
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

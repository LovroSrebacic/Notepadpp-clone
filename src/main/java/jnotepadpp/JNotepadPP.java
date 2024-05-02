package main.java.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.java.actions.NewDocumentAction;
import main.java.actions.OpenDocumentAction;
import main.java.actions.SaveAsDocumentAction;
import main.java.actions.SaveDocumentAction;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 700;

	private DefaultMultipleDocumentModel tabs;
	private JTextArea editor;
	private JPanel textEditor;

	private Action newDocumentAction;
	private Action openDocumentAction;
	private Action saveDocumentAction;
	private Action saveAsDocumentAction;

	public JNotepadPP() {
		initGUI();

		this.getContentPane().setLayout(new BorderLayout());
		this.textEditor = new JPanel(new BorderLayout());
		this.tabs = new DefaultMultipleDocumentModel(this);
		this.editor = this.tabs.getCurrentDocument().getTextComponent();
		
		this.editor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				((SaveDocumentAction) saveDocumentAction).setPath(tabs.getCurrentDocument().getFilePath());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				((SaveDocumentAction) saveDocumentAction).setPath(tabs.getCurrentDocument().getFilePath());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				((SaveDocumentAction) saveDocumentAction).setPath(tabs.getCurrentDocument().getFilePath());
			}
		});
		
		this.textEditor.add(this.tabs, BorderLayout.CENTER);
		this.getContentPane().add(textEditor, BorderLayout.CENTER);

		createActions();
		createMenu();
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
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		openDocumentAction = new OpenDocumentAction(tabs, JNotepadPP.this);
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		saveDocumentAction = new SaveDocumentAction(tabs,
				tabs.getNumberOfDocuments() == 0 ? Paths.get("") : tabs.getCurrentDocument().getFilePath());
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		saveAsDocumentAction = new SaveAsDocumentAction(tabs,
				tabs.getNumberOfDocuments() == 0 ? Paths.get("") : tabs.getCurrentDocument().getFilePath());
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));

		this.setJMenuBar(menuBar);
	}

	private void addListeners() {
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
						break;
					}
					counter++;
					iterator.next();
				}

				editor.getDocument().addDocumentListener(new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent e) {
						((SaveDocumentAction) saveDocumentAction).setPath(tabs.getCurrentDocument().getFilePath());
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						((SaveDocumentAction) saveDocumentAction).setPath(tabs.getCurrentDocument().getFilePath());
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						((SaveDocumentAction) saveDocumentAction).setPath(tabs.getCurrentDocument().getFilePath());
					}
				});
			}
		});
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

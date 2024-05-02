package main.java.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import main.java.actions.NewDocumentAction;


public class JNotepadPP extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 700;
	
	private DefaultMultipleDocumentModel tabs;
	private JPanel textEditor;
	
	private Action newDocumentAction;

	public JNotepadPP() {
		initGUI();
		
		this.getContentPane().setLayout(new BorderLayout());
		this.textEditor = new JPanel(new BorderLayout());
		this.tabs = new DefaultMultipleDocumentModel(this);
		this.textEditor.add(this.tabs, BorderLayout.CENTER);
		this.getContentPane().add(textEditor, BorderLayout.CENTER);
		
		createActions();
		createMenu();
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
	}
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(newDocumentAction));
		
		this.setJMenuBar(menuBar);
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

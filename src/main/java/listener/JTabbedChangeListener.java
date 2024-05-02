package main.java.listener;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;
import main.java.jnotepadpp.SingleDocumentModel;

public class JTabbedChangeListener implements ChangeListener{
	
	private DefaultMultipleDocumentModel multipleDocument;
	private JTabbedPane tabs;
	private SingleDocumentModel document;
	
	public JTabbedChangeListener(DefaultMultipleDocumentModel multipleDocument, JTabbedPane tabs) {
		this.multipleDocument = multipleDocument;
		this.tabs = tabs;
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		this.document = (SingleDocumentModel) this.tabs.getSelectedComponent();
		this.multipleDocument.setCurrentDocument(document);
	}

}

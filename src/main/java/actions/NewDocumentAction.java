package main.java.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;

public class NewDocumentAction extends AbstractAction{

private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel documents;
	
	public NewDocumentAction(DefaultMultipleDocumentModel documents) {
		this.documents = documents;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		documents.createNewDocument();
	}

}

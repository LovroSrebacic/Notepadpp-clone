package main.java.actions;

import java.awt.event.ActionEvent;

import java.nio.file.Path;

import javax.swing.AbstractAction;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;

public class SaveDocumentAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel documents;
	private Path path;

	public SaveDocumentAction(DefaultMultipleDocumentModel documents, Path path) {
		this.documents = documents;
		this.path = path;
	}

	public void setPath(Path path) {
		this.path = path;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		documents.saveDocument(documents.getCurrentDocument(), path, false);
	}

}

package main.java.actions;

import java.awt.event.ActionEvent;

import java.nio.file.Path;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class SaveDocumentAction extends LocalizableAction{

	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel documents;
	private Path path;

	public SaveDocumentAction(DefaultMultipleDocumentModel documents, Path path, String key, ILocalizationProvider provider) {
		super(key, provider);
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

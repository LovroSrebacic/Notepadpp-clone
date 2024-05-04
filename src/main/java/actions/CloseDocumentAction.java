package main.java.actions;

import java.awt.event.ActionEvent;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class CloseDocumentAction extends LocalizableAction{

private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel documents;
	
	public CloseDocumentAction(DefaultMultipleDocumentModel documents, String key, ILocalizationProvider provider) {
		super(key, provider);
		this.documents = documents;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		documents.closeDocument(documents.getCurrentDocument());
	}

}

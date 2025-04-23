package main.java.actions;

import java.awt.event.ActionEvent;

import javax.swing.KeyStroke;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class CloseDocumentAction extends LocalizableAction{
	
	private final DefaultMultipleDocumentModel documents;
	
	public CloseDocumentAction(DefaultMultipleDocumentModel documents, String key, ILocalizationProvider provider, KeyStroke keyStroke, int mnemonicKey, boolean enabled) {
		super(key, provider, keyStroke, mnemonicKey, enabled);
		this.documents = documents;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		documents.closeDocument(documents.getCurrentDocument());
	}

}

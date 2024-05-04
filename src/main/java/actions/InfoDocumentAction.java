package main.java.actions;

import java.awt.event.ActionEvent;

import javax.swing.KeyStroke;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class InfoDocumentAction extends LocalizableAction{

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel documents;
	
	public InfoDocumentAction(DefaultMultipleDocumentModel documents, String key, ILocalizationProvider provider, KeyStroke keyStroke, int mnemonicKey, boolean enabled) {
		super(key, provider, keyStroke, mnemonicKey, enabled);
		this.documents = documents;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		documents.getInfo();
	}
}

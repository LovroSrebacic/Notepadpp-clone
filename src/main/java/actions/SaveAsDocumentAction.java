package main.java.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;

import javax.swing.KeyStroke;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class SaveAsDocumentAction extends LocalizableAction{
	
	private final DefaultMultipleDocumentModel documents;
	private final Path path;

	public SaveAsDocumentAction(DefaultMultipleDocumentModel documents, Path path, String key, ILocalizationProvider provider, KeyStroke keyStroke, int mnemonicKey, boolean enabled) {
		super(key, provider, keyStroke, mnemonicKey, enabled);
		this.documents = documents;
		this.path = path;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		documents.setSaveAs(true);
		documents.saveDocument(documents.getCurrentDocument(), path, false);
	}

}

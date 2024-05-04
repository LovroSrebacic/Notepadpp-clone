package main.java.actions;

import java.awt.event.ActionEvent;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;
import main.java.jnotepadpp.JNotepadPP;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class ExitDocumentAction extends LocalizableAction{

	private static final long serialVersionUID = 1L;
	
	private JNotepadPP notepad;
	private DefaultMultipleDocumentModel model;

	public ExitDocumentAction(JNotepadPP notepad, DefaultMultipleDocumentModel model, String key, ILocalizationProvider provider) {
		super(key, provider);
		this.notepad = notepad;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		notepad.closingApplication(model.getNumberOfDocuments(), true);
	}

}

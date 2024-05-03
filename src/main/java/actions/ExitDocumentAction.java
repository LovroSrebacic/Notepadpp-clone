package main.java.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;
import main.java.jnotepadpp.JNotepadPP;


public class ExitDocumentAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private JNotepadPP notepad;
	private DefaultMultipleDocumentModel model;

	public ExitDocumentAction(JNotepadPP notepad, DefaultMultipleDocumentModel model) {
		this.notepad = notepad;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		notepad.closingApplication(model.getNumberOfDocuments(), true);
	}

}

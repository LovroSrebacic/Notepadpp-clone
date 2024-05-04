package main.java.actions;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.text.Caret;

import main.java.jnotepadpp.JNotepadPP;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class PasteDocumentAction extends LocalizableAction{

	private static final long serialVersionUID = 1L;

	private JTextArea textArea;
	private JNotepadPP notepad;
	
	public PasteDocumentAction(JTextArea textArea, JNotepadPP notepad, String key, ILocalizationProvider provider) {
		super(key, provider);
		this.textArea = textArea;
		this.notepad = notepad;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Caret c = textArea.getCaret();
		String newString;
		if((c.getDot() - c.getMark()) == 0) {
			newString = textArea.getText().substring(0, c.getDot());
			newString += notepad.getCopiedText();
			newString += textArea.getText().substring(c.getDot());
		}else {
			if(c.getDot() < c.getMark()) {
				newString = textArea.getText().substring(0, c.getDot());
				newString += notepad.getCopiedText();
				newString += textArea.getText().substring(c.getMark());
			}else {
				newString = textArea.getText().substring(0, c.getMark());
				newString += notepad.getCopiedText();
				newString += textArea.getText().substring(c.getDot());
			}
		}
		
		textArea.setText(newString);
	}
	
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
}

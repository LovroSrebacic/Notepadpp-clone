package main.java.actions;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.Caret;

import main.java.jnotepadpp.JNotepadPP;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class CutDocumentAction extends LocalizableAction{

	private static final long serialVersionUID = 1L;

	private JTextArea textArea;
	private JNotepadPP notepad;
	
	public CutDocumentAction(JTextArea textArea, JNotepadPP notepad, String key, ILocalizationProvider provider, KeyStroke keyStroke, int mnemonicKey, boolean enabled) {
		super(key, provider, keyStroke, mnemonicKey, enabled);
		this.textArea = textArea;
		this.notepad = notepad;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Caret c = textArea.getCaret();
		String newString;
		
		if(c.getDot() < c.getMark()) {
			notepad.setCopiedText(textArea.getText().substring(c.getDot(), c.getMark()));
			newString = textArea.getText().substring(0, c.getDot());
			newString += textArea.getText().substring(c.getMark());
		}else {
			notepad.setCopiedText(textArea.getText().substring(c.getMark(), c.getDot()));
			newString = textArea.getText().substring(0, c.getMark());
			newString += textArea.getText().substring(c.getDot());
		}
		
		textArea.setText(newString);
	}
	
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
}

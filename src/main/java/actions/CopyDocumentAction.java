package main.java.actions;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.Caret;

import main.java.jnotepadpp.JNotepadPP;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class CopyDocumentAction extends LocalizableAction{

	private JTextArea textArea;
	private final JNotepadPP notepad;
	
	public CopyDocumentAction(JTextArea textArea, JNotepadPP notepad, String key, ILocalizationProvider provider, KeyStroke keyStroke, int mnemonicKey, boolean enabled) {
		super(key, provider, keyStroke, mnemonicKey, enabled);
		this.textArea = textArea;
		this.notepad = notepad;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Caret c = textArea.getCaret();
		
		if(c.getDot() < c.getMark()) {
			notepad.setCopiedText(textArea.getText().substring(c.getDot(), c.getMark()));
		}else {
			notepad.setCopiedText(textArea.getText().substring(c.getMark(), c.getDot()));
		}
	}
	
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

}

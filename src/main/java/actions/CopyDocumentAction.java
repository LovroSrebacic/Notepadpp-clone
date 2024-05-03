package main.java.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.text.Caret;

import main.java.jnotepadpp.JNotepadPP;

public class CopyDocumentAction extends AbstractAction{

	private static final long serialVersionUID = 1L;

	private JTextArea textArea;
	private JNotepadPP notepad;
	
	public CopyDocumentAction(JTextArea textArea, JNotepadPP notepad) {
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

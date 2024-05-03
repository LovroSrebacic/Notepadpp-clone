package main.java.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class ToggleCaseAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private JTextArea textArea;

	public ToggleCaseAction(JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Document doc = textArea.getDocument();
		int length = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
		int offset = 0;
		if(length != 0) {
			offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
		}
		
		try {
			String text = doc.getText(offset, length);
			text= changeCase(text);
			doc.remove(offset, length);
			doc.insertString(offset, text, null);
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	
	private String changeCase(String text) {
		char[] znakovi = text.toCharArray();
		for(int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			if(Character.isLowerCase(c)) {
				znakovi[i] = Character.toUpperCase(c);
			} else if(Character.isUpperCase(c)) {
				znakovi[i] = Character.toLowerCase(c);
			}
		}
			return new String(znakovi);
	}
	
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
}

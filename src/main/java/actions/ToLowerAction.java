package main.java.actions;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class ToLowerAction extends LocalizableAction{

	private static final long serialVersionUID = 1L;
	
	private JTextArea textArea;

	public ToLowerAction(JTextArea textArea, String key, ILocalizationProvider provider) {
		super(key, provider);
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
		char[] chars = text.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			char c = chars[i];
			chars[i] = Character.toLowerCase(c);
		}
		return new String(chars);
	}
	
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
}

package main.java.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

import main.java.listener.SingleDocumentListener;

public interface SingleDocumentModel {
	JTextArea getTextComponent();
	Path getFilePath();
	void setFilePath(Path path);
	boolean isModified();
	void setModified(boolean modified);
	void addSingleDocumentListener(SingleDocumentListener l);
	void removeSingleDocumentListener(SingleDocumentListener l);
	boolean getFirstSave();
	void setFirstSave(boolean firstSave);
}

package main.java.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import main.java.listener.SingleDocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel{
	private JTextArea textArea;
	private Path path;
	private boolean modified;
	private List<SingleDocumentListener> listeners;
	private boolean firstSave;
	
	public DefaultSingleDocumentModel(String text, Path path) {
		this.textArea = new JTextArea();
		this.textArea.setText(text);
		this.path = path;
		this.modified = false;
		this.listeners = new ArrayList<>();
		this.firstSave = true;
	}
	@Override
	public JTextArea getTextComponent() {
		return this.textArea;
	}

	@Override
	public Path getFilePath() {
		return this.path;
	}

	@Override
	public void setFilePath(Path path) {
		this.path = path;
	}

	@Override
	public boolean isModified() {
		return this.modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.remove(l);
	}
	
	public List<SingleDocumentListener> getListeners() {
		return this.listeners;
	}
	
	@Override
	public void setFirstSave(boolean firstSave) {
		this.firstSave = firstSave;
	}
	
	@Override
	public boolean getFirstSave() {
		return firstSave;
	}

}

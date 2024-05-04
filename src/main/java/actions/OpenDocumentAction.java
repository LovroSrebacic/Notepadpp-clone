package main.java.actions;

import java.awt.event.ActionEvent;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;
import main.java.jnotepadpp.JNotepadPP;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class OpenDocumentAction extends LocalizableAction{
	
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel documents;
	private JNotepadPP notepad;
	
	public OpenDocumentAction(DefaultMultipleDocumentModel documents, JNotepadPP notepad, String key, ILocalizationProvider provider, KeyStroke keyStroke, int mnemonicKey, boolean enabled) {
		super(key, provider, keyStroke, mnemonicKey, enabled);
		this.notepad = notepad;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if(fc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION){
			return;
		}
		
		File fileName = fc.getSelectedFile();
		Path path = fileName.toPath();
		
		if(!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(
					notepad, 
					"Datoteka " + fileName.getAbsolutePath() + " ne postoji!",
					"Pogreška",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		documents.loadDocument(path);
	}

}

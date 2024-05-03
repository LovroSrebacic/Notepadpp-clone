package main.java.actions;

import java.awt.event.ActionEvent;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;
import main.java.jnotepadpp.JNotepadPP;

public class OpenDocumentAction extends AbstractAction{
	
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel documents;
	private JNotepadPP notepad;
	
	public OpenDocumentAction(DefaultMultipleDocumentModel documents, JNotepadPP notepad) {
		this.documents = documents;
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

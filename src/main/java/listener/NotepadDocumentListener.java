package main.java.listener;

import main.java.components.StatusBar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class NotepadDocumentListener implements DocumentListener {

    private final JTextArea editor;
    private final StatusBar statusBar;

    public NotepadDocumentListener(JTextArea editor, StatusBar statusBar) {
        this.editor = editor;
        this.statusBar = statusBar;
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        statusBar.updateDocumentLength(editor.getText().length());
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        statusBar.updateDocumentLength(editor.getText().length());
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        statusBar.updateDocumentLength(editor.getText().length());
    }
}

package main.java.actions_set;

import main.java.actions.*;
import main.java.jnotepadpp.DefaultMultipleDocumentModel;
import main.java.jnotepadpp.JNotepadPP;
import main.java.local.ILocalizationProvider;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EditorActions {
    private Action newDocumentAction;
    private Action openDocumentAction;
    private Action saveDocumentAction;
    private Action saveAsDocumentAction;
    private Action closeDocumentAction;
    private Action toggleCaseAction;
    private Action toUpperCaseAction;
    private Action toLowerCaseAction;
    private Action copyAction;
    private Action pasteAction;
    private Action cutAction;
    private Action ascendingSortAction;
    private Action descendingSortAction;
    private Action infoAction;
    private Action exitDocumentAction;

    private final JNotepadPP parent;
    private final DefaultMultipleDocumentModel tabs;
    private JTextArea editor;
    private final ILocalizationProvider provider;

    public EditorActions(JNotepadPP parent, DefaultMultipleDocumentModel tabs, JTextArea editor, ILocalizationProvider provider) {
        this.parent = parent;
        this.tabs = tabs;
        this.editor = editor;
        this.provider = provider;

        initializeActions();
    }

    private void initializeActions() {
        newDocumentAction = new NewDocumentAction(tabs, "new", provider, KeyStroke.getKeyStroke("control N"),
                KeyEvent.VK_N, true);

        openDocumentAction = new OpenDocumentAction(tabs, parent, "open", provider,
                KeyStroke.getKeyStroke("control O"), KeyEvent.VK_O, true);

        Path path = Paths.get("");
        saveDocumentAction = new SaveDocumentAction(tabs,
                tabs.getNumberOfDocuments() == 0 ? path : tabs.getCurrentDocument().getFilePath(), "save",
                provider, KeyStroke.getKeyStroke("control S"), KeyEvent.VK_S, false);

        saveAsDocumentAction = new SaveAsDocumentAction(tabs,
                tabs.getNumberOfDocuments() == 0 ? path : tabs.getCurrentDocument().getFilePath(), "saveAs",
                provider, KeyStroke.getKeyStroke("control alt S"), KeyEvent.VK_S, false);

        closeDocumentAction = new CloseDocumentAction(tabs, "close", provider, KeyStroke.getKeyStroke("control W"),
                KeyEvent.VK_W, true);

        toggleCaseAction = new ToggleCaseAction(editor, "toggle", provider, KeyStroke.getKeyStroke("control T"),
                KeyEvent.VK_T, false);

        toUpperCaseAction = new ToUpperAction(editor, "toUpper", provider, KeyStroke.getKeyStroke("control U"),
                KeyEvent.VK_U, false);

        toLowerCaseAction = new ToLowerAction(editor, "toLower", provider, KeyStroke.getKeyStroke("control L"),
                KeyEvent.VK_L, false);

        copyAction = new CopyDocumentAction(editor, parent, "copy", provider, KeyStroke.getKeyStroke("control C"),
                KeyEvent.VK_C, false);

        pasteAction = new PasteDocumentAction(editor, parent, "paste", provider,
                KeyStroke.getKeyStroke("control shift V"), KeyEvent.VK_V, false);

        cutAction = new CutDocumentAction(editor, parent, "cut", provider, KeyStroke.getKeyStroke("control X"),
                KeyEvent.VK_X, false);

        ascendingSortAction = new AscendingSortAction(tabs, "asc", provider, KeyStroke.getKeyStroke("control shift A"),
                KeyEvent.VK_A, false);

        descendingSortAction = new DescendingSortAction(tabs, "dsc", provider,
                KeyStroke.getKeyStroke("control shift D"), KeyEvent.VK_D, false);

        infoAction = new InfoDocumentAction(tabs, "info", provider, KeyStroke.getKeyStroke("control I"), KeyEvent.VK_I,
                true);

        exitDocumentAction = new ExitDocumentAction(parent, tabs, "exit", provider, KeyStroke.getKeyStroke("control E"),
                KeyEvent.VK_E, true);
    }

    public void setEditor(JTextArea editor) {
        this.editor = editor;
    }

    public void setCaretDependentActionsEnabled(boolean enabled, JTextArea textArea) {
        toggleCaseAction.setEnabled(enabled);
        toUpperCaseAction.setEnabled(enabled);
        toLowerCaseAction.setEnabled(enabled);
        copyAction.setEnabled(enabled);
        cutAction.setEnabled(enabled);
        ascendingSortAction.setEnabled(enabled);
        descendingSortAction.setEnabled(enabled);

        ((ToggleCaseAction)toggleCaseAction).setTextArea(textArea);
        ((ToUpperAction)toUpperCaseAction).setTextArea(textArea);
        ((ToLowerAction)toLowerCaseAction).setTextArea(textArea);
        ((CopyDocumentAction)copyAction).setTextArea(textArea);
        ((CutDocumentAction)cutAction).setTextArea(textArea);
    }

    public void setSavePath(Path path) {
        ((SaveDocumentAction) saveDocumentAction).setPath(path);
    }

    public void setSaveActionsEnabled(boolean enabled) {
        if (saveDocumentAction != null && saveAsDocumentAction != null) {
            saveDocumentAction.setEnabled(enabled);
            saveAsDocumentAction.setEnabled(enabled);
        }
    }

    public Action getNewDocumentAction() { return newDocumentAction; }
    public Action getOpenDocumentAction() { return openDocumentAction; }
    public Action getSaveDocumentAction() { return saveDocumentAction; }
    public Action getSaveAsDocumentAction() { return saveAsDocumentAction; }
    public Action getCloseDocumentAction() { return closeDocumentAction; }
    public Action getToggleCaseAction() { return toggleCaseAction; }
    public Action getToUpperCaseAction() { return toUpperCaseAction; }
    public Action getToLowerCaseAction() { return toLowerCaseAction; }
    public Action getCopyAction() { return copyAction; }
    public Action getPasteAction() { return pasteAction; }
    public Action getCutAction() { return cutAction; }
    public Action getAscendingSortAction() { return ascendingSortAction; }
    public Action getDescendingSortAction() { return descendingSortAction; }
    public Action getInfoAction() { return infoAction; }
    public Action getExitDocumentAction() { return exitDocumentAction; }
}

package main.java.components;

import main.java.actions_set.EditorActions;
import main.java.local.ILocalizationProvider;
import main.java.local.LJButton;

import javax.swing.*;

public class EditorToolbar extends JToolBar {
    private JButton newDocumentButton;
    private JButton openDocumentButton;
    private JButton saveDocumentButton;
    private JButton saveAsDocumentButton;
    private JButton toggleCaseButton;
    private JButton toUpperButton;
    private JButton toLowerButton;
    private JButton copyButton;
    private JButton pasteButton;
    private JButton cutButton;
    private JButton infoButton;

    private final EditorActions actions;
    private final ILocalizationProvider provider;

    public EditorToolbar(EditorActions actions, ILocalizationProvider provider) {
        super("Tools");
        this.actions = actions;
        this.provider = provider;

        setFloatable(true);
        initComponents();
        addComponents();
    }

    private void initComponents() {
        newDocumentButton = new LJButton(actions.getNewDocumentAction(), "new", provider);
        openDocumentButton = new LJButton(actions.getOpenDocumentAction(), "open", provider);
        saveDocumentButton = new LJButton(actions.getSaveDocumentAction(), "save", provider);
        saveAsDocumentButton = new LJButton(actions.getSaveAsDocumentAction(), "saveAs", provider);
        toggleCaseButton = new LJButton(actions.getToggleCaseAction(), "toggle", provider);
        toUpperButton = new LJButton(actions.getToUpperCaseAction(), "toUpper", provider);
        toLowerButton = new LJButton(actions.getToLowerCaseAction(), "toLower", provider);
        copyButton = new LJButton(actions.getCopyAction(), "copy", provider);
        pasteButton = new LJButton(actions.getPasteAction(), "paste", provider);
        cutButton = new LJButton(actions.getCutAction(), "cut", provider);
        infoButton = new LJButton(actions.getInfoAction(), "info", provider);
    }

    private void addComponents() {
        add(newDocumentButton);
        add(openDocumentButton);
        add(saveDocumentButton);
        add(saveAsDocumentButton);
        addSeparator();

        add(toggleCaseButton);
        add(toUpperButton);
        add(toLowerButton);
        addSeparator();

        add(copyButton);
        add(pasteButton);
        add(cutButton);
        addSeparator();

        add(infoButton);
    }
}

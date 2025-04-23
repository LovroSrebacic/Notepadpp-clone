package main.java.components;

import main.java.actions_set.EditorActions;
import main.java.actions_set.LanguageActions;
import main.java.local.ILocalizationProvider;
import main.java.local.LJMenu;
import main.java.util.Constants;

import javax.swing.*;

public class Menu extends JMenuBar {

    private final EditorActions editorActions;
    private final LanguageActions languageActions;
    private final ILocalizationProvider provider;

    public Menu(EditorActions editorActions, LanguageActions languageActions, ILocalizationProvider provider) {
        this.editorActions = editorActions;
        this.languageActions = languageActions;
        this.provider = provider;

        initComponents();
    }

    private void initComponents(){
        JMenu fileMenu = new LJMenu("file", provider);
        this.add(fileMenu);

        fileMenu.add(new JMenuItem(editorActions.getNewDocumentAction()));
        fileMenu.add(new JMenuItem(editorActions.getOpenDocumentAction()));
        fileMenu.add(new JMenuItem(editorActions.getSaveDocumentAction()));
        fileMenu.add(new JMenuItem(editorActions.getSaveAsDocumentAction()));
        fileMenu.add(new JMenuItem(editorActions.getCloseDocumentAction()));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(editorActions.getExitDocumentAction()));

        JMenu toolMenu = new LJMenu("tools", provider);
        JMenu changeCase = new LJMenu("changeCase", provider);
        JMenu textMenu = new LJMenu("textMenu", provider);

        changeCase.add(new JMenuItem(editorActions.getToggleCaseAction()));
        changeCase.add(new JMenuItem(editorActions.getToUpperCaseAction()));
        changeCase.add(new JMenuItem(editorActions.getToLowerCaseAction()));

        textMenu.add(new JMenuItem(editorActions.getCopyAction()));
        textMenu.add(new JMenuItem(editorActions.getPasteAction()));
        textMenu.add(new JMenuItem(editorActions.getCutAction()));
        toolMenu.add(changeCase);
        toolMenu.add(textMenu);

        this.add(toolMenu);

        JMenu infoMenu = new LJMenu("info", provider);
        infoMenu.add(new JMenuItem(editorActions.getInfoAction()));

        this.add(infoMenu);

        JMenu sortMenu = new LJMenu("sort", provider);
        JMenuItem ascending = new JMenuItem(editorActions.getAscendingSortAction());
        JMenuItem descending = new JMenuItem(editorActions.getDescendingSortAction());
        sortMenu.add(ascending);
        sortMenu.add(descending);

        this.add(sortMenu);

        JMenu languageMenu = new LJMenu("languages", provider);
        JMenuItem hrAction = new JMenuItem(languageActions.getHrLanguageAction());
        JMenuItem enAction = new JMenuItem(languageActions.getEnLanguageAction());
        JMenuItem deAction = new JMenuItem(languageActions.getDeLanguageAction());
        hrAction.setIcon(new ImageIcon(Constants.HR_FLAG_PATH));
        enAction.setIcon(new ImageIcon(Constants.EN_FLAG_PATH));
        deAction.setIcon(new ImageIcon(Constants.DE_FLAG_PATH));
        languageMenu.add(hrAction);
        languageMenu.add(enAction);
        languageMenu.add(deAction);

        this.add(languageMenu);
    }
}

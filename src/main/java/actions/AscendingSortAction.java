package main.java.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;

import javax.swing.KeyStroke;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class AscendingSortAction extends LocalizableAction{
	
	private final SortImplementation sorter;
	private final DefaultMultipleDocumentModel model;
	private Collator collator;
	
	public AscendingSortAction(DefaultMultipleDocumentModel model, String key, ILocalizationProvider provider, KeyStroke keyStroke, int mnemonicKey, boolean enabled) {
		super(key, provider, keyStroke, mnemonicKey, enabled);
		this.model = model;
		this.sorter = new SortImplementation(model.getCurrentDocument());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.sorter.setCurrentDocument(this.model.getCurrentDocument());
		this.collator = this.model.getCollator();
		
		this.sorter.sort(t -> {
            t.sort((x, y) -> collator.compare(x, y));
            return t;
        });
		
	}

}

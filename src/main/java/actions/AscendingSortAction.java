package main.java.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.List;
import java.util.function.Function;

import javax.swing.KeyStroke;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class AscendingSortAction extends LocalizableAction{

	private static final long serialVersionUID = 1L;
	
	private SortImplementation sorter;
	private DefaultMultipleDocumentModel model;
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
		
		this.sorter.sort(new Function<List<String>, List<String>>() {
			
			@Override
			public List<String> apply(List<String> t) {
				t.sort((x, y) -> collator.compare(x, y));
				return t;
			}
		});
		
	}

}

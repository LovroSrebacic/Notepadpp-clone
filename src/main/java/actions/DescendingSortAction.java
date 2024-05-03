package main.java.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.List;
import java.util.function.Function;

import javax.swing.AbstractAction;

import main.java.jnotepadpp.DefaultMultipleDocumentModel;

public class DescendingSortAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private SortImplementation sorter;
	private DefaultMultipleDocumentModel model;
	private Collator collator;
	
	public DescendingSortAction(DefaultMultipleDocumentModel model) {
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
				t.sort((x, y) -> -collator.compare(x, y));
				return t;
			}
		});
		
	}
}

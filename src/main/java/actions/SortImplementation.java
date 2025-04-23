package main.java.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import main.java.jnotepadpp.SingleDocumentModel;

public class SortImplementation {
	private SingleDocumentModel currentDocument;

	public SortImplementation(SingleDocumentModel currentDocument) {
		this.currentDocument = currentDocument;
	}
	
	public void setCurrentDocument(SingleDocumentModel currentDocument) {
		this.currentDocument = currentDocument;
	}

	public void sort(Function<List<String>, List<String>> f) {

		int offset = Math.min(currentDocument.getTextComponent().getCaret().getDot(),
				currentDocument.getTextComponent().getCaret().getMark());
		int endset = Math.max(currentDocument.getTextComponent().getCaret().getDot(),
				currentDocument.getTextComponent().getCaret().getMark());

		try {
			offset = currentDocument.getTextComponent()
					.getLineStartOffset(currentDocument.getTextComponent().getLineOfOffset(offset));
			endset = currentDocument.getTextComponent()
					.getLineEndOffset(currentDocument.getTextComponent().getLineOfOffset(endset));

			Document document = currentDocument.getTextComponent().getDocument();
			String text = document.getText(offset, endset - offset);
			String[] lines = text.split("\\r?\\n");
			List<String> order = new ArrayList<>();
            Collections.addAll(order, lines);

			order = f.apply(order);
			document.remove(offset, endset - offset);
			StringBuilder sb = new StringBuilder();
			for (String s : order) {
				sb.append(s).append("\n");
			}

			document.insertString(offset, sb.toString(), null);

		} catch (BadLocationException e) {
			System.err.println("BadLocationException: " + e.getMessage());
		}

	}
}

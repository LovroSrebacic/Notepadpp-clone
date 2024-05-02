package main.java.listener;

import main.java.jnotepadpp.SingleDocumentModel;

public interface MultipleDocumentListener {
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	void documentAdded(SingleDocumentModel model);
	void documentRemoved(SingleDocumentModel model);
}

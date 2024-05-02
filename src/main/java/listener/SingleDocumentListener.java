package main.java.listener;

import main.java.jnotepadpp.SingleDocumentModel;

public interface SingleDocumentListener {
	void documentModifyStatusUpdated(SingleDocumentModel model);
	void documentFilePathUpdated(SingleDocumentModel model);
}

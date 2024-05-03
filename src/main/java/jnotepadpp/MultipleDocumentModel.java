package main.java.jnotepadpp;

import java.nio.file.Path;

import main.java.listener.MultipleDocumentListener;

public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	SingleDocumentModel createNewDocument();
	SingleDocumentModel getCurrentDocument();
	SingleDocumentModel loadDocument(Path path);
	void saveDocument(SingleDocumentModel model, Path newPath, boolean closing);
	void closeDocument(SingleDocumentModel model);
	void addMultipleDocumentListener(MultipleDocumentListener l);
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	int getNumberOfDocuments();
	SingleDocumentModel getDocument(int index);
}

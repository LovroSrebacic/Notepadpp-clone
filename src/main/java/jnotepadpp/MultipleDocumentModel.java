package main.java.jnotepadpp;

import java.nio.file.Path;

public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	void createNewDocument();
	SingleDocumentModel getCurrentDocument();
	void loadDocument(Path path);
	void saveDocument(SingleDocumentModel model, Path newPath, boolean closing);
	void closeDocument(SingleDocumentModel model);
	int getNumberOfDocuments();
	SingleDocumentModel getDocument(int index);
}

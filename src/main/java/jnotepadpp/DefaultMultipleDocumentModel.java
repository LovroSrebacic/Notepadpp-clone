package main.java.jnotepadpp;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.java.listener.MultipleDocumentListener;
import main.java.listener.SingleDocumentListener;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{

	private static final long serialVersionUID = 1L;
	private static final ImageIcon greenDiskette = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\resources\\greenDiskette.png");
	private static final ImageIcon redDiskette = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\resources\\redDiskette.png");
	
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel currentDocument;
	private SingleDocumentModel previousDocument;
	private List<MultipleDocumentListener> listeners;
	private int currentIndex;
	private JTabbedPane tabs;
	private SingleDocumentListener singleDocumentListener;
	private DocumentListener jTextAreaListener;
	
	public DefaultMultipleDocumentModel(JNotepadPP notepad) {
		this.documents = new ArrayList<>();
		this.listeners = new ArrayList<>();
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		this.tabs = this;
		
		MultipleDocumentListener multipleDocumentListener = new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
					tabs.removeTabAt(currentIndex);
					documents.remove(model);
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				JScrollPane pane = new JScrollPane(model.getTextComponent());
				tabs.addTab(model.getFilePath().toString().substring(model.getFilePath().toString().lastIndexOf("\\") + 1), greenDiskette, pane, model.getFilePath().toString());
				tabs.setSelectedIndex(documents.size() - 1);
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if(!previousModel.getTextComponent().getText().equals(currentModel.getTextComponent().getText())) {
					for(SingleDocumentListener l : ((DefaultSingleDocumentModel) currentDocument).getListeners()) {
						l.documentModifyStatusUpdated(currentDocument);
					}
				}
				
			}
		};
		
		this.listeners.add(multipleDocumentListener);
		
		this.singleDocumentListener = new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				int counter = 0;
				Iterator<SingleDocumentModel> iterator = iterator();
				while(iterator.hasNext()) {
					if(iterator.next().equals(model)) {
						tabs.setIconAt(counter, redDiskette);
					}else {
						counter++;
					}
				}
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				int counter = 0;
				Iterator<SingleDocumentModel> iterator = iterator();
				while(iterator.hasNext()) {
					if(iterator.next().equals(model)) {
						tabs.setTitleAt(counter, model.getFilePath().toString().substring(model.getFilePath().toString().lastIndexOf("\\") + 1));
					}else {
						counter++;
					}
				}
				
			}
		};
		
		ChangeListener tabsListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane source = (JTabbedPane) e.getSource();
				currentIndex = source.getSelectedIndex();
				notepad.setTitle(currentIndex == -1 ? "JNotepad++" : tabs.getToolTipTextAt(currentIndex) + " - JNotepad++");
				previousDocument = new DefaultSingleDocumentModel(currentDocument.getTextComponent().getText(), currentDocument.getFilePath());
				currentDocument = currentIndex == -1 ? null : (DefaultSingleDocumentModel) documents.get(tabs.getSelectedIndex());
			}
		};
		
		this.tabs.addChangeListener(tabsListener);
		
		this.jTextAreaListener = new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				for(MultipleDocumentListener l : listeners) {
					l.currentDocumentChanged(previousDocument, currentDocument);
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				for(MultipleDocumentListener l : listeners) {
					l.currentDocumentChanged(previousDocument, currentDocument);
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				for(SingleDocumentListener l : ((DefaultSingleDocumentModel) currentDocument).getListeners()) {
					l.documentFilePathUpdated(currentDocument);
				}
			}
		};
		
		createNewDocument();
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		Path path = Paths.get("(unnamed)");
		DefaultSingleDocumentModel document = new DefaultSingleDocumentModel("", path);
		document.addSingleDocumentListener(singleDocumentListener);
		this.currentDocument = document;
		this.documents.add(document);
		
		document.getTextComponent().getDocument().addDocumentListener(jTextAreaListener);
		
		for(MultipleDocumentListener l : listeners) {
			l.documentAdded(document);
		}
		
		return document;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return this.documents.get(currentIndex);
	}
	
	public void setCurrentDocument(SingleDocumentModel document) {
		this.currentDocument = document;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		for(MultipleDocumentListener l : listeners) {
			l.documentRemoved(model);
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return this.documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return this.documents.get(index);
	}

}

package main.java.jnotepadpp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.java.listener.MultipleDocumentListener;
import main.java.listener.SingleDocumentListener;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	private static final ImageIcon greenDiskette = new ImageIcon(
			System.getProperty("user.dir") + "\\src\\main\\resources\\greenDiskette.png");
	private static final ImageIcon redDiskette = new ImageIcon(
			System.getProperty("user.dir") + "\\src\\main\\resources\\redDiskette.png");

	private List<SingleDocumentModel> documents;
	private SingleDocumentModel currentDocument;
	private SingleDocumentModel previousDocument;
	private List<MultipleDocumentListener> listeners;
	private int currentIndex;
	private JTabbedPane tabs;
	private SingleDocumentListener singleDocumentListener;
	private DocumentListener jTextAreaListener;
	private JNotepadPP notepad;
	private boolean saveAs;

	public DefaultMultipleDocumentModel(JNotepadPP notepad) {
		this.documents = new ArrayList<>();
		this.listeners = new ArrayList<>();
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		this.tabs = this;
		this.notepad = notepad;
		this.saveAs = false;

		MultipleDocumentListener multipleDocumentListener = new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				notepad.closeWindow(model, currentIndex);
				tabs.removeTabAt(currentIndex);
				documents.remove(model);
				currentDocument = documents.get(tabs.getSelectedIndex());
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				JScrollPane pane = new JScrollPane(model.getTextComponent());
				tabs.addTab(
						model.getFilePath().toString().substring(model.getFilePath().toString().lastIndexOf("\\") + 1),
						greenDiskette, pane, model.getFilePath().toString());
				notepad.setSaveOptions(false);
				tabs.setSelectedIndex(documents.size() - 1);
				currentDocument = model;
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (!previousModel.getTextComponent().getText().equals(currentModel.getTextComponent().getText())) {
					for (SingleDocumentListener l : ((DefaultSingleDocumentModel) currentDocument).getListeners()) {
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
				while (iterator.hasNext()) {
					if (iterator.next().equals(model)) {
						tabs.setIconAt(counter, redDiskette);
						notepad.setSaveOptions(true);
					} else {
						counter++;
					}
				}
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				int counter = 0;
				Iterator<SingleDocumentModel> iterator = iterator();
				while (iterator.hasNext()) {
					if (iterator.next().equals(model)) {
						tabs.setTitleAt(counter, model.getFilePath().toString()
								.substring(model.getFilePath().toString().lastIndexOf("\\") + 1));
					} else {
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
				notepad.setTitle(currentIndex == -1 ? "JNotepad++"
						: (documents.get(currentIndex).getFilePath().toString().equals("(unnamed)") ? "unnamed"
								: documents.get(currentIndex).getFilePath().toString()) + " - JNotepad++");
				previousDocument = new DefaultSingleDocumentModel(currentDocument.getTextComponent().getText(),
						currentDocument.getFilePath());
				currentDocument = currentIndex == -1 ? null
						: (DefaultSingleDocumentModel) documents.get(tabs.getSelectedIndex());
			}
		};

		this.tabs.addChangeListener(tabsListener);

		this.jTextAreaListener = new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				for (MultipleDocumentListener l : listeners) {
					l.currentDocumentChanged(previousDocument, currentDocument);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				for (MultipleDocumentListener l : listeners) {
					l.currentDocumentChanged(previousDocument, currentDocument);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				for (SingleDocumentListener l : ((DefaultSingleDocumentModel) currentDocument).getListeners()) {
					l.documentFilePathUpdated(currentDocument);
				}
			}
		};

		createNewDocument();
	}

	public void setSaveAs(boolean saveAs) {
		this.saveAs = saveAs;
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

		for (MultipleDocumentListener l : listeners) {
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
		byte[] octet;
		try {
			octet = Files.readAllBytes(path);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(notepad,
					"Error while reading file" + " " + path.toFile().getAbsolutePath() + ".", "Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		String text = new String(octet, StandardCharsets.UTF_8);
		DefaultSingleDocumentModel document = new DefaultSingleDocumentModel(text, path);
		document.addSingleDocumentListener(singleDocumentListener);
		this.currentDocument = document;
		this.documents.add(document);
		this.currentDocument.setFilePath(path);
		this.currentDocument.setFirstSave(false);

		document.getTextComponent().getDocument().addDocumentListener(jTextAreaListener);

		for (MultipleDocumentListener l : listeners) {
			l.documentAdded(document);
		}

		return document;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath, boolean closing) {
		if (model.getFilePath().equals(Paths.get("(unnamed)")) || saveAs) {
			JFileChooser jfc = new JFileChooser();
			if (saveAs) {
				jfc.setDialogTitle("Save Document As");
			} else {
				jfc.setDialogTitle("Save Document");
			}
			if (jfc.showSaveDialog(DefaultMultipleDocumentModel.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "Nothing Saved", "Warning",
						JOptionPane.WARNING_MESSAGE);
				saveAs = false;
				model.setFirstSave(true);
				return;
			}
			newPath = jfc.getSelectedFile().toPath();
			model.setFilePath(newPath);
			notepad.setSavePath(newPath);
		}

		if ((Files.exists(newPath) && saveAs) || (Files.exists(newPath) && model.getFirstSave())) {
			String[] options = new String[] { "Yes", "No" };

			int rezultat = JOptionPane.showOptionDialog(notepad, "File Already Exists. Do you want to overwrite it?",
					"Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

			switch (rezultat) {
			case JOptionPane.YES_OPTION:{
				notepad.setSavePath(newPath);
				return;
			}
			case JOptionPane.CLOSED_OPTION:{
				notepad.setSavePath(newPath);
				return;
			}
			case JOptionPane.NO_OPTION:
				break;
			}
		}

		byte[] data = currentDocument.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, data);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this,
					"Error while writing to file" + " " + newPath, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.currentDocument.setFilePath(newPath);
		notepad.setSavePath(newPath);

		notepad.setTitle(model.getFilePath().toString() + " - JNotepad++");
		this.setTitleAt(currentIndex,
				model.getFilePath().toString().substring(model.getFilePath().toString().lastIndexOf("\\") + 1));
		this.setIconAt(currentIndex, greenDiskette);
		this.notepad.setSaveOptions(false);

		if (model.getFirstSave()) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "File Saved", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}
		saveAs = false;
		model.setFirstSave(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		for (MultipleDocumentListener l : listeners) {
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

	public List<SingleDocumentModel> getDocuments() {
		return this.documents;
	}

	public void getInfo() {

		if (currentDocument == null) {
			JOptionPane.showMessageDialog(DefaultMultipleDocumentModel.this, "There is no document opened.",
					"Information", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String text = currentDocument.getTextComponent().getText();
		int allCharacters = text.length();
		int nonWhiteCharacters = text.replaceAll("\\s+", "").length();
		int numberOfRows = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '\n') {
				numberOfRows++;
			}
		}

		JOptionPane
				.showMessageDialog(DefaultMultipleDocumentModel.this,
						String.format("This document has %d characters, %d non white characters and %d rows.",
								allCharacters, nonWhiteCharacters, numberOfRows + 1),
						"Information", JOptionPane.INFORMATION_MESSAGE);
	}

	public Collator getCollator() {
		return this.notepad.getCollator();
	}

}

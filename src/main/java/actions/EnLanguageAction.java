package main.java.actions;

import java.awt.event.ActionEvent;

import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;
import main.java.local.LocalizationProvider;

public class EnLanguageAction extends LocalizableAction{

	private static final long serialVersionUID = 1L;

	public EnLanguageAction(String key, ILocalizationProvider provider) {
		super(key, provider);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getProvider().setLanguage("en");
	}

}

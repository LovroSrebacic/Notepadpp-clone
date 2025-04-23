package main.java.actions;

import java.awt.event.ActionEvent;

import javax.swing.KeyStroke;

import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;
import main.java.local.LocalizationProvider;

public class EnLanguageAction extends LocalizableAction{

	public EnLanguageAction(String key, ILocalizationProvider provider, KeyStroke keyStroke, int mnemonicKey, boolean enabled) {
		super(key, provider, keyStroke, mnemonicKey, enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getProvider().setLanguage("en");
	}

}

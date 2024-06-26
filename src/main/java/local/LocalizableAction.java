package main.java.local;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public abstract class LocalizableAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private ILocalizationProvider provider;
	
	public LocalizableAction(String key, ILocalizationProvider provider, KeyStroke keyStroke, int mnemonicKey, boolean enabled) {
		this.provider = provider;
		putValue(NAME, provider.getString(key));
		putValue(SHORT_DESCRIPTION, provider.getString(key + "_desc"));
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(MNEMONIC_KEY, mnemonicKey);
		setEnabled(enabled);
		
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				putValue(NAME, provider.getString(key));
				putValue(SHORT_DESCRIPTION, provider.getString(key + "_desc"));
			}
		});
	}
	
	public ILocalizationProvider getProvider() {
		return this.provider;
	}
}

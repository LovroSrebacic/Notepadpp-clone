package main.java.local;

import javax.swing.AbstractAction;

public abstract class LocalizableAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	public LocalizableAction(String key, ILocalizationProvider provider) {
		putValue(NAME, provider.getString(key));
		putValue(SHORT_DESCRIPTION, provider.getString(key + "_desc"));
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				putValue(NAME, provider.getString(key));
				putValue(SHORT_DESCRIPTION, provider.getString(key + "_desc"));
			}
		});
	}
}

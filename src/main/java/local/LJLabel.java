package main.java.local;

import javax.swing.JLabel;

public class LJLabel extends JLabel{

	private static final long serialVersionUID = 1L;

	public LJLabel(String key, ILocalizationProvider provider) {
		
		setText(provider.getString(key));
		provider.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setText(provider.getString(key));
			}
		});
	}
}

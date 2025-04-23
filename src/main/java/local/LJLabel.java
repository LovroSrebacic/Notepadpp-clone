package main.java.local;

import javax.swing.JLabel;

public class LJLabel extends JLabel{

	public LJLabel(String key, ILocalizationProvider provider) {
		
		setText(provider.getString(key));
		provider.addLocalizationListener(() -> setText(provider.getString(key)));
	}
}

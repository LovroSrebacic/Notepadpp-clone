package main.java.local;

import javax.swing.JMenu;

public class LJMenu extends JMenu{
	
	public LJMenu(String key, ILocalizationProvider provider) {
		
		setText(provider.getString(key));
		provider.addLocalizationListener(() -> setText(provider.getString(key)));
	}
}

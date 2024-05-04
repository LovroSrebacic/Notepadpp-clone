package main.java.local;

import javax.swing.JMenu;

public class LJMenu extends JMenu{

	private static final long serialVersionUID = 1L;
	
	public LJMenu(String key, ILocalizationProvider provider) {
		
		setText(provider.getString(key));
		provider.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setText(provider.getString(key));
			}
		});
	}
}

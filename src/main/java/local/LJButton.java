package main.java.local;

import javax.swing.Action;
import javax.swing.JButton;

public class LJButton extends JButton{

	private static final long serialVersionUID = 1L;

	public LJButton(Action action, String key, ILocalizationProvider provider) {
		super(action);
		
		setText(provider.getString(key));
		provider.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setText(provider.getString(key));
			}
		});
	}
}

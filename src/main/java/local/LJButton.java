package main.java.local;

import javax.swing.Action;
import javax.swing.JButton;

public class LJButton extends JButton{

	public LJButton(Action action, String key, ILocalizationProvider provider) {
		super(action);
		
		setText(provider.getString(key));
		provider.addLocalizationListener(() -> setText(provider.getString(key)));
	}
}

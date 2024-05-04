package main.java.local;

import java.text.Collator;

public interface ILocalizationProvider {
	public void addLocalizationListener(ILocalizationListener listener);
	public void removeLocalizationListener(ILocalizationListener listener);
	public Collator getCollator();
	public String getString(String key);
}

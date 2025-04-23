package main.java.local;

import java.text.Collator;

public interface ILocalizationProvider {
	void addLocalizationListener(ILocalizationListener listener);
	void removeLocalizationListener(ILocalizationListener listener);
	Collator getCollator();
	String getString(String key);
}

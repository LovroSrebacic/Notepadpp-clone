package main.java.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	private final List<ILocalizationListener> listeners;
	
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	public void addLocalizationListener(ILocalizationListener listener) {
			listeners.add(listener);
	}
	
	public void removeLocalizationListener(ILocalizationListener listener) {
			listeners.remove(listener);
	}
	
	public void fire() {
		for(ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}
}

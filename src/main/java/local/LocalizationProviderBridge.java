package main.java.local;

import java.text.Collator;

public class LocalizationProviderBridge extends AbstractLocalizationProvider{

	private boolean connected;
	
	private ILocalizationProvider provider;
	
	private ILocalizationListener listener;
	
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		this.listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}
	
	public void connect() {
		if(connected) {
			return;
		}
		
		this.connected = true;
		this.provider.addLocalizationListener(this.listener);
		
	}
	
	public void disconnect() {
		if(!connected) {
			return;
		}
		
		this.connected = false;
		this.provider.removeLocalizationListener(this.listener);
	}
	
	@Override
	public String getString(String key) {
		return this.provider.getString(key);
	}

	@Override
	public Collator getCollator() {
		return provider.getCollator();
	}
}

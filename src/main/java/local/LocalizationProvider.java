package main.java.local;

import java.text.Collator;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider{
	
	private String language;
	private ResourceBundle bundle;
	private Collator collator;

    private static final LocalizationProvider provider = new LocalizationProvider();

	private LocalizationProvider() {
		setLanguage("en");
	}

	public void setLanguage(String language) {
		if (this.language != null) {
			if (this.language.equals(language)) {
				return;
			}
		}

		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		collator = Collator.getInstance(locale);
        String LANGUAGES = "main.java.local.lang.language";
        bundle = ResourceBundle.getBundle(LANGUAGES + "_" + this.language, locale);
		fire();
	}

	public static LocalizationProvider getProvider() {
		return provider;
	}
	
	public Collator getCollator() {
		return collator;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

}

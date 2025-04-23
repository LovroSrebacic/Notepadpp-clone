package main.java.actions_set;

import main.java.actions.DeLanguageAction;
import main.java.actions.EnLanguageAction;
import main.java.actions.HrLanguageAction;
import main.java.local.ILocalizationProvider;
import main.java.local.LocalizableAction;

public class LanguageActions {
    private final LocalizableAction hrLanguageAction;
    private final LocalizableAction enLanguageAction;
    private final LocalizableAction deLanguageAction;

    public LanguageActions(ILocalizationProvider provider) {
        hrLanguageAction = new HrLanguageAction("hr", provider, null, 0, true);
        enLanguageAction = new EnLanguageAction("en", provider, null, 0, true);
        deLanguageAction = new DeLanguageAction("de", provider, null, 0, true);
    }

    public LocalizableAction getHrLanguageAction() {
        return hrLanguageAction;
    }

    public LocalizableAction getEnLanguageAction() {
        return enLanguageAction;
    }

    public LocalizableAction getDeLanguageAction() {
        return deLanguageAction;
    }
}

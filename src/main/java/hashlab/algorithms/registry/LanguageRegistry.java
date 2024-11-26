package hashlab.algorithms.registry;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class LanguageRegistry {
    private static final Map<String, Locale> languageRegistry = new HashMap<>();

    static {
        languageRegistry.put("English", Locale.ENGLISH);
        languageRegistry.put("Polski", new Locale("pl", "PL"));
    }

    public static Locale getLocale(String language){
        return languageRegistry.get(language);
    }

    public static Set<String> getAllLanguages(){
        return languageRegistry.keySet();
    }
}

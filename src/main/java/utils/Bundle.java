package utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Bundle {
    private static final ResourceBundle STRING_BUNDLE =
            ResourceBundle.getBundle("bundles.strings",new Locale("en", "EN"));

    public static String getString(String key) {
        return STRING_BUNDLE.getString(key);
    }
}

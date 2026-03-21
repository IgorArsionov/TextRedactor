package com.example.textredactor.ui.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public final class I18n {

    private static Locale currentLocale = new Locale("uk");
    private static ResourceBundle bundle =
            ResourceBundle.getBundle("i18n.messages", currentLocale);

    private I18n() {
    }

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("i18n.messages", currentLocale);
    }

    public static Locale getLocale() {
        return currentLocale;
    }

    public static String get(String key) {
        return bundle.getString(key);
    }
}
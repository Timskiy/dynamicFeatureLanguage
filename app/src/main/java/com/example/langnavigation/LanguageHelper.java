package com.example.langnavigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;

import java.util.Locale;

import androidx.preference.PreferenceManager;

public class LanguageHelper {
    private static final String PREFS_LANG = "SelectedLanguage";

    private static SharedPreferences prefs;
    private static String language;

    public static void init(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        language = prefs.getString(PREFS_LANG, Locale.getDefault().getLanguage());
    }

    public static Context getLanguageConfigurationContext(Context context) {
        Configuration configuration = getLanguageConfiguration();
        return context.createConfigurationContext(configuration);
    }

    public static Configuration getLanguageConfiguration() {
        Configuration configuration = new Configuration();
        Log.d("LANG_TEST", "language is " + language);
        configuration.setLocale(Locale.forLanguageTag(language));
        return configuration;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String newLanguage) {
        language = newLanguage;
        prefs.edit().putString(PREFS_LANG, newLanguage).apply();
    }
}

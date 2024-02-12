package com.example.langnavigation.interfaces;

public interface LanguageChangeCallback {
    void onLocaleChanged(String languageCode);
    void onShowProgressBar();
    void onHideProgressBar();
    void onShowErrorDialog();
}

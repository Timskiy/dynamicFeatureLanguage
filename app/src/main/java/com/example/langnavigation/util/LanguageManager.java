package com.example.langnavigation.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.langnavigation.LanguageHelper;
import com.example.langnavigation.interfaces.LanguageChangeCallback;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;

import java.util.Locale;

import androidx.preference.PreferenceManager;

public class LanguageManager {

    private final SplitInstallManager splitInstallManager;
    private final SharedPreferences sharedPreferences;
    private SplitInstallStateUpdatedListener splitInstallListener;
    private LanguageChangeCallback callback;

    private String selectedLanguageCode;

    public LanguageManager(Context context, LanguageChangeCallback callback) {
        this.callback = callback;
        this.splitInstallManager = SplitInstallManagerFactory.create(context);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        initListener();
    }

    public void initListener() {
        splitInstallListener = state -> {
            switch (state.status()) {
                case SplitInstallSessionStatus.DOWNLOADING:
                    int progress = (int) (100 * state.bytesDownloaded() / state.totalBytesToDownload());
                    Log.v("LANG_TEST", "Downloading: " + progress);
                    if (callback != null) {
                        callback.onShowProgressBar();
                    }
                    break;
                case SplitInstallSessionStatus.INSTALLED:
                    Log.v("LANG_TEST", "Language installed");
                    if (callback != null) {
                        callback.onHideProgressBar();
                    }
                    onSuccessfulLanguageLoad(selectedLanguageCode);
                    break;
                case SplitInstallSessionStatus.FAILED:
                    Log.e("LANG_TEST", "Failed to install language: ");
                    if (callback != null) {
                        callback.onHideProgressBar();
                        callback.onShowErrorDialog();
                    }
                    break;
            }
        };
        splitInstallManager.registerListener(splitInstallListener);
    }

    public void loadAndSwitchLanguage(String languageCode) {
        Log.d("LANG_TEST", "Loading and switching language: " + languageCode);
        selectedLanguageCode = languageCode;

        String currentLanguage = getAppLocale().getLanguage();
        if (currentLanguage.equals(languageCode)) {
            Log.d("LANG_TEST", "Selected language is already in use");
            return;
        }
        //skip loading if the language is already installed
        if (splitInstallManager.getInstalledLanguages().contains(languageCode)) {
            Log.d("LANG_TEST", "Language already installed");
            onSuccessfulLanguageLoad(languageCode);
        } else {
            //create request to download language
            SplitInstallRequest request = SplitInstallRequest.newBuilder()
                    .addLanguage(Locale.forLanguageTag(languageCode))
                    .build();

            //start to install
            splitInstallManager.startInstall(request)
                    .addOnSuccessListener(sessionId -> {
                        Log.i("LANG_TEST", "Language successfully started");
                    })
                    .addOnFailureListener(exception -> {
                        Log.e("LANG_TEST", "Failed to install language.");
                    });

        }
    }

    public boolean isLanguageInstalled(String languageCode) {
        return splitInstallManager.getInstalledLanguages().contains(languageCode);
    }

    public void unregisterListener() {
        if (splitInstallListener != null) {
            splitInstallManager.unregisterListener(splitInstallListener);
        }
    }

    private Locale getAppLocale() {
        String language = sharedPreferences.getString("SelectedLanguage", Locale.getDefault().getLanguage());
        return new Locale(language);
    }


    private void onSuccessfulLanguageLoad(String languageCode){
        Log.d("LANG_TEST", "successful language is " + languageCode);
        LanguageHelper.setLanguage(languageCode);
        if (callback != null){
            callback.onLocaleChanged(languageCode);
        }
    }
}

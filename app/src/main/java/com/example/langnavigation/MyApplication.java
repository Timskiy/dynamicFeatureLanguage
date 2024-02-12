package com.example.langnavigation;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.play.core.splitcompat.SplitCompat;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        LanguageHelper.init(base);
        Context context = LanguageHelper.getLanguageConfigurationContext(base);
        super.attachBaseContext(context);
        SplitCompat.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, initializationStatus -> {
            Log.d("LANG_TEST", "MobileAds SDK Initialized");
        });
    }
}


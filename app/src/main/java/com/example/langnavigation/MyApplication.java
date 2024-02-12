package com.example.langnavigation;

import android.app.Application;
import android.content.Context;

import com.google.android.play.core.splitcompat.SplitCompat;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        LanguageHelper.init(base);
        Context context = LanguageHelper.getLanguageConfigurationContext(base);
        super.attachBaseContext(context);
        SplitCompat.install(this);
    }
}


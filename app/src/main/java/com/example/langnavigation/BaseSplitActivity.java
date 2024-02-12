package com.example.langnavigation;

import android.content.Context;

import com.google.android.play.core.splitcompat.SplitCompat;

import androidx.appcompat.app.AppCompatActivity;

public class BaseSplitActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = LanguageHelper.getLanguageConfigurationContext(newBase);
        super.attachBaseContext(context);
        SplitCompat.installActivity(this);
    }
}

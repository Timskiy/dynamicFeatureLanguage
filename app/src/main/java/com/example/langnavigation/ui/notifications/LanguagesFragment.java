package com.example.langnavigation.ui.notifications;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.langnavigation.MainActivity;
import com.example.langnavigation.R;
import com.example.langnavigation.adapter.LanguageAdapter;
import com.example.langnavigation.interfaces.LanguageChangeCallback;
import com.example.langnavigation.model.LanguageItem;
import com.example.langnavigation.util.LanguageManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

public class LanguagesFragment extends Fragment implements LanguageChangeCallback {

    private TextView text_lang;
    private SharedPreferences prefs;
    private LanguageManager languageManager;
    private ProgressBar progressBarLanguage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_languages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text_lang = view.findViewById(R.id.text_lang);
        progressBarLanguage = view.findViewById(R.id.indicatorLang);
        iniUnitLanguage();

        TextView text_test = view.findViewById(R.id.text_test);
        text_test.setOnClickListener(v -> {
            text_test.setText(getResources().getString(R.string.load_italian));
        });
    }

    private void iniUnitLanguage() {
        String[] languageNames = getResources().getStringArray(R.array.languages_units_titles);
        String[] languageCodes = getResources().getStringArray(R.array.languages_codes);

        String savedLanguageCode = prefs.getString("SelectedLanguage", "");
        String languageName = "no";
        for (int i = 0; i < languageCodes.length; i++) {
            if (languageCodes[i].equals(savedLanguageCode)) {
                languageName = languageNames[i];
                break;
            }
        }

        text_lang.setText(languageName);

        languageManager = new LanguageManager(requireContext(), this);
        text_lang.setOnClickListener(v -> {
            selectUnitLanguage(languageNames, languageCodes, savedLanguageCode);
        });
    }

    private void selectUnitLanguage(String[] languageNames, String[] languageCodes, String currentLanguage) {
        List<LanguageItem> languageItems = new ArrayList<>();
        for (int i = 0; i < languageNames.length; i++) {
            boolean isInstalled = languageManager.isLanguageInstalled(languageCodes[i]);
            languageItems.add(new LanguageItem(languageNames[i], languageCodes[i], isInstalled));
        }

        Collections.sort(languageItems, new Comparator<LanguageItem>() {
            @Override
            public int compare(LanguageItem o1, LanguageItem o2) {
                Collator collator = Collator.getInstance();
                return collator.compare(o1.getName(), o2.getName());
            }
        });

        LanguageAdapter languageAdapter = new LanguageAdapter(requireContext(), languageItems, currentLanguage);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setAdapter(languageAdapter, ((dialog, which) -> {
            languageManager.loadAndSwitchLanguage(languageItems.get(which).getCode());
        }));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (languageManager != null) {
            languageManager.unregisterListener();
        }
    }

    @Override
    public void onLocaleChanged(String languageCode) {
        if (getActivity() instanceof MainActivity){
            ((MainActivity)getActivity()).restartActivity();
        }
    }

    @Override
    public void onShowProgressBar() {
        progressBarLanguage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideProgressBar() {
        progressBarLanguage.setVisibility(View.GONE);
    }

    @Override
    public void onShowErrorDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Installation error")
                .setMessage("Failed to install the language pack. PLease check internet connection")
                .setPositiveButton("OK", null)
                .show();
    }
}
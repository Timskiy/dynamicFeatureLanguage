package com.example.langnavigation.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.langnavigation.R;
import com.example.langnavigation.model.LanguageItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LanguageAdapter extends ArrayAdapter<LanguageItem> {

    private final String currentLanguageCode;

    public LanguageAdapter(Context context, List<LanguageItem> languages, String currentLanguageCode) {
        super(context, 0, languages);
        this.currentLanguageCode = currentLanguageCode;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_language, parent, false);
        }

        TextView tvLanguage = convertView.findViewById(R.id.tvLanguage);
        ImageView ivStatus = convertView.findViewById(R.id.ivStatus);

        LanguageItem languageItem = getItem(position);
        if (languageItem != null) {
            tvLanguage.setText(languageItem.getName());

            int imageDrawable = languageItem.isLoaded() ? R.drawable.circle_check : R.drawable.circle_outline;
            ivStatus.setImageResource(imageDrawable);
        }

        if (languageItem.isLoaded()){
            tvLanguage.setTypeface(null, Typeface.BOLD);
        } else {
            tvLanguage.setTypeface(null, Typeface.NORMAL);
        }

        return convertView;
    }
}

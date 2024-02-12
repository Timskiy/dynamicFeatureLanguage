package com.example.langnavigation.model;

public class LanguageItem {
    private String name;
    private String code;
    private boolean isLoaded;

    public LanguageItem(String name, String code, boolean isLoaded) {
        this.name = name;
        this.code = code;
        this.isLoaded = isLoaded;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }
}

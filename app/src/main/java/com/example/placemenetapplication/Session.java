package com.example.placemenetapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences preferences;

    public Session(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUsername(String username) {
        preferences.edit().putString("sessionUsername", username).apply();
    }

    public String getUsername() {
        return preferences.getString("sessionUsername","");
    }

    public void clearSession() {
        preferences.edit().remove("sessionUsername").apply();
        preferences.edit().clear().apply();
    }
}
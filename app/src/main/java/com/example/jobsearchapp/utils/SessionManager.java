package com.example.jobsearchapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_ROLE = "role";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveSession(int userId, String role) {
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }

    public int getUserId() { return pref.getInt(KEY_USER_ID, -1); }
    public String getRole() { return pref.getString(KEY_ROLE, ""); }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
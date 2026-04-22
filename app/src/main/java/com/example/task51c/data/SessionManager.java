package com.example.task51c.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "task51_session";
    private static final String KEY_USER_ID = "user_id";

    private final SharedPreferences preferences;

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void login(int userId) {
        preferences.edit().putInt(KEY_USER_ID, userId).apply();
    }

    public void logout() {
        preferences.edit().clear().apply();
    }

    public int getLoggedInUserId() {
        return preferences.getInt(KEY_USER_ID, -1);
    }

    public boolean isLoggedIn() {
        return getLoggedInUserId() != -1;
    }
}

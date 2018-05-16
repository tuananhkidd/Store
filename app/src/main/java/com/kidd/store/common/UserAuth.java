package com.kidd.store.common;

import android.content.Context;
import android.content.SharedPreferences;

public class UserAuth {
    public static final String USER_SHARE_PREFERENCES = "user_prefs";

    public static String getUserID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.KEY_USER_EMAIL, null);
    }
    public static void saveLoginState(Context context, String userID) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_USER_EMAIL, userID);
        editor.apply();
    }

    public static void saveLogoutState(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_USER_EMAIL, null);
        editor.apply();
    }
    public static boolean isUserLoggedIn(Context context) {
        return Constants.LOGIN_TRUE.equals(Utils.getSharePreferenceValues(context,Constants.STATUS_LOGIN));
    }
}

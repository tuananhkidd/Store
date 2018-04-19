package com.kidd.store.common;

import android.content.Context;

public class UserAuth {
    public static boolean isUserLoggedIn(Context context) {
        return Constants.LOGIN_TRUE.equals(Utils.getSharePreferenceValues(context,Constants.STATUS_LOGIN));
    }
}

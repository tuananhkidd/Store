package com.kidd.store.common;

import android.util.Base64;


public class Base64UtilAccount {
    public static String getBase64Account(String username, String password) {
        String account = username + ":" + password;
        String base64Account = Base64.encodeToString(account.getBytes(), Base64.NO_WRAP | Base64.URL_SAFE);
        base64Account = RequestConstants.BASIC_PREFIX + base64Account;
        return base64Account;
    }


}

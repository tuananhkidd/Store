package com.kidd.store.presenter.account.login.facebook_login;

import com.kidd.store.models.response.HeaderProfile;

public interface OnFacebookLoginSuccessListener {
    void onLoginSuccess(HeaderProfile headerProfile);
    void onLoginError(String msg);
}

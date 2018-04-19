package com.kidd.store.presenter.account.login;

import com.kidd.store.models.response.HeaderProfile;

public interface OnLoginSuccessListener {
    void onLoginSuccess(HeaderProfile headerProfile);
    void onAccounNotVerify();
    void onError(String message);
}

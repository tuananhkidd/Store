package com.kidd.store.view.account.login.facebook_login;

import com.kidd.store.models.response.HeaderProfile;

public interface FacebookLoginView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showEmailError();
    void showEmailInvalid();
    void backToHomeScreen(HeaderProfile headerProfile, int resultCode);
}

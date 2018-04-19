package com.kidd.store.view.account.login;

import com.kidd.store.models.response.HeaderProfile;

public interface LoginView  {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showUserNameError();
    void showPasswordError();
    void backToHomeScreen(HeaderProfile headerProfile,int resultCode);

}

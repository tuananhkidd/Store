package com.kidd.store.view.account.password.change_password;

public interface ChangePasswordView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showOldPassError();
    void showNewPassError();
    void showConfirmPassError();
    void showConfirmPasNotMatch();
    void backToHomeScreen();
}

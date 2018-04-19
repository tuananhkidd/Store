package com.kidd.store.view.account.register;

public interface RegisterView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showUserNameError();
    void showPasswordError();
    void showFullNameError();
    void showAddressError();
    void showPhoneError();
    void gotoVerifyActivity(String username);
}

package com.kidd.store.view.profile.update_profile;

public interface UpdateProfileView {
    void showLoadingDiaolog();
    void hideLoadingDialog();
    void showFullNameError();
    void showAddressError();
    void showEmailError();
    void showPhoneError();
    void showIDCardError();
    void backToProfileScreen();
}

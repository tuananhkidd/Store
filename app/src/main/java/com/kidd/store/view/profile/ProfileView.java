package com.kidd.store.view.profile;

import com.kidd.store.models.response.Profile;

public interface ProfileView {
    void showLoadingDialog();
    void hideLoadingDialog();

    void showProfile(Profile profile);
}

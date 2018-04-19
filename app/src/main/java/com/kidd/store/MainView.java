package com.kidd.store;

import com.kidd.store.models.response.HeaderProfile;

public interface MainView {
    void switchNavigationDrawer(boolean isLoggedIn);
    void showHeaderProfile(HeaderProfile headerProfile);

    void showProgress();
    void hideProgress();
}

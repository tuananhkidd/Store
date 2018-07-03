package com.kidd.store.view.splash;

public interface SplashView {
    void startProgress();
    boolean isInProgress();
    void completeLoading();
    void onServerMaintance();
    void onServerError();
    void onNetworkError();
}

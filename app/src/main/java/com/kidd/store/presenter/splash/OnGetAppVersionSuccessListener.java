package com.kidd.store.presenter.splash;

public interface OnGetAppVersionSuccessListener {
    void onGetAppVerion(int version);
    void onServerMaintain();
    void onNetworkConnectionError();
    void onServerError();
}

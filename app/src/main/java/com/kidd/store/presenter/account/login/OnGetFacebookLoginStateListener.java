package com.kidd.store.presenter.account.login;

public interface OnGetFacebookLoginStateListener {
    void onGetState(Object o);
    void onError(String msg);
}

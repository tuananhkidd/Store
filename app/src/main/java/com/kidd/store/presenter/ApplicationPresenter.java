package com.kidd.store.presenter;


public interface ApplicationPresenter extends BasePresenter {
    void changeOnlineState(boolean isOnline, OnRequestCompleteListener listener);
}

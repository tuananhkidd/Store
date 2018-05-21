package com.kidd.store.presenter;


public interface ApplicationInteractor extends BaseInteractor {
    void updateUserOnlineState(String userID, boolean isOnline, OnRequestCompleteListener listener);
}

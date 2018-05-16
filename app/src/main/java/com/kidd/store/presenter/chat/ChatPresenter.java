package com.kidd.store.presenter.chat;


import com.kidd.store.presenter.BasePresenter;

public interface ChatPresenter extends BasePresenter {
    void registerOnMessageAddedListener();
    void unregisterOnMessageAddedListener();

    void validateSendingMessage(String message);
}

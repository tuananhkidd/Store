package com.kidd.store.presenter.chat;


import com.kidd.store.models.model_chat.UserMessage;

public interface OnMessageChangedListener {
    void onMessageAdded(UserMessage userMessage);
    void onMessageModified(UserMessage userMessage);
}

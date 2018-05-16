package com.kidd.store.presenter.chat;


import com.kidd.store.models.model_chat.Message;

import java.util.List;

public interface OnGetMessageCompleteListener {
    void onGetMessagesSuccess(List<Message> messages);
    void onError(String message);
}

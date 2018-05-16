package com.kidd.store.view.chat;


import com.kidd.store.models.PageList;
import com.kidd.store.models.model_chat.Message;
import com.kidd.store.models.model_chat.UserMessage;

public interface ChatView {
    void addMessage(UserMessage userMessag);
    void modifiedMessage(UserMessage userMessage);
    void onMessageSeen();


    void showLoadMoreProgress();
    void hideLoadMoreProgress();
    void enableLoadMore(boolean enable);
    void enableRefreshing(boolean enable);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void refreshMessages(PageList<Message> messagePageList);
}

package com.kidd.store.presenter.chat;

import android.content.Context;
import android.util.Log;

import com.kidd.store.models.model_chat.Message;
import com.kidd.store.models.model_chat.UserChat;
import com.kidd.store.models.model_chat.UserMessage;
import com.kidd.store.presenter.OnRequestCompleteListener;
import com.kidd.store.view.chat.ChatView;


public class ChatPresenterImpl implements ChatPresenter {
    public static final String TAG = "ChatPresenterImpl";

    private Context context;
    private ChatView chatView;
    private ChatInteractor chatInteractor;

    private UserChat owner;
    private UserChat friend;

    public ChatPresenterImpl(Context context, ChatView chatView, String roomID) {
        this.context = context;
        this.chatView = chatView;
        this.chatInteractor = new ChatInteractorImpl(context, roomID);
    }

    public void setUser(UserChat owner, UserChat friend) {
        this.owner = owner;
        this.friend = friend;
    }

    private boolean isOwnerMessage(Message message) {
        return message.getOwner().equals(owner.getEmail());
    }

    @Override
    public void onViewDestroy() {
        context = null;
        chatInteractor.onViewDestroy();
    }

    @Override
    public void registerOnMessageAddedListener() {
       // chatView.showLoadMoreProgress();
        chatInteractor.registerOnMessageChangedListener(new OnMessageChangedListener() {
            @Override
            public void onMessageAdded(UserMessage message) {
                chatView.addMessage(message);
                chatView.hideLoadMoreProgress();
            }

            @Override
            public void onMessageModified(UserMessage message) {
                chatView.modifiedMessage(message);
                chatView.hideLoadMoreProgress();
            }
        });
    }

    @Override
    public void unregisterOnMessageAddedListener() {
        chatInteractor.unregisterOnMessageChangedListener();
    }

    @Override
    public void validateSendingMessage(String message) {
        if (message.isEmpty()) {
            return;
        }
        chatView.showLoadMoreProgress();
        chatInteractor.sendMessage(message, new OnRequestCompleteListener() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onServerError(String message) {

            }
        });
    }
}

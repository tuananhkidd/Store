package com.kidd.store.services.event_bus;

import com.kidd.store.models.model_chat.UserChat;

/**
 * Created by KingIT on 3/22/2018.
 */

public class FriendStateOnlineEvent {
    private UserChat user;

    public FriendStateOnlineEvent(UserChat user) {
        this.user = user;
    }

    public UserChat getUser() {
        return user;
    }

    public void setUser(UserChat user) {
        this.user = user;
    }
}

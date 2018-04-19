package com.kidd.store.services.event_bus;

import com.kidd.store.models.response.HeaderProfile;

public class HeaderProfileEvent {
    private HeaderProfile headerProfile;

    public HeaderProfile getHeaderProfile() {
        return headerProfile;
    }

    public void setHeaderProfile(HeaderProfile headerProfile) {
        this.headerProfile = headerProfile;
    }

    public HeaderProfileEvent() {

    }

    public HeaderProfileEvent(HeaderProfile headerProfile) {

        this.headerProfile = headerProfile;
    }
}

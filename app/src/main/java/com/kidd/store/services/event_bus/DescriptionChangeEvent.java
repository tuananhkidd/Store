package com.kidd.store.services.event_bus;

/**
 * Created by La Vo Tinh on 20/04/2018.
 */

public class DescriptionChangeEvent {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DescriptionChangeEvent(String description) {

        this.description = description;
    }
}

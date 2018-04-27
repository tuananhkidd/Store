package com.kidd.store.models.body;

import com.google.android.gms.maps.model.LatLng;

public class LatLngBody {
    private double lat;
    private double lng;

    public LatLngBody() {
    }

    public LatLngBody(LatLng latLng) {
        setLat(latLng.latitude);
        setLng(latLng.longitude);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}

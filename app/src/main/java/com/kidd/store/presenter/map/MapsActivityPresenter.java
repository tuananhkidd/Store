package com.kidd.store.presenter.map;

import com.google.android.gms.maps.model.LatLng;
import com.kidd.store.models.body.LatLngBody;
import com.kidd.store.presenter.BasePresenter;

public interface MapsActivityPresenter extends BasePresenter {
    void getStoreBranch(LatLngBody latLngBody);
}

package com.kidd.store.presenter.map;

import com.kidd.store.models.body.LatLngBody;
import com.kidd.store.models.response.StoreBranchViewModel;
import com.kidd.store.presenter.BaseInteractor;

import java.util.List;

public interface MapsActivityInterator  extends BaseInteractor{
    void getAllStoreBranch(LatLngBody latLngBody,OnGetSuccessListener listener);
}

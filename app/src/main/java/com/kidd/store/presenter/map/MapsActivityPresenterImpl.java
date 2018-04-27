package com.kidd.store.presenter.map;

import android.content.Context;
import android.widget.Toast;

import com.kidd.store.models.body.LatLngBody;
import com.kidd.store.models.response.StoreBranchViewModel;
import com.kidd.store.view.map.MapsActivityView;

import java.util.List;

public class MapsActivityPresenterImpl implements MapsActivityPresenter{
    private Context context;
    private MapsActivityView mapsActivityView;
    private MapsActivityInterator mapsActivityInterator;

    public MapsActivityPresenterImpl(Context context, MapsActivityView mapsActivityView) {
        this.context = context;
        this.mapsActivityView = mapsActivityView;
        this.mapsActivityInterator = new MapsActivityInteratorImpl(context);
    }

    @Override
    public void getStoreBranch(LatLngBody latLngBody) {
        mapsActivityView.showLoadingDialog();
        mapsActivityInterator.getAllStoreBranch(latLngBody,new OnGetSuccessListener() {
            @Override
            public void onSuccess(List<StoreBranchViewModel> storeBranchViewModels) {
                mapsActivityView.hideLoadingDialog();
                mapsActivityView.getAllStoreBrach(storeBranchViewModels);
            }

            @Override
            public void onError(String error) {
                mapsActivityView.hideLoadingDialog();
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewDestroy() {
        mapsActivityInterator.onViewDestroy();
    }
}

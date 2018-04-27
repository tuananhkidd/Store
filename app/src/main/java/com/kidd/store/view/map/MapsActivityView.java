package com.kidd.store.view.map;

import com.kidd.store.models.response.StoreBranchViewModel;

import java.util.List;

public interface MapsActivityView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void getAllStoreBrach(List<StoreBranchViewModel> storeBranchViewModels);
}

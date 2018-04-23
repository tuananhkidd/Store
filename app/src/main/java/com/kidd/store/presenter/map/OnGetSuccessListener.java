package com.kidd.store.presenter.map;

import com.kidd.store.models.response.StoreBranchViewModel;

import java.util.List;

public interface OnGetSuccessListener {
    void onSuccess(List<StoreBranchViewModel> storeBranchViewModels);
    void onError(String error);
}

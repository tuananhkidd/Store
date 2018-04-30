package com.kidd.store.view.follow;

import com.kidd.store.models.response.SaveClothesPreview;

import java.util.List;

public interface SaveClothesView {
    void showLoadMoreProgress();
    void hideLoadMoreProgress();
    void enableLoadMore(boolean enable);
    void enableRefreshing(boolean enable);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void loadMoreSaveClothes(List<SaveClothesPreview> saveClothesPreviews);
    void refreshSaveClothes(List<SaveClothesPreview> saveClothesViews);
    void showNoResult();
    void hideNoResult();
    void showLoadingDialog();
    void hideLoadingDialog();
}

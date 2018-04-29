package com.kidd.store.view.history_order;

import com.kidd.store.models.response.OrderViewModel;

import java.util.List;

public interface HistoryOrderView {
    void showLoadMoreProgress();
    void hideLoadMoreProgress();
    void enableLoadMore(boolean enable);
    void enableRefreshing(boolean enable);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void loadMoreOrderViewModel(List<OrderViewModel> saveClothesPreviews);
    void refreshOrderViewModel(List<OrderViewModel> saveClothesViews);
    void showNoResult();
    void hideNoResult();
    void showLoadingDialog();
    void hideLoadingDialog();
}

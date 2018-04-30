package com.kidd.store.presenter.history;

import android.content.Context;

import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.common.ToastUtils;
import com.kidd.store.models.PageList;
import com.kidd.store.models.response.OrderViewModel;
import com.kidd.store.view.history_order.HistoryOrderView;

public class HistoryOrderPresenterImpl implements HistoryOrderPresenter {
    private Context context;
    private HistoryOrderView historyOrderView;
    private HistoryOrderInteractor historyOrderInteractor;
    int currenIndex = 0;
    public HistoryOrderPresenterImpl(Context context, HistoryOrderView historyOrderView) {
        this.context = context;
        this.historyOrderView = historyOrderView;
        this.historyOrderInteractor = new HistoryOrderInteractorImpl(context);
    }

    @Override
    public void onLoadmoreHistoryOrder() {
        historyOrderView.showLoadMoreProgress();
        historyOrderView.enableRefreshing(false);
        historyOrderInteractor.getAllHistoryOrder(currenIndex + 1, Constants.PAGE_SIZE,
                new OnGetPageHistoryOrderSuccessListener() {
                    @Override
                    public void onSuccess(PageList<OrderViewModel> saveClothesPreviewPageList) {
                        currenIndex++;
                        historyOrderView.hideLoadMoreProgress();
                        historyOrderView.enableRefreshing(true);
                        if (saveClothesPreviewPageList.getPageIndex() == saveClothesPreviewPageList.getTotalPage() - 1) {
                            historyOrderView.enableLoadMore(false);
                        }
                        historyOrderView.loadMoreOrderViewModel(saveClothesPreviewPageList.getResults());
                    }

                    @Override
                    public void onError(String msg) {
                        historyOrderView.hideLoadMoreProgress();
                        historyOrderView.enableRefreshing(true);
                        ToastUtils.quickToast(context, R.string.error_message);
                    }
                });
    }

    @Override
    public void onRefreshHistoryOrder() {
        historyOrderView.showRefreshingProgress();
        historyOrderView.enableRefreshing(true);
        historyOrderInteractor.getAllHistoryOrder(0, Constants.PAGE_SIZE, new OnGetPageHistoryOrderSuccessListener() {
            @Override
            public void onSuccess(PageList<OrderViewModel> orderViewModelPageList) {
                currenIndex=0;
                if (orderViewModelPageList.getPageIndex() == orderViewModelPageList.getTotalPage() - 1) {
                    historyOrderView.enableLoadMore(false);
                }
                historyOrderView.hideRefreshingProgress();
                historyOrderView.enableLoadMore(true);
                historyOrderView.refreshOrderViewModel(orderViewModelPageList.getResults());
                if(orderViewModelPageList.getTotalItem()==0){
                    historyOrderView.showNoResult();
                }else {
                    historyOrderView.hideNoResult();
                }
            }

            @Override
            public void onError(String msg) {
                historyOrderView.hideNoResult();
                historyOrderView.hideRefreshingProgress();
                historyOrderView.enableRefreshing(true);
                ToastUtils.quickToast(context, msg);
            }
        });
    }

    @Override
    public void onViewDestroy() {
        historyOrderInteractor.onViewDestroy();
    }
}

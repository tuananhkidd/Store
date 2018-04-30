package com.kidd.store.presenter.history;

import com.kidd.store.models.PageList;
import com.kidd.store.models.response.OrderViewModel;

public interface OnGetPageHistoryOrderSuccessListener {
    void onSuccess(PageList<OrderViewModel> orderViewModelPageList);
    void onError(String msg);
}

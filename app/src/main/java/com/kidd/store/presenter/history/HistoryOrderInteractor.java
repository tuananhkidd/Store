package com.kidd.store.presenter.history;

import android.content.Context;

import com.kidd.store.presenter.BaseInteractor;
import com.kidd.store.presenter.shop.clothes_detail.OnGetPageRateClothesSuccessListener;

import io.reactivex.disposables.CompositeDisposable;

public interface HistoryOrderInteractor extends BaseInteractor {
    void getAllHistoryOrder(int pageIndex, int pageSize, OnGetPageHistoryOrderSuccessListener listener);
}

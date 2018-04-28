package com.kidd.store.presenter.shop.clothes_detail;

import com.kidd.store.models.response.RateClothesViewModel;

import java.util.List;

public interface OnGetPageRateClothesSuccessListener {
    void onGetRateClothesSuccess(List<RateClothesViewModel> rateClothesViewModelList);
    void onError(String msg);
}

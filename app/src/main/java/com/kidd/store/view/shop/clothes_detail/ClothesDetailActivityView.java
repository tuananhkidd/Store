package com.kidd.store.view.shop.clothes_detail;

import com.kidd.store.models.Clothes;
import com.kidd.store.models.ClothesPreview;
import com.kidd.store.models.response.ClothesViewModel;
import com.kidd.store.models.response.RateClothesViewModel;

import java.util.List;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface ClothesDetailActivityView {
    void showProgress();
    void hideProgress();
    void showClothesDetail(ClothesViewModel clothesViewModel);
    void showErrorLoading(String message);

    void showProgressSimilarClothes();
    void hideProgressSimilarClothes();

    void showErrorSimilarClothes();
    void hideErrorSimilarClothes();

    void showSimilarLoadingMoreProgress();
    void hideSimilarLoadingMoreProgress();

    void enableLoadingMore(boolean enable);

    void refreshSimilarClothes(List<ClothesPreview> similarClothes);
    void loadmoreSimilarClothes(List<ClothesPreview> similarClothes);

    void switchButtonSaveJobToSaved();
    void switchButtonSaveJobToUnSaved();

    void showListSimilarClothes();
    void hideListSimilarClothes();

    void hideRatingDialog();

    void getAllRateClothes(List<RateClothesViewModel> rateClothesViewModelList);
}

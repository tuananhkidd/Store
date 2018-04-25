package com.kidd.store.presenter.shop.clothes_detail;

import android.content.Context;

import com.kidd.store.models.Clothes;
import com.kidd.store.presenter.shop.list_clothes.GetClothesInteractor;
import com.kidd.store.view.shop.ShopFragmentView;
import com.kidd.store.view.shop.clothes_detail.ClothesDetailActivityView;

/**
 * Created by KingIT on 4/23/2018.
 */

public class ClothesDetailPresenterImpl implements ClothesDetailPresenter {
    private Context context;
    private ClothesDetailActivityView clothesDetailActivityView;
    private ClothesDetailInteractor clothesDetailInteractor;
    private int currentPage = 0;

    public ClothesDetailPresenterImpl(Context context, ClothesDetailActivityView clothesDetailActivityView) {
        this.context = context;
        this.clothesDetailActivityView = clothesDetailActivityView;
        this.clothesDetailInteractor = new ClothesDetailInteractorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        context = null;
        clothesDetailInteractor.onViewDestroy();
    }

    @Override
    public void fetchClothesDetail(String clothesID) {
        clothesDetailActivityView.showProgress();
        clothesDetailInteractor.getClothesDetail(clothesID, new OnGetClothesDetailCompleteListener() {
            @Override
            public void onGetClothesDetailComplete(Clothes clothes) {
                clothesDetailActivityView.showClothesDetail(clothes);
                clothesDetailActivityView.hideProgress();
            }

            @Override
            public void onMessageEror(String msg) {
                clothesDetailActivityView.showErrorLoading(msg);
            }
        });
    }

    @Override
    public void saveJob(String clothesID) {

    }

    @Override
    public void deleteSavedJob(String clothesID) {

    }

    @Override
    public void firstFetchSimilarJobs(String clothesID) {

    }

    @Override
    public void loadMoreSimilarJobs(String clothesID) {

    }
}

package com.kidd.store.presenter.shop.clothes_detail;

import android.content.Context;

import com.kidd.store.R;
import com.kidd.store.common.Constants;

import com.kidd.store.models.ClothesPreview;
import com.kidd.store.models.PageList;
import com.kidd.store.models.response.ClothesViewModel;

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
            public void onGetClothesDetailComplete(ClothesViewModel clothesViewModel) {
                clothesDetailActivityView.showClothesDetail(clothesViewModel);
                clothesDetailActivityView.hideProgress();
            }

            @Override
            public void onMessageEror(String msg) {
                clothesDetailActivityView.showErrorLoading(msg);
            }
        });
    }

    @Override
    public void saveClothes(String clothesID) {

    }

    @Override
    public void deleteSavedClothes(String clothesID) {

    }

    @Override
    public void firstFetchSimilarClothes(String clothesID) {

//        clothesDetailActivityView.showProgressSimilarClothes();
        clothesDetailActivityView.hideErrorSimilarClothes();
        clothesDetailInteractor.getSimilarClothes(clothesID,0,
                Constants.PAGE_SIZE,  new OnGetPageClothesPreviewCompleteListener() {
                    @Override
                    public void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList) {
                        if (clothesPreviewPageList.getTotalItem() == 0) {
                            clothesDetailActivityView.hideListSimilarClothes();
                        }
                        clothesDetailActivityView.hideProgressSimilarClothes();
                        if (clothesPreviewPageList.getPageIndex() == clothesPreviewPageList.getTotalPage() - 1) {
                            clothesDetailActivityView.enableLoadingMore(false);
                        } else {
                            clothesDetailActivityView.enableLoadingMore(true);
                        }
                        clothesDetailActivityView.addSimilarClothes(clothesPreviewPageList.getResults());
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        clothesDetailActivityView.showListSimilarClothes();
                        clothesDetailActivityView.hideProgressSimilarClothes();
                        clothesDetailActivityView.showErrorSimilarClothes();
                    }
                });
    }

    @Override
    public void loadMoreSimilarClothes(String clothesID) {
//        clothesDetailActivityView.s();
        clothesDetailInteractor.getSimilarClothes(clothesID, currentPage + 1,
                Constants.PAGE_SIZE, new OnGetPageClothesPreviewCompleteListener() {
                    @Override
                    public void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList) {
                        currentPage++;
                        if (clothesPreviewPageList.getPageIndex() == clothesPreviewPageList.getTotalPage() - 1) {
                            clothesDetailActivityView.enableLoadingMore(false);
                        }
                        clothesDetailActivityView.hideSimilarLoadingMoreProgress();
                        clothesDetailActivityView.addSimilarClothes(clothesPreviewPageList.getResults());
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        clothesDetailActivityView.hideSimilarLoadingMoreProgress();
                    }
                });
    }


}

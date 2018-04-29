package com.kidd.store.presenter.shop.clothes_detail;

import android.content.Context;
import android.widget.Toast;

import com.kidd.store.R;
import com.kidd.store.common.Constants;

import com.kidd.store.models.ClothesPreview;
import com.kidd.store.models.PageList;
import com.kidd.store.models.body.OrderBody;
import com.kidd.store.models.body.RateClothesBody;
import com.kidd.store.models.response.ClothesViewModel;

import com.kidd.store.models.response.RateClothesViewModel;
import com.kidd.store.presenter.OnRequestCompleteListener;
import com.kidd.store.services.event_bus.OnSaveClothesEvent;
import com.kidd.store.view.shop.clothes_detail.ClothesDetailActivityView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
        clothesDetailInteractor.onViewDestroy();
    }

    @Override
    public void fetchClothesDetail(String clothesID) {
        clothesDetailActivityView.showProgress();
        clothesDetailInteractor.getClothesDetail(clothesID, new OnGetClothesDetailCompleteListener() {
            @Override
            public void onGetClothesDetailComplete(ClothesViewModel clothesViewModel) {
                clothesDetailActivityView.hideProgress();
                clothesDetailActivityView.showClothesDetail(clothesViewModel);
            }

            @Override
            public void onMessageEror(String msg) {
                clothesDetailActivityView.hideProgress();
                clothesDetailActivityView.showErrorLoading(msg);
            }
        });
    }

    @Override
    public void saveClothes(String clothesID) {
        clothesDetailActivityView.showProgress();
        clothesDetailInteractor.saveClothes(clothesID, new OnRequestCompleteListener() {
            @Override
            public void onSuccess() {
                clothesDetailActivityView.hideProgress();
                clothesDetailActivityView.switchButtonSaveJobToSaved();
                EventBus.getDefault().post(new OnSaveClothesEvent());
            }

            @Override
            public void onServerError(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void deleteSavedClothes(String clothesID) {
        clothesDetailActivityView.showProgress();
        clothesDetailInteractor.deleteSavedClothes(clothesID, new OnRequestCompleteListener() {
            @Override
            public void onSuccess() {
                clothesDetailActivityView.hideProgress();
                clothesDetailActivityView.switchButtonSaveJobToUnSaved();
                EventBus.getDefault().post(new OnSaveClothesEvent());
            }

            @Override
            public void onServerError(String message) {
                clothesDetailActivityView.hideProgress();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void firstFetchSimilarClothes(String clothesID) {
        clothesDetailActivityView.showProgressSimilarClothes();
        clothesDetailInteractor.getSimilarClothes(clothesID, 0,
                Constants.PAGE_SIZE, new OnGetPageClothesPreviewCompleteListener() {
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
                        clothesDetailActivityView.hideErrorSimilarClothes();
                        clothesDetailActivityView.refreshSimilarClothes(clothesPreviewPageList.getResults());
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
        clothesDetailActivityView.showProgressSimilarClothes();
        clothesDetailInteractor.getSimilarClothes(clothesID, currentPage + 1,
                Constants.PAGE_SIZE, new OnGetPageClothesPreviewCompleteListener() {
                    @Override
                    public void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList) {
                        currentPage++;
                        if (clothesPreviewPageList.getPageIndex() == clothesPreviewPageList.getTotalPage() - 1) {
                            clothesDetailActivityView.enableLoadingMore(false);
                        }
                        clothesDetailActivityView.hideSimilarLoadingMoreProgress();
                        clothesDetailActivityView.loadmoreSimilarClothes(clothesPreviewPageList.getResults());
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        clothesDetailActivityView.hideSimilarLoadingMoreProgress();
                    }
                });
    }

    @Override
    public void rateClothes(String clothesID, RateClothesBody rateClothesBody) {
        clothesDetailActivityView.showProgress();
        clothesDetailInteractor.rateClothes(clothesID, rateClothesBody, new OnRequestCompleteListener() {
            @Override
            public void onSuccess() {
                clothesDetailActivityView.hideProgress();
                clothesDetailActivityView.hideRatingDialog();
                getAllRateClothes(clothesID);
            }

            @Override
            public void onServerError(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                clothesDetailActivityView.hideProgress();
            }
        });
    }

    @Override
    public void getAllRateClothes(String clothesID) {
        clothesDetailInteractor.getAllRateClothes(clothesID, new OnGetPageRateClothesSuccessListener() {
            @Override
            public void onGetRateClothesSuccess(List<RateClothesViewModel> rateClothesViewModelList) {
                clothesDetailActivityView.getAllRateClothes(rateClothesViewModelList);
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void orderClothes(String clothesID, OrderBody orderBody) {
        clothesDetailActivityView.showProgress();
        clothesDetailInteractor.orderClothes(clothesID, orderBody, new OnRequestCompleteListener() {
            @Override
            public void onSuccess() {
                clothesDetailActivityView.hideProgress();
                Toast.makeText(context, context.getString(R.string.message), Toast.LENGTH_LONG).show();
                clothesDetailActivityView.payAndBackToHomeScreen();
            }

            @Override
            public void onServerError(String message) {
                clothesDetailActivityView.hideProgress();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
    }
}

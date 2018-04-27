package com.kidd.store.presenter.follow.following;

import android.content.Context;

import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.common.ToastUtils;
import com.kidd.store.models.PageList;
import com.kidd.store.models.response.SaveClothesPreview;
import com.kidd.store.view.follow.SaveClothesView;

public class SaveClothesPresenterImpl  implements SaveClothesPresenter{
    private Context context;
    private SaveClothesView saveClothesView;
    private SaveClothesInteractor saveClothesInteractor;
    int currentPage = 0;

    public SaveClothesPresenterImpl(Context context, SaveClothesView saveClothesView) {
        this.context = context;
        this.saveClothesView = saveClothesView;
        this.saveClothesInteractor = new SaveClothesInteractorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        saveClothesInteractor.onViewDestroy();
    }

    @Override
    public void refreshSaveClothes() {
        saveClothesView.showRefreshingProgress();
        saveClothesView.enableRefreshing(true);
        saveClothesInteractor.getSaveClothes(0, Constants.PAGE_SIZE, new OnGetSaveClothesSuccessListener() {
            @Override
            public void onSuccess(PageList<SaveClothesPreview> saveClothesPreviews) {
                currentPage=0;
                if (saveClothesPreviews.getPageIndex() == saveClothesPreviews.getTotalPage() - 1) {
                    saveClothesView.enableLoadMore(false);
                }
                saveClothesView.hideRefreshingProgress();
                saveClothesView.enableLoadMore(true);
                saveClothesView.refreshSaveClothes(saveClothesPreviews.getResults());
                if(saveClothesPreviews.getTotalItem()==0){
                    saveClothesView.showNoResult();
                }else {
                    saveClothesView.hideNoResult();
                }
            }

            @Override
            public void onError(String msg) {
                saveClothesView.hideNoResult();
                saveClothesView.hideRefreshingProgress();
                saveClothesView.enableRefreshing(true);
                ToastUtils.quickToast(context, msg);
            }
        });
    }

    @Override
    public void loadMoreSaveClothes() {
        saveClothesView.showLoadMoreProgress();
        saveClothesView.enableRefreshing(false);
        saveClothesInteractor.getSaveClothes(currentPage + 1, Constants.PAGE_SIZE,
                new OnGetSaveClothesSuccessListener() {
                    @Override
                    public void onSuccess(PageList<SaveClothesPreview> saveClothesPreviewPageList) {
                        currentPage++;
                        saveClothesView.hideLoadMoreProgress();
                        saveClothesView.enableRefreshing(true);
                        if (saveClothesPreviewPageList.getPageIndex() == saveClothesPreviewPageList.getTotalPage() - 1) {
                            saveClothesView.enableLoadMore(false);
                        }
                        saveClothesView.loadMoreSaveClothes(saveClothesPreviewPageList.getResults());
                    }

                    @Override
                    public void onError(String msg) {
                        saveClothesView.hideLoadMoreProgress();
                        saveClothesView.enableRefreshing(true);
                        ToastUtils.quickToast(context, R.string.error_message);
                    }
                });
    }
}

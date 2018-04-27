package com.kidd.store.presenter.shop.clothes_detail;

import android.content.Context;

import com.kidd.store.R;
import com.kidd.store.common.ResponseCode;
import com.kidd.store.presenter.OnRequestCompleteListener;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.clothes.ClothesService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by KingIT on 4/23/2018.
 */

public class ClothesDetailInteractorImpl implements ClothesDetailInteractor{
    private Context context;
    private CompositeDisposable compositeDisposable;

    public ClothesDetailInteractorImpl(Context context) {
        this.context = context;
        compositeDisposable= new CompositeDisposable();
    }
    @Override
    public void onViewDestroy() {
        this.compositeDisposable.clear();
    }
    @Override
    public void getClothesDetail(String clothesID, OnGetClothesDetailCompleteListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .getClothesViewModel(clothesID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                    switch (response.code()) {
                        case ResponseCode.OK: {
                            listener.onGetClothesDetailComplete(response.body().getData());
                            break;
                        }
                        case ResponseCode.NOT_FOUND: {
                            listener.onMessageEror(response.message());
                            break;
                        }
                    }
                }, error -> {
                    listener.onMessageEror(context.getString(R.string.server_error));
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void saveClothes(String clothesID, OnRequestCompleteListener listener) {

    }

    @Override
    public void deleteSavedClothes(String clothesID, OnRequestCompleteListener listener) {

    }

    @Override
    public void getSimilarClothes(String clothesID, int pageIndex, int pageSize,
                                  OnGetPageClothesPreviewCompleteListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .getSimilarClothesPreview(clothesID, pageIndex, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                    switch (response.code()) {
                        case ResponseCode.OK: {
                            listener.onGetPageClothesPreviewsSuccess(response.body().getData());
                            break;
                        }
                        case ResponseCode.NOT_FOUND: {
                            listener.onMessageEror(response.message());
                            break;
                        }
                    }
                }, error -> {
                    listener.onMessageEror(context.getString(R.string.server_error));
                });
        compositeDisposable.add(disposable);
    }


}

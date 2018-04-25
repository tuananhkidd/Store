package com.kidd.store.presenter.shop.list_clothes;

import android.content.Context;

import com.kidd.store.R;
import com.kidd.store.common.ResponseCode;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.clothes.ClothesService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by KingIT on 4/22/2018.
 */

public class GetClothesInteratorImpl implements GetClothesInteractor{
    private Context context;
    private CompositeDisposable compositeDisposable;

    public GetClothesInteratorImpl(Context context) {
        this.context = context;
        compositeDisposable= new CompositeDisposable();
    }

    @Override
    public void getClothesPreviews(int pageIndex, int pageSize, OnGetClothesSuccessListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .getClothesPreview(pageIndex, pageSize)
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

    @Override
    public void onViewDestroy() {
        this.compositeDisposable.clear();
    }
}

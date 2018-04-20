package com.kidd.store.presenter.profile.update_description;

import android.content.Context;

import com.kidd.store.common.ResponseCode;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.profile.UpdateDescriptionService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by La Vo Tinh on 20/04/2018.
 */

public class UpdateDescriptionInteratorImpl implements UpdateDescriptionInterator {
    Context context;
    CompositeDisposable compositeDisposable;

    public UpdateDescriptionInteratorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void updateDescription(String customerID, String description, OnUpdateDescriptionSuccessListener listener) {
        Observable<Response<ResponseBody<String>>> observable =
                ApiClient.getClient().create(UpdateDescriptionService.class).updateDescription(customerID, description);

        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onSuccess(response.body().getData());
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onError(response.message());
                                    break;
                                }
                            }
                        },
                        error -> {
                            listener.onError(error.getMessage());
                        }
                );

        compositeDisposable.add(disposable);
    }
}

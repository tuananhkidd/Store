package com.kidd.store.presenter.account.password.forget_password;

import android.content.Context;

import com.kidd.store.common.Constants;
import com.kidd.store.common.ResponseCode;
import com.kidd.store.common.Utils;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.account.ForgetPasswordService;
import com.kidd.store.view.account.password.reset_password.ForgetPasswordView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ForgetPasswordInteratorImpl implements ForgetPasswordInterator {
    Context context;
    CompositeDisposable compositeDisposable;

    public ForgetPasswordInteratorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void ForgetPassword(String email, OnSuccessListener listener) {
        String customerID = Utils.getSharePreferenceValues(context, Constants.CUSTOMER_ID);
        Observable<Response<ResponseBody<String>>> observable =
                ApiClient.getClient().create(ForgetPasswordService.class).forgetPassword(customerID, email);

        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onSuccess();
                                    break;
                                }
                                case ResponseCode.GONE: {
                                    listener.onInvalidEmail();
                                    break;
                                }
                                default: {
                                    listener.onError(response.message());
                                }
                            }
                        },
                        error -> {
                            listener.onError(error.getMessage());
                        }
                );

        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }
}

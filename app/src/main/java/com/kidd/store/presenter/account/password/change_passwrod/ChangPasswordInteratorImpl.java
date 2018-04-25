package com.kidd.store.presenter.account.password.change_passwrod;

import android.content.Context;

import com.kidd.store.common.Constants;
import com.kidd.store.common.ResponseCode;
import com.kidd.store.common.Utils;
import com.kidd.store.models.body.NewPassword;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.account.ChangePasswordService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ChangPasswordInteratorImpl implements ChangPasswordInterator {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public ChangPasswordInteratorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void changPassword(NewPassword newPassword, OnChangePasswordSuccessListener listener) {
        String customerID = Utils.getSharePreferenceValues(context, Constants.CUSTOMER_ID);
        Observable<Response<ResponseBody<String>>> observable =
                ApiClient.getClient().create(ChangePasswordService.class).changePassword(customerID,newPassword);
        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.OnChangeSuccess();
                                    break;
                                }
                                case ResponseCode.CONFLICT:{
                                    listener.OnError(response.message());
                                }
                                default:{
                                    listener.OnError(response.message());
                                }
                            }
                        },
                        error->{
                            listener.OnError(error.getMessage());
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }
}

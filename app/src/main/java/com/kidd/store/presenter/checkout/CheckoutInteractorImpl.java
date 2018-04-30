package com.kidd.store.presenter.checkout;

import android.content.Context;

import com.kidd.store.common.Constants;
import com.kidd.store.common.ResponseCode;
import com.kidd.store.common.Utils;
import com.kidd.store.models.body.SetOrderBody;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.presenter.OnRequestCompleteListener;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.checkout.CheckoutService;

import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CheckoutInteractorImpl implements CheckoutInteractor{
    private Context context;
    private CompositeDisposable compositeDisposable;

    public CheckoutInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void checkout(Set<SetOrderBody> orderBodies,OnRequestCompleteListener listener) {
        String customerID = Utils.getSharePreferenceValues(context, Constants.CUSTOMER_ID);
        Observable<Response<ResponseBody<String>>> observable = ApiClient.getClient().create(CheckoutService.class)
                .checkout(customerID,orderBodies);
        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onSuccess();
                                    break;
                                }
                                default:{
                                    listener.onServerError(response.message());
                                    break;
                                }
                            }
                        },
                        error->{
                            listener.onServerError(error.getMessage());
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }
}

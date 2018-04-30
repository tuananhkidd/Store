package com.kidd.store.presenter.history;

import android.content.Context;

import com.kidd.store.common.Constants;
import com.kidd.store.common.ResponseCode;
import com.kidd.store.common.Utils;
import com.kidd.store.models.PageList;
import com.kidd.store.models.response.OrderViewModel;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.presenter.shop.clothes_detail.OnGetPageRateClothesSuccessListener;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.history.HistoryOrderService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class HistoryOrderInteractorImpl implements HistoryOrderInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public HistoryOrderInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getAllHistoryOrder(int pageIndex, int pageSize, OnGetPageHistoryOrderSuccessListener listener) {
        String customerID = Utils.getSharePreferenceValues(context, Constants.CUSTOMER_ID);
        Observable<Response<ResponseBody<PageList<OrderViewModel>>>> observable =
                ApiClient.getClient().create(HistoryOrderService.class).getAllOrder(customerID,pageIndex,pageSize,null,null);

        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onSuccess(response.body().getData());
                                    break;
                                }
                                default:{
                                    listener.onError(response.message());
                                    break;
                                }
                            }
                        },
                        error->{
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

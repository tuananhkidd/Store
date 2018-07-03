package com.kidd.store.presenter.splash;

import android.content.Context;

import com.kidd.store.common.ResponseCode;
import com.kidd.store.common.Utils;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.splash.SplashService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class GetAppVersionInteractorImpl implements GetAppVersionInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public GetAppVersionInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getVersionApp(OnGetAppVersionSuccessListener listener) {
        Observable<Response<ResponseBody<Integer>>> observable = ApiClient.getClient().create(SplashService.class)
                .getAppVersion();
        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onGetAppVerion(response.body().getData());
                                    break;
                                }
                                default:{
                                    if(!Utils.checkNetwork(context)){
                                        listener.onNetworkConnectionError();
                                    }else {
                                        listener.onServerError();
                                    }
                                }
                            }
                        },
                        error -> {
                            if(!Utils.checkNetwork(context)){
                                listener.onNetworkConnectionError();
                            }else {
                                listener.onServerMaintain();
                            }
                        }

                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }
}

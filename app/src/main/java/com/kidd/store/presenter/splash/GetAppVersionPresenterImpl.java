package com.kidd.store.presenter.splash;

import android.app.AlertDialog;
import android.content.Context;

import com.kidd.store.R;
import com.kidd.store.common.Utils;
import com.kidd.store.view.splash.SplashView;

public class GetAppVersionPresenterImpl implements  GetAppVersionPresenter{
    Context context;
    SplashView splashView;
    GetAppVersionInteractor getAppVersionInteractor;

    public GetAppVersionPresenterImpl(Context context, SplashView splashView) {
        this.context = context;
        this.splashView = splashView;
        this.getAppVersionInteractor = new GetAppVersionInteractorImpl(context);
    }

    @Override
    public void startApp() {
        if(!splashView.isInProgress()){
            splashView.startProgress();
        }
        getAppVersionInteractor.getVersionApp(new OnGetAppVersionSuccessListener() {
            @Override
            public void onGetAppVerion(int version) {

                    splashView.completeLoading();

            }

            @Override
            public void onServerMaintain() {
                splashView.onServerMaintance();
            }

            @Override
            public void onNetworkConnectionError() {
                splashView.onNetworkError();
            }

            @Override
            public void onServerError() {
                splashView.onServerError();
            }
        });
    }

    @Override
    public void onViewDestroy() {
        getAppVersionInteractor.onViewDestroy();
    }
}

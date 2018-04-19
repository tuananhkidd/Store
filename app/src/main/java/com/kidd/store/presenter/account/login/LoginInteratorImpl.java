package com.kidd.store.presenter.account.login;

import android.content.Context;

import com.kidd.store.R;
import com.kidd.store.common.Base64UtilAccount;
import com.kidd.store.common.ResponseCode;
import com.kidd.store.models.response.HeaderProfile;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.login.LoginServices;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoginInteratorImpl implements LoginInterator {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public LoginInteratorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void login(String username, String password, OnLoginSuccessListener listener) {
        Observable<Response<ResponseBody<HeaderProfile>>> observable =
                ApiClient.getClient().create(LoginServices.class).login(Base64UtilAccount.getBase64Account(username, password));
        Disposable disposable = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onLoginSuccess(response.body().getData());
                                    break;
                                }
                                case ResponseCode.UNAUTHORIZATION: {
                                    listener.onError(context.getString(R.string.wrong_password_or_email));
                                    break;
                                }
                                case ResponseCode.FORBIDDEN: {
                                    listener.onAccounNotVerify();
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onError(context.getString(R.string.api_not_found));
                                    break;
                                }
                                default: {
                                    listener.onError(context.getString(R.string.server_error));
                                    break;
                                }
                            }
                        },
                        error -> {
                            listener.onError(context.getString(R.string.server_error));
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        context = null;
        compositeDisposable.clear();
    }
}
package com.kidd.store.presenter.account.register;

import android.content.Context;

import com.kidd.store.R;
import com.kidd.store.common.Base64UtilAccount;
import com.kidd.store.common.ResponseCode;
import com.kidd.store.models.body.CustomerRegisterBody;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.register.RegisterService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RegisterInteratorImpl implements RegisterInterator {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public RegisterInteratorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void register(String username, String password, CustomerRegisterBody body, OnRegisterCompleteListener listener) {
        Observable<Response<ResponseBody<String>>> observable = ApiClient.getClient().create(RegisterService.class)
                .CustomerRegister(Base64UtilAccount.getBase64Account(username, password), body);
        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onRegisterSuccess(response.body().getData());
                                    break;
                                }
                                case ResponseCode.CONFLICT: {
                                    listener.onAccountExist();
                                    break;
                                }
                                case ResponseCode.FORBIDDEN: {
                                    listener.onError(context.getString(R.string.password_must_be_more_than_six_letter));
                                    break;
                                }
                                default:{
                                    listener.onError(context.getString(R.string.password_must_be_more_than_six_letter));
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

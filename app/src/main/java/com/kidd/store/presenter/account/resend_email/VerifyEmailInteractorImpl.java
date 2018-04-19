package com.kidd.store.presenter.account.resend_email;

import android.content.Context;

import com.kidd.store.R;
import com.kidd.store.common.ResponseCode;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.verify_email.VerifyEmailService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class VerifyEmailInteractorImpl implements VerifyEmailInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public VerifyEmailInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void verifyEmail(String username, OnVerifyEmailSuccessListener listener) {
        Observable<Response<ResponseBody<String>>> observable =
                ApiClient.getClient().create(VerifyEmailService.class).verifyEmail(username);

        Disposable disposable = observable.observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onSuccess(username);
                                    break;
                                }
                            }
                        },
                        error->{
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

package com.kidd.store.presenter.account.login.facebook_login;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kidd.store.common.Constants;
import com.kidd.store.common.ResponseCode;
import com.kidd.store.models.body.FacebookLoginBody;
import com.kidd.store.models.model_chat.UserChat;
import com.kidd.store.models.response.HeaderProfile;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.firebase.FireBaseInstanceID;
import com.kidd.store.services.retrofit.account.LoginServices;
import com.kidd.store.view.account.login.facebook_login.FacebookLoginView;

import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FacebookLoginInteractorImpl implements FacebookLoginInteractor {
    Context context;
    CompositeDisposable compositeDisposable;

    public FacebookLoginInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void facebookLogin(FacebookLoginBody facebookLoginBody,OnFacebookLoginSuccessListener listener) {
        Observable<Response<ResponseBody<HeaderProfile>>> observable = ApiClient.getClient().create(LoginServices.class)
                .facebookRegister(facebookLoginBody, UUID.randomUUID().toString());

        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    FirebaseFirestore.getInstance().collection(Constants.USERS_COLLECTION)
                                            .document(facebookLoginBody.getFacebookUserID())
                                            .set(new UserChat(facebookLoginBody.getFacebookUserID(), facebookLoginBody.getFullname(), "ABC",facebookLoginBody.getAvatarUrl()))
                                            .addOnSuccessListener(documentReference -> listener.onLoginSuccess(response.body().getData()))
                                            .addOnFailureListener(e -> listener.onLoginError(e.getMessage()));
                                    ;

                                    break;
                                }
                                default:{
                                    listener.onLoginError(response.message());
                                    break;
                                }
                            }
                        },
                        error->{
                            listener.onLoginError(error.getMessage());
                        }
                );

        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }
}

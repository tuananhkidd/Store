package com.kidd.store.presenter.account.login;

import com.kidd.store.models.body.FacebookLoginBody;
import com.kidd.store.presenter.BaseInteractor;
import com.kidd.store.presenter.OnRequestCompleteListener;

public interface LoginInterator extends BaseInteractor {
    void login(String username,String password,OnLoginSuccessListener listener);
    void facebookLogin(String facebookUserID, OnGetFacebookLoginStateListener listener);
}

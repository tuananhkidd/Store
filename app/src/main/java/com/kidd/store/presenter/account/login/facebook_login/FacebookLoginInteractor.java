package com.kidd.store.presenter.account.login.facebook_login;

import com.kidd.store.models.body.FacebookLoginBody;
import com.kidd.store.presenter.BaseInteractor;

public interface FacebookLoginInteractor extends BaseInteractor {
    void facebookLogin(FacebookLoginBody facebookLoginBody,OnFacebookLoginSuccessListener listener);
}

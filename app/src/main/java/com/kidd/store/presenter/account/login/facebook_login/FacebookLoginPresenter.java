package com.kidd.store.presenter.account.login.facebook_login;

import com.kidd.store.models.body.FacebookLoginBody;
import com.kidd.store.presenter.BasePresenter;

public interface FacebookLoginPresenter extends BasePresenter{
    void facebookLogin(FacebookLoginBody facebookLoginBody);
}

package com.kidd.store.presenter.account.login;

import com.kidd.store.models.body.FacebookLoginBody;
import com.kidd.store.presenter.BasePresenter;

public interface LoginPresenter extends BasePresenter{
    void validateUsernameAndPassword(String username,String password);
    void validateFacebookLogin(String facebookUserID);
}

package com.kidd.store.presenter.account.login;

import com.kidd.store.presenter.BasePresenter;

public interface LoginPresenter extends BasePresenter{
    void validateUsernameAndPassword(String username,String password);
}

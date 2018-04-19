package com.kidd.store.presenter.account.login;

import com.kidd.store.presenter.BaseInteractor;

public interface LoginInterator extends BaseInteractor {
    void login(String username,String password,OnLoginSuccessListener listener);
}

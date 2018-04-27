package com.kidd.store.presenter.account.password.forget_password;

import com.kidd.store.presenter.BasePresenter;

public interface ForgetPasswordPresenter extends BasePresenter {
    void validateEmail(String email);
}

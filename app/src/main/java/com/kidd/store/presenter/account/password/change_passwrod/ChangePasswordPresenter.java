package com.kidd.store.presenter.account.password.change_passwrod;

import com.kidd.store.presenter.BasePresenter;

public interface ChangePasswordPresenter extends BasePresenter{
    void validateFeild(String oldPass,String newPass,String confirmPass);
}

package com.kidd.store.presenter.account.register;

import com.kidd.store.models.body.CustomerRegisterBody;
import com.kidd.store.presenter.BasePresenter;

public interface RegisterPresenter extends BasePresenter{
    void validateUsernameAndPassword(String username, String password, CustomerRegisterBody body);
    void validateFullName(String fullname);
    void validatePhone(String phoneNumber);
    void validateAddress(String address);

}

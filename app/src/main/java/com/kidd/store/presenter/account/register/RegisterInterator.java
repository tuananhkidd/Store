package com.kidd.store.presenter.account.register;

import com.kidd.store.models.body.CustomerRegisterBody;
import com.kidd.store.presenter.BaseInteractor;

public interface RegisterInterator extends BaseInteractor{
    void register(String username,String password,
                  CustomerRegisterBody body,
                  OnRegisterCompleteListener listener);
}

package com.kidd.store.presenter.account.password.forget_password;

import com.kidd.store.presenter.BaseInteractor;

public interface ForgetPasswordInterator extends BaseInteractor {
    void ForgetPassword(String email,OnSuccessListener listener);
}

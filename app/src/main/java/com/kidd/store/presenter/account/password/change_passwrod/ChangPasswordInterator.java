package com.kidd.store.presenter.account.password.change_passwrod;

import com.kidd.store.models.body.NewPassword;
import com.kidd.store.presenter.BaseInteractor;

import retrofit2.http.Body;

public interface ChangPasswordInterator extends BaseInteractor{
    void changPassword(NewPassword newPassword,OnChangePasswordSuccessListener listener);
}

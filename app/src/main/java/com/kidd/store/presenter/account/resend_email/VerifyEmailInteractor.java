package com.kidd.store.presenter.account.resend_email;

import com.kidd.store.presenter.BaseInteractor;

public interface VerifyEmailInteractor extends BaseInteractor {
    void verifyEmail(String username,OnVerifyEmailSuccessListener listener);
}

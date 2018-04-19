package com.kidd.store.presenter.account.resend_email;

import android.content.Context;
import android.widget.Toast;

import com.kidd.store.view.account.verify_email.VerifyEmailView;

public class VerifyPresenterImpl implements VerifyEmailPresenter {
    private Context context;
    private VerifyEmailView verifyEmailView;
    private VerifyEmailInteractor verifyEmailInteractor;

    public VerifyPresenterImpl(Context context, VerifyEmailView verifyEmailView) {
        this.context = context;
        this.verifyEmailView = verifyEmailView;
        this.verifyEmailInteractor = new VerifyEmailInteractorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        verifyEmailInteractor.onViewDestroy();
    }

    @Override
    public void VerifyEmail(String username) {
        verifyEmailView.showLoadingDialog();

        verifyEmailInteractor.verifyEmail(username, new OnVerifyEmailSuccessListener() {
            @Override
            public void onSuccess(String username) {
                verifyEmailView.hideLoadingDialog();
                Toast.makeText(context, "Successfully! Please check your mail!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

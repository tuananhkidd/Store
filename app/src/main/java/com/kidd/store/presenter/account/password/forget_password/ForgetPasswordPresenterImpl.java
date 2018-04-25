package com.kidd.store.presenter.account.password.forget_password;

import android.content.Context;
import android.widget.Toast;

import com.kidd.store.view.account.password.reset_password.ForgetPasswordView;

public class ForgetPasswordPresenterImpl implements ForgetPasswordPresenter {
    private Context context;
    private ForgetPasswordView forgetPasswordView;
    private ForgetPasswordInterator forgetPasswordInterator;

    public ForgetPasswordPresenterImpl(Context context, ForgetPasswordView forgetPasswordView) {
        this.context = context;
        this.forgetPasswordView = forgetPasswordView;
        this.forgetPasswordInterator = new ForgetPasswordInteratorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        forgetPasswordInterator.onViewDestroy();
    }

    @Override
    public void validateEmail(String email) {
        if(email.isEmpty()){
            forgetPasswordView.showEmailInputError();
            return;
        }

        forgetPasswordView.showLoadingDialog();
        forgetPasswordInterator.ForgetPassword(email, new OnSuccessListener() {
            @Override
            public void onSuccess() {
                forgetPasswordView.hideLoadingDialog();
                forgetPasswordView.backToHome();
                Toast.makeText(context, "Gửi thành công !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String msg) {
                forgetPasswordView.hideLoadingDialog();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInvalidEmail() {
                forgetPasswordView.hideLoadingDialog();
                forgetPasswordView.showEmailInputInvalid();
            }
        });
    }
}

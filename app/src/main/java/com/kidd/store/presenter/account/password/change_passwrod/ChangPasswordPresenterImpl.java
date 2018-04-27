package com.kidd.store.presenter.account.password.change_passwrod;

import android.content.Context;
import android.widget.Toast;

import com.kidd.store.R;
import com.kidd.store.models.body.NewPassword;
import com.kidd.store.view.account.password.change_password.ChangePasswordView;

public class ChangPasswordPresenterImpl implements ChangePasswordPresenter {
    private Context context;
    private ChangePasswordView changePasswordView;
    private ChangPasswordInterator changPasswordInterator;

    public ChangPasswordPresenterImpl(Context context, ChangePasswordView changePasswordView) {
        this.context = context;
        this.changePasswordView = changePasswordView;
        this.changPasswordInterator = new ChangPasswordInteratorImpl(context);
    }

    @Override
    public void validateFeild(String oldPass, String newPass, String confirmPass) {
        if (oldPass.isEmpty()) {
            changePasswordView.showOldPassError();
            return;
        }
        if (newPass.isEmpty()) {
            changePasswordView.showNewPassError();
            return;
        }
        if (confirmPass.isEmpty()) {
            changePasswordView.showConfirmPassError();
            return;
        }
        if (!confirmPass.matches(newPass)) {
            changePasswordView.showConfirmPasNotMatch();
            return;
        }

        changePasswordView.showLoadingDialog();
        NewPassword newPassword = new NewPassword(oldPass, newPass);
        changPasswordInterator.changPassword(newPassword, new OnChangePasswordSuccessListener() {
            @Override
            public void OnChangeSuccess() {
                changePasswordView.hideLoadingDialog();
                changePasswordView.backToHomeScreen();
                Toast.makeText(context, context.getString(R.string.change_pass_success), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnError(String msg) {
                changePasswordView.hideLoadingDialog();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onViewDestroy() {
        changPasswordInterator.onViewDestroy();
    }
}

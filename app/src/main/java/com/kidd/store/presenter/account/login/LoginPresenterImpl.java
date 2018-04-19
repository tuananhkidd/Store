package com.kidd.store.presenter.account.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.common.Utils;
import com.kidd.store.models.response.HeaderProfile;
import com.kidd.store.view.account.login.LoginView;

public class LoginPresenterImpl implements LoginPresenter {
    private Context context;
    private LoginView loginView;
    private LoginInterator loginInterator;

    public LoginPresenterImpl(Context context, LoginView loginView) {
        this.context = context;
        this.loginView = loginView;
        this.loginInterator = new LoginInteratorImpl(context);
    }

    @Override
    public void validateUsernameAndPassword(String username, String password) {
        if(username.isEmpty()){
            loginView.showUserNameError();
            return;
        }
        if(password.isEmpty()){
            loginView.showPasswordError();
            return;
        }

        loginView.showLoadingDialog();
        loginInterator.login(username, password, new OnLoginSuccessListener() {
            @Override
            public void onLoginSuccess(HeaderProfile headerProfile) {
                Utils.setSharePreferenceValues(context, Constants.STATUS_LOGIN,Constants.LOGIN_TRUE);
                Utils.setSharePreferenceValues(context, Constants.CUSTOMER_ID,headerProfile.getCustomerID());
                Utils.saveHeaderProfile(context,headerProfile);
                loginView.hideLoadingDialog();
                loginView.backToHomeScreen(headerProfile,Activity.RESULT_OK);
            }

            @Override
            public void onAccounNotVerify() {
                loginView.hideLoadingDialog();
                Toast.makeText(context, context.getString(R.string.account_not_verify), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                loginView.hideLoadingDialog();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onViewDestroy() {
        loginInterator.onViewDestroy();
    }
}
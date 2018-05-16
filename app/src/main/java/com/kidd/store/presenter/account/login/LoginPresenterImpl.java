package com.kidd.store.presenter.account.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.common.ToastUtils;
import com.kidd.store.common.UserAuth;
import com.kidd.store.common.Utils;
import com.kidd.store.models.body.FacebookLoginBody;
import com.kidd.store.models.response.HeaderProfile;
import com.kidd.store.services.event_bus.HeaderProfileEvent;
import com.kidd.store.services.event_bus.UserAuthorizationChangedEvent;
import com.kidd.store.view.account.login.LoginView;

import org.greenrobot.eventbus.EventBus;

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
        if (username.isEmpty()) {
            loginView.showUserNameError();
            return;
        }
        if (password.isEmpty()) {
            loginView.showPasswordError();
            return;
        }

        loginView.showLoadingDialog();
        loginInterator.login(username, password, new OnLoginSuccessListener() {
            @Override
            public void onLoginSuccess(HeaderProfile headerProfile) {
                Utils.setSharePreferenceValues(context, Constants.STATUS_LOGIN, Constants.LOGIN_TRUE);
                Utils.setSharePreferenceValues(context, Constants.CUSTOMER_ID, headerProfile.getCustomerID());
                UserAuth.saveLoginState(context, username);
                Utils.saveHeaderProfile(context, headerProfile);
                loginView.hideLoadingDialog();
                EventBus.getDefault().post(new UserAuthorizationChangedEvent());
                loginView.backToHomeScreen(headerProfile, Activity.RESULT_OK);
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
    public void validateFacebookLogin(String facebookUserID) {
        loginView.showLoadingDialog();
        loginInterator.facebookLogin(facebookUserID, new OnGetFacebookLoginStateListener() {
            @Override
            public void onGetState(Object o) {
                loginView.hideLoadingDialog();
                try {
                    HeaderProfile headerProfile = new HeaderProfile();
                    String customerID = (String) ((LinkedTreeMap) o).get("customerID");
                    String fullName = (String) ((LinkedTreeMap) o).get("fullName");
                    String email = (String) ((LinkedTreeMap) o).get("email");
                    String avatarUrl = (String) ((LinkedTreeMap) o).get("avatarUrl");
                    headerProfile.setCustomerID(customerID);
                    headerProfile.setFullName(fullName);
                    headerProfile.setEmail(email);
                    headerProfile.setAvatarUrl(avatarUrl);
                    EventBus.getDefault().post(new HeaderProfileEvent(headerProfile));
                    Utils.setSharePreferenceValues(context, Constants.STATUS_LOGIN, Constants.LOGIN_TRUE);
                    Utils.setSharePreferenceValues(context, Constants.CUSTOMER_ID, headerProfile.getCustomerID());
                    Utils.saveHeaderProfile(context, headerProfile);
                    UserAuth.saveLoginState(context, email);
                    loginView.backToHomeScreen(headerProfile, Activity.RESULT_OK);
                } catch (Exception e) {
                    loginView.goToVerifyFacebookAccount();

                }
            }

            @Override
            public void onError(String msg) {
                ToastUtils.quickToast(context, msg
                );
            }
        });
    }

    @Override
    public void onViewDestroy() {
        loginInterator.onViewDestroy();
    }
}

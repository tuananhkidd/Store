package com.kidd.store.presenter.account.login.facebook_login;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.kidd.store.common.Constants;
import com.kidd.store.common.Utils;
import com.kidd.store.models.body.FacebookLoginBody;
import com.kidd.store.models.response.HeaderProfile;
import com.kidd.store.services.event_bus.HeaderProfileEvent;
import com.kidd.store.view.account.login.facebook_login.FacebookLoginView;

import org.greenrobot.eventbus.EventBus;

public class FacebookLoginPresenterImpl implements FacebookLoginPresenter {
    Context context;
    FacebookLoginView facebookLoginView;
    FacebookLoginInteractor facebookLoginInteractor;

    public FacebookLoginPresenterImpl(Context context, FacebookLoginView facebookLoginView) {
        this.context = context;
        this.facebookLoginView = facebookLoginView;
        this.facebookLoginInteractor = new FacebookLoginInteractorImpl(context);
    }

    @Override
    public void facebookLogin(FacebookLoginBody facebookLoginBody) {
        if(facebookLoginBody.getEmail().isEmpty()){
            facebookLoginView.showEmailError();
            return;
        }

        if(!Utils.isEmailValid(facebookLoginBody.getEmail())){
            facebookLoginView.showEmailInvalid();
            return;
        }

        facebookLoginView.showLoadingDialog();

        facebookLoginInteractor.facebookLogin(facebookLoginBody, new OnFacebookLoginSuccessListener() {
            @Override
            public void onLoginSuccess(HeaderProfile headerProfile) {
                facebookLoginView.hideLoadingDialog();
                EventBus.getDefault().post(new HeaderProfileEvent(headerProfile));
                Utils.setSharePreferenceValues(context, Constants.STATUS_LOGIN, Constants.LOGIN_TRUE);
                Utils.setSharePreferenceValues(context, Constants.CUSTOMER_ID, headerProfile.getCustomerID());
                Utils.saveHeaderProfile(context, headerProfile);
                facebookLoginView.backToHomeScreen(headerProfile, Activity.RESULT_OK);
            }

            @Override
            public void onLoginError(String msg) {
                facebookLoginView.hideLoadingDialog();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onViewDestroy() {
        facebookLoginInteractor.onViewDestroy();
    }
}

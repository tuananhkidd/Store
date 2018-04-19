package com.kidd.store.presenter.profile;

import android.content.Context;
import android.widget.Toast;

import com.kidd.store.R;
import com.kidd.store.models.response.Profile;
import com.kidd.store.view.profile.ProfileView;

public class ProfilePresenterImpl implements ProfilePresenter {
    Context context;
    ProfileInterator profileInterator;
    ProfileView profileView;

    public ProfilePresenterImpl(Context context, ProfileView profileView) {
        this.context = context;
        this.profileView = profileView;
        this.profileInterator = new ProfileInteratorImpl(context);
    }

    @Override
    public void getProfile(String customerID) {
        profileView.showLoadingDialog();

        profileInterator.getProfile(customerID, new OnGetProfileSuccessListener() {
            @Override
            public void onSuccess(Profile profile) {
                profileView.hideLoadingDialog();
                profileView.showProfile(profile);
            }

            @Override
            public void onError(String message) {
                profileView.hideLoadingDialog();
                Toast.makeText(context, context.getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewDestroy() {
        profileInterator.onViewDestroy();
    }
}

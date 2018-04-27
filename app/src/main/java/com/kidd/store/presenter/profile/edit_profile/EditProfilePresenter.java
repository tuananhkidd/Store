package com.kidd.store.presenter.profile.edit_profile;

import android.net.Uri;

import com.kidd.store.presenter.BasePresenter;

public interface EditProfilePresenter extends BasePresenter{
    void validateProfile(Uri uri,
                         String fullName,
                         String phone,
                         String address,
                         String identityCard,
                         String avatarUrl,
                         int gender,
                         long birthday,
                         String email
    );
}

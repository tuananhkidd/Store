package com.kidd.store.presenter.profile.edit_profile;

import com.kidd.store.models.body.ProfileBody;
import com.kidd.store.presenter.BaseInteractor;

public interface EditProfileInterator extends BaseInteractor {
    void updateProfile(ProfileBody profileBody, OnEditSuccessListener listener);
}

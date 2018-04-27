package com.kidd.store.presenter.profile.edit_profile;

import com.kidd.store.models.response.Profile;

public interface OnEditSuccessListener {
    void onSuccess(Profile profile);
    void onError(String message);
}

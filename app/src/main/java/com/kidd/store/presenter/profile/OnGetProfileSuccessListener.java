package com.kidd.store.presenter.profile;

import com.kidd.store.models.response.Profile;

public interface OnGetProfileSuccessListener {
    void onSuccess(Profile profile);
    void onError(String message);
}

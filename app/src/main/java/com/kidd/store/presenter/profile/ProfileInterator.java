package com.kidd.store.presenter.profile;

import com.kidd.store.presenter.BaseInteractor;

public interface ProfileInterator extends BaseInteractor {
    void getProfile(String customerID,OnGetProfileSuccessListener listener);
}

package com.kidd.store.presenter.splash;

import com.kidd.store.presenter.BaseInteractor;

public interface GetAppVersionInteractor extends BaseInteractor {
    void getVersionApp(OnGetAppVersionSuccessListener listener);
}

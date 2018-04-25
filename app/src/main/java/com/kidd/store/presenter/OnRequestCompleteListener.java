package com.kidd.store.presenter;

/**
 * Created by TranThanhTung on 03/02/2018.
 */

public interface OnRequestCompleteListener {
    void onSuccess();
    void onServerError(String message);
}

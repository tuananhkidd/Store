package com.kidd.store.services.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.kidd.store.common.Constants;
import com.kidd.store.common.Utils;

public class FireBaseInstanceID extends FirebaseMessagingService {
    private static final String TAG = FireBaseInstanceID.class.getSimpleName();

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Utils.setSharePreferenceValues(this,"firebase_token",s);
        FirebaseMessaging.getInstance().subscribeToTopic(s);
    }

//    @Override
//    public void onTokenRefresh() {
//        super.onTokenRefresh();
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.i(TAG, refreshedToken);
////        FirebaseMessaging.getInstance().subscribeToTopic(Constants.FIREBASE_NOTIFICATION_TOPIC_GLOBAL);
//    }
}


package com.kidd.store.services.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.view.shop.clothes_detail.ClothesDetailActivity;

import org.json.JSONObject;

import java.util.Map;

public class FireBaseMessaging extends FirebaseMessagingService {

    private static final String TAG = FireBaseMessaging.class.getSimpleName();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        JSONObject objectData = new JSONObject(data);
        Log.i(TAG, "onMessageReceived: "+objectData.toString());
        try {
            String id = objectData.getString("id");
            buildNoti(id);
        }catch (Exception e){
            Log.i(TAG, "error: "+e.getCause());
        }

    }

    public void buildNoti(String clothesID) {
        String contentText = "Store";
        String contentTitle = "Store vừa thêm 1 mặt hàng mới";
        Intent intent = new Intent(this, ClothesDetailActivity.class);
        intent.putExtra(Constants.KEY_CLOTHES_ID,clothesID);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logoapp)
                        .setContentTitle(contentText)
                        .setContentText(contentTitle)
                        .setContentIntent(pendingIntent)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(contentTitle))
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setAutoCancel(true);
        final Notification notification = mBuilder.build();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, notification);
    }

}

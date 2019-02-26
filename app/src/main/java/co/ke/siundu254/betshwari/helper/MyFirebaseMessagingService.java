package co.ke.siundu254.betshwari.helper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by rahmak on 3/5/18
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String mTitle, mBody, mClickAction, pushId, pushTips, pushTitle, pushStatus;

        if (remoteMessage.getNotification() != null) {
            mTitle = remoteMessage.getNotification().getTitle();
            mBody = remoteMessage.getNotification().getBody();
            mClickAction = remoteMessage.getNotification().getClickAction();

            pushId = remoteMessage.getData().get("pushId");
            pushTitle = remoteMessage.getData().get("pushTitle");
            pushStatus = remoteMessage.getData().get("pushStatus");
            pushTips = remoteMessage.getData().get("pushTips");

            sendNotification(mTitle, mBody, mClickAction, pushId, pushTitle, pushStatus, pushTips);
        }
    }

    private void sendNotification(String mTitle, String mBody, String mClickAction, String pushId, String pushTitle, String pushStatus, String pushTips) {
        Intent mIntent = new Intent(mClickAction);
        mIntent.putExtra("pushId", pushId);
        mIntent.putExtra("pushTitle", pushTitle);
        mIntent.putExtra("pushStatus", pushStatus);
        mIntent.putExtra("pushTips", pushTips);
        PendingIntent intent = PendingIntent.getActivity(
                this,
                0,
                mIntent,
                PendingIntent.FLAG_ONE_SHOT
        );

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(co.ke.siundu254.betshwari.R.drawable.ic_launcher)
                .setContentIntent(intent)
                .setContentTitle(mTitle)
                .setContentText(mBody)
                .setSound(defaultSoundUri);

        builder.setContentIntent(intent);

        int mNotifyId = (int) System.currentTimeMillis();

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(mNotifyId, builder.build());
    }
}

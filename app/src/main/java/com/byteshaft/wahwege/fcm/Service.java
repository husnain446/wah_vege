package com.byteshaft.wahwege.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.byteshaft.wahwege.MainActivity;
import com.byteshaft.wahwege.R;
import com.byteshaft.wahwege.utils.AppGlobals;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONObject;

import static com.byteshaft.wahwege.utils.AppGlobals.sImageLoader;


public class Service extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i("DATA" + "good", remoteMessage.getData().toString());
        sendNotification(remoteMessage.getData().get("text"), "Promotions");
    }

    private void sendNotification(String messageBody, String appName) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("promotion", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.vegetables)
                .showImageOnLoading(R.mipmap.vegetables)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheInMemory(false)
                .cacheOnDisc(false).considerExifParams(true).build();
//        Bitmap bitmap = sImageLoader.loadImageSync(AppGlobals.SERVER_IP + photo, options);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.vegetables))
                .setSmallIcon(R.mipmap.vegetables)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setContentTitle(appName)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }
}

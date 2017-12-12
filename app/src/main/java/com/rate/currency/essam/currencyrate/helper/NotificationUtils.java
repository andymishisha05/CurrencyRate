package com.rate.currency.essam.currencyrate.helper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.rate.currency.essam.currencyrate.NavActivity;
import com.rate.currency.essam.currencyrate.R;

import java.io.IOException;



public class NotificationUtils {
    public static void setIDOnPrefernces(Context context, String id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("0", id);
        editor.apply();
    }

    public static void setNewID(Context context, String id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("1", id);
        editor.apply();
    }

    public static String getID(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String id = sp.getString("0", "");
        return id;
    }

    public static String getNewID(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String id = sp.getString("1", "");


        return id;
    }

    public static void notifyUserOfNews(Context context) throws IOException {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Bitmap image = BitmapFactory.decodeStream(ImgURL.openConnection().getInputStream());
        Intent i = new Intent(context, NavActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
               // .setContentTitle(getText())
                //.setContentText(getTile())
                .setSmallIcon(R.drawable.ic_lancher)
                //  .setLargeIcon(image)
                .setSound(alarmSound)
                .setAutoCancel(true);


        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntentWithParentStack(i);
        PendingIntent resultPendingIntent = taskStackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(4444, builder.build());

    }


}

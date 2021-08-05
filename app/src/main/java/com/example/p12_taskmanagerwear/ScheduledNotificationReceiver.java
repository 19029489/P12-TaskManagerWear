package com.example.p12_taskmanagerwear;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import java.util.ArrayList;

public class ScheduledNotificationReceiver extends BroadcastReceiver {
    ArrayList<Task> al = new ArrayList<Task>();

    @Override
    public void onReceive(Context context, Intent intent) {

        long id = intent.getLongExtra("id", 0);
        String task_name = intent.getStringExtra("task_name");
        String task_desc = intent.getStringExtra("task_desc");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("This is for default notification");

            channel.enableVibration(true);
            channel.enableLights(true);

            notificationManager.createNotificationChannel(channel);
        }

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, Integer.parseInt("" + id), i, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Launch Task Manager", pIntent).build();

        Intent intentreply = new Intent(context, MainActivity.class);
        intentreply.putExtra("id", id);

        PendingIntent pendingIntentReply = PendingIntent.getActivity(context, 0, intentreply, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput ri = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String[] {"Completed", "Not yet"})
                .build();

        NotificationCompat.Action action2 = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Reply", pendingIntentReply)
                .addRemoteInput(ri)
                .build();

        NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender();
        extender.addAction(action);
        extender.addAction(action2);

        // build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setContentTitle("Task");
        builder.setContentText(task_name + "\n" + task_desc);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);

        builder.extend(extender);

        //set vibrate
        builder.setVibrate(new long[] { 500, 500 });

        //LED lights
        builder.setLights(Color.RED, 3000, 3000);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);

        builder.setPriority(Notification.PRIORITY_HIGH);

        Notification n = builder.build();
        notificationManager.notify(Integer.parseInt("" + id), n);

    }
}
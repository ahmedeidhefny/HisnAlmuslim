package com.ahmed.hisnalmuslimapp.ui.hadithsList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.ahmed.hisnalmuslimapp.Constants;

public class ZkrAlarmReceiver extends BroadcastReceiver {

    public String collectionName ;


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        collectionName = intent.getExtras().getString(Constants.COLLECTION_NAME);
        NotificationHelper notificationHelper = new NotificationHelper(context);
        notificationHelper.setCollectionName(collectionName);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());

        throw new UnsupportedOperationException("Not yet implemented");


    }
}

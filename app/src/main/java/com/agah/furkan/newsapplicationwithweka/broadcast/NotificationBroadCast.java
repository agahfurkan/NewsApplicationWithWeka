package com.agah.furkan.newsapplicationwithweka.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.agah.furkan.newsapplicationwithweka.service.WekaClusteringService;
import timber.log.Timber;

public class NotificationBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {//recommendation notification
        Timber.tag("timber").d("NotificationBroadCast triggered");
        Intent clusteringService = new Intent(context, WekaClusteringService.class);
        WekaClusteringService.enqueueWork(context, clusteringService);
    }
}

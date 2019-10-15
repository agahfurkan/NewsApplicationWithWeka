package com.agah.furkan.newsapplicationwithweka.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.agah.furkan.newsapplicationwithweka.service.WekaClassificationService;
import timber.log.Timber;

public class WekaBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {//Classification model update
        Timber.tag("timber").d("WekaBroadCast triggered");
        Intent classificationService = new Intent(context, WekaClassificationService.class);
        WekaClassificationService.enqueueWork(context, classificationService);
    }
}

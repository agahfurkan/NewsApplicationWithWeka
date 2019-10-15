package com.agah.furkan.newsapplicationwithweka.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import com.agah.furkan.newsapplicationwithweka.broadcast.NotificationBroadCast;
import com.agah.furkan.newsapplicationwithweka.broadcast.WekaBroadCast;
import timber.log.Timber;

public class AlarmManagerHelper {
    static public void setNotificationAlarm(Context context, int hour, int minute) { //Clustering
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationBroadCast.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmRunning) {
            PendingIntent pintent = PendingIntent.getBroadcast(context, 0, intent, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            long TimeUntilTrigger;
            if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
                TimeUntilTrigger = calendar.getTimeInMillis() + 86400000;
            } else {
                TimeUntilTrigger = calendar.getTimeInMillis();
            }
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, TimeUntilTrigger, AlarmManager.INTERVAL_DAY, pintent);
            Timber.tag("timber").d("New notification alarm set.");
        } else {
            Timber.tag("timber").d("Notification alarm already up.");
        }
    }

    static public void setWekaModelUpdateAlarm(Context context, int hour, int minute) {  //Updating dataset,model and filter
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, WekaBroadCast.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmRunning) {
            PendingIntent pintent = PendingIntent.getBroadcast(context, 1, intent, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            long TimeUntilTrigger;
            if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
                TimeUntilTrigger = calendar.getTimeInMillis() + 86400000;
            } else {
                TimeUntilTrigger = calendar.getTimeInMillis();
            }
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, TimeUntilTrigger, AlarmManager.INTERVAL_DAY, pintent);
            Timber.tag("timber").d("New weka update alarm set.");
        } else {
            Timber.tag("timber").d("Weka update alarm already up.");
        }
    }

    static public void cancelNotificationAlarm(Context context) {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationBroadCast.class);
        PendingIntent pintent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarm.cancel(pintent);
        pintent.cancel();
        Timber.tag("timber").d("Notification Alarm Cancelled.");
    }

    static public void cancelModelAlarm(Context context) {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, WekaBroadCast.class);
        PendingIntent pintent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarm.cancel(pintent);
        pintent.cancel();
        Timber.tag("timber").d("Model Alarm Cancelled.");
    }
}

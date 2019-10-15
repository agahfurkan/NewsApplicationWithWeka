package com.agah.furkan.newsapplicationwithweka.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtil {
    public static void showToastInThread(final String message, final Activity activity) {
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(activity, message, Toast.LENGTH_SHORT).show());
    }

    public static void showToast(String message, Activity activity) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}

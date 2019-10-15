package com.agah.furkan.newsapplicationwithweka.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.agah.furkan.newsapplicationwithweka.GlobalCons;

public class PermissionUtil {
    public static void checkPerms(Context context, Activity activity) {
        for (String perms : GlobalCons.PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, perms) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, GlobalCons.PERMISSIONS, GlobalCons.PERM_REQUEST_CODE);
            }
        }
    }
}

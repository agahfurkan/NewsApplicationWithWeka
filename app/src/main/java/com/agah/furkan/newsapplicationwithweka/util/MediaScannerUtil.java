package com.agah.furkan.newsapplicationwithweka.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class MediaScannerUtil {
    public static void scan(File myFile, Activity activity) {
        Uri fileContentUri = Uri.fromFile(myFile);
        Intent mediaScannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, fileContentUri);
        activity.sendBroadcast(mediaScannerIntent);
    }
}

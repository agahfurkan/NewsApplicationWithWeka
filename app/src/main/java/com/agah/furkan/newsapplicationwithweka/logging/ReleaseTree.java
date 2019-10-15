package com.agah.furkan.newsapplicationwithweka.logging;

import android.annotation.SuppressLint;
import android.os.Environment;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

public class ReleaseTree extends Timber.Tree {
    @Override
    @SuppressLint("SimpleDateFormat")
    protected void log(int priority, @Nullable String tag, String message, @Nullable Throwable t) {
        String folder_main = "NewsApplicationWithWeka";
        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        File logFile = new File(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/log.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(logFile, true);
            fileOutputStream.write(("Date-Time: " + format + " Priority: " + priority + " Tag: " + tag + " Message: " + message + "\n").getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Timber.tag("timber").d("Error Filenotfound in ReleaseTree");
            e.printStackTrace();
        } catch (IOException e) {
            Timber.tag("timber").d("Error IO in ReleaseTree");
            e.printStackTrace();
        }
    }
}

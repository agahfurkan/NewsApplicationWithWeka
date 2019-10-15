package com.agah.furkan.newsapplicationwithweka.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.agah.furkan.newsapplicationwithweka.GlobalCons;
import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.util.PermissionUtil;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GlobalCons.PERM_REQUEST_CODE: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean checkBox = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            checkBox = shouldShowRequestPermissionRationale(permission);
                        }
                        if (!checkBox) {
                            Toast.makeText(this, R.string.permission_toast, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, 2);
                        } else {
                            PermissionUtil.checkPerms(this, this);
                        }
                    }
                }
            }
        }
    }
}

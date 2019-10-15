package com.agah.furkan.newsapplicationwithweka.util;

import android.app.Activity;
import android.content.Context;

import com.agah.furkan.newsapplicationwithweka.GlobalCons;
import com.agah.furkan.newsapplicationwithweka.data.local.sharedpref.SharedPrefs;
import com.agah.furkan.newsapplicationwithweka.data.local.sharedpref.model.UserModel;

public class UserManager {
    public static void setUserInfo(Activity activity, UserModel userInfo) {
        SharedPrefs sharedPrefs = new SharedPrefs(activity);
        UserModel userModel = new UserModel();
        userModel.setUsername(userInfo.getUsername());
        userModel.setEmail(userInfo.getEmail());
        userModel.setUId(userInfo.getUId());
        userModel.setPhotoUrl(userInfo.getPhotoUrl());
        sharedPrefs.setUserInfo(userModel);
    }

    public static UserModel getUserInfo(Activity activity) {
        SharedPrefs sharedPrefs = new SharedPrefs(activity);
        return sharedPrefs.getUserInfo();
    }

    public static UserModel getUserInfo(Context context) {
        SharedPrefs sharedPrefs = new SharedPrefs(context);
        return sharedPrefs.getUserInfo();
    }

    public static String getEmail(Activity activity) {
        SharedPrefs sharedPrefs = new SharedPrefs(activity);
        return sharedPrefs.getFromPrefs(GlobalCons.SHARED_PREF_EMAIL_KEY);
    }
}

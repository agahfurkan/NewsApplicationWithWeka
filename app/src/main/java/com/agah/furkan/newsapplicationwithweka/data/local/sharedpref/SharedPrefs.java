package com.agah.furkan.newsapplicationwithweka.data.local.sharedpref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.agah.furkan.newsapplicationwithweka.GlobalCons;
import com.agah.furkan.newsapplicationwithweka.data.local.sharedpref.model.UserModel;


public class SharedPrefs {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String SHARED_PREF_NAME = "UserInfo";

    public SharedPrefs(Activity activity) {
        sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, 0);
        editor = sharedPreferences.edit();
    }

    public SharedPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, 0);
        editor = sharedPreferences.edit();
    }

    public void clearPref() {
        editor.clear();
        editor.commit();
    }

    public String getFromPrefs(String key) {
        return sharedPreferences.getString(key, null);
    }

    public UserModel getUserInfo() {
        UserModel userModel = new UserModel();
        userModel.setUsername(getFromPrefs(GlobalCons.SHARED_PREF_USERNAME_KEY));
        userModel.setEmail(getFromPrefs(GlobalCons.SHARED_PREF_EMAIL_KEY));
        userModel.setUId(getFromPrefs(GlobalCons.SHARED_PREF_UID_KEY));
        userModel.setPhotoUrl(getFromPrefs(GlobalCons.SHARED_PREF_USERPHOTOURL_KEY));
        return userModel;
    }

    public void setUserInfo(UserModel userInfo) {
        editor.putString(GlobalCons.SHARED_PREF_USERNAME_KEY, userInfo.getUsername());
        editor.putString(GlobalCons.SHARED_PREF_EMAIL_KEY, userInfo.getEmail());
        editor.putString(GlobalCons.SHARED_PREF_UID_KEY, userInfo.getUId());
        editor.putString(GlobalCons.SHARED_PREF_USERPHOTOURL_KEY, userInfo.getPhotoUrl());
        editor.commit();
    }
}

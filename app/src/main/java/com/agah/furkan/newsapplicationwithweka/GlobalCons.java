package com.agah.furkan.newsapplicationwithweka;

import android.Manifest;

public class GlobalCons {
    public static final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final int PERM_REQUEST_CODE = 0;//request permission code
    public static final String NEWS_API_BASE_URL = "https://newsapi.org";
    public static final String BOOKS_API_BASE_URL = "https://www.googleapis.com";
    public static final int SPLASH_OUT_TIME = 5000;
    public static final String SHARED_PREF_EMAIL_KEY = "U_EMAIL";//shared preferences token key
    public static final String SHARED_PREF_USERNAME_KEY = "U_USERNAME";//shared preferences username key
    public static final String SHARED_PREF_UID_KEY = "U_UID";//shared preferences username key
    public static final String SHARED_PREF_USERPHOTOURL_KEY = "U_PHOTOURL";//shared preferences username key
    public static final String CUSTOM_FRAGMENT_DIALOG_TAG = "C_DIALOG_TAG";//fragment dialog transaction tag
    public static final String FIREBASE_CLIENT_ID = BuildConfig.firebaseClientKey;
    public static final String NEWSAPI_API_KEY = BuildConfig.newsApiKey;
}

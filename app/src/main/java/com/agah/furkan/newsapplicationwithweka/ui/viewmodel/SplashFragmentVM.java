package com.agah.furkan.newsapplicationwithweka.ui.viewmodel;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.agah.furkan.newsapplicationwithweka.GlobalCons;


public class SplashFragmentVM extends ViewModel {
    private MutableLiveData<String> liveData;

    public SplashFragmentVM() {
        setTimer();
    }

    public MutableLiveData<String> getLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }

    public void setTimer() {
        Handler handler = new Handler();
        handler.postDelayed(() -> liveData.setValue("Done"), GlobalCons.SPLASH_OUT_TIME);
    }
}
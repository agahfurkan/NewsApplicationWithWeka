package com.agah.furkan.newsapplicationwithweka;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.agah.furkan.newsapplicationwithweka.ui.viewmodel.NewsFragmentVM;


public class ViewModelFactory implements ViewModelProvider.Factory {
    private String category;
    private Application application;

    public ViewModelFactory(Application application, String category) {
        this.category = category;
        this.application = application;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewsFragmentVM.class)) {
            return (T) new NewsFragmentVM(application, category);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}

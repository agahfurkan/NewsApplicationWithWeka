package com.agah.furkan.newsapplicationwithweka.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import com.agah.furkan.newsapplicationwithweka.data.local.room.NewsRepository;
import com.agah.furkan.newsapplicationwithweka.data.local.room.RoomArticleModel;

public class RecommendedNewsVM extends AndroidViewModel {
    private LiveData<List<RoomArticleModel>> newsListLiveData;

    public RecommendedNewsVM(@NonNull Application application) {
        super(application);
        newsListLiveData = new MutableLiveData<>();
        NewsRepository newsRepository = new NewsRepository(application);
        newsListLiveData = newsRepository.getAllNews();
    }

    public LiveData<List<RoomArticleModel>> getLiveData() {
        return newsListLiveData;
    }
}

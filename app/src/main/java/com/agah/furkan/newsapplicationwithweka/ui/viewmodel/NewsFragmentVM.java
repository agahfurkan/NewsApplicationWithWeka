package com.agah.furkan.newsapplicationwithweka.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.agah.furkan.newsapplicationwithweka.GlobalCons;
import com.agah.furkan.newsapplicationwithweka.data.web.INewsRetrofit;
import com.agah.furkan.newsapplicationwithweka.data.web.ModelForNewsRequest;
import com.agah.furkan.newsapplicationwithweka.data.web.RestApi;
import com.agah.furkan.newsapplicationwithweka.util.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsFragmentVM extends AndroidViewModel {
    private MutableLiveData<ModelForNewsRequest> newsListLiveData;

    public NewsFragmentVM(@NonNull Application application, String category) {
        super(application);
        newsListLiveData = new MutableLiveData<>();
        final StringBuilder categoriesToFilter = new StringBuilder();
        final Map<String, String> retrofitParams = new HashMap<>();
        if (category.equals("For you")) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users/" + UserManager.getUserInfo(application).getUId());
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (int x = 0; x < dataSnapshot.getChildrenCount(); x++) {
                        categoriesToFilter.append("\"");
                        categoriesToFilter.append(Objects.requireNonNull(dataSnapshot.child(String.valueOf(x)).getValue()).toString());
                        categoriesToFilter.append("\"");
                        if (x != dataSnapshot.getChildrenCount() - 1) {
                            categoriesToFilter.append("OR");
                        }
                    }

                    retrofitParams.put("q", categoriesToFilter.toString());
                    retrofitParams.put("language", "en");
                    retrofitParams.put("sortBy", "publishedAt");
                    retrofitParams.put("pageSize", "100");
                    retrofitParams.put("apiKey", GlobalCons.NEWSAPI_API_KEY);
                    connectToService(RestApi.getNewsClient().create(INewsRetrofit.class), retrofitParams);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            getTopNews(RestApi.getNewsClient().create(INewsRetrofit.class), category);
        }
    }


    public LiveData<ModelForNewsRequest> getLiveData() {
        return newsListLiveData;
    }

    private void connectToService(INewsRetrofit INewsRetrofit, Map<String, String> params) {
        INewsRetrofit.getNews(params).enqueue(new Callback<ModelForNewsRequest>() {
            @Override
            public void onResponse(@NonNull Call<ModelForNewsRequest> call, @NonNull Response<ModelForNewsRequest> response) {
                if (response.body() != null) {
                    newsListLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelForNewsRequest> call, @NonNull Throwable t) {
            }
        });
    }

    private void getTopNews(INewsRetrofit INewsRetrofit, String category) {
        INewsRetrofit.getTopNews(category).enqueue(new Callback<ModelForNewsRequest>() {
            @Override
            public void onResponse(@NonNull Call<ModelForNewsRequest> call, @NonNull Response<ModelForNewsRequest> response) {
                if (response.body() != null) {
                    newsListLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelForNewsRequest> call, @NonNull Throwable t) {
            }
        });
    }
}

package com.agah.furkan.newsapplicationwithweka.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.agah.furkan.newsapplicationwithweka.data.web.IBooksRetrofit;
import com.agah.furkan.newsapplicationwithweka.data.web.ModelForBooksRequest;
import com.agah.furkan.newsapplicationwithweka.data.web.RestApi;
import com.agah.furkan.newsapplicationwithweka.util.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksFragmentVM extends AndroidViewModel {
    private int categoryCount, tempCounter = 0;
    private MutableLiveData<ModelForBooksRequest> booksList;
    private Application application;
    private List<ModelForBooksRequest.Item> itemList;

    public BooksFragmentVM(@NonNull Application application) {
        super(application);
        this.application = application;
        booksList = new MutableLiveData<>();
        itemList = new ArrayList<>();
        getCategories();
    }

    public MutableLiveData<ModelForBooksRequest> getBooksList() {
        return booksList;
    }

    private void getBooks(IBooksRetrofit iBooksRetrofit, HashMap<String, String> params) {
        iBooksRetrofit.getBooks(params).enqueue(new Callback<ModelForBooksRequest>() {
            @Override
            public void onResponse(Call<ModelForBooksRequest> call, Response<ModelForBooksRequest> response) {
                tempCounter++;
                if (response.body() != null) {
                    addItemsToList(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelForBooksRequest> call, Throwable t) {
                tempCounter++;
            }
        });
    }

    private void addItemsToList(ModelForBooksRequest modelForBooksRequest) {
        for (int x = 0; x < modelForBooksRequest.getItems().size(); x++) {
            boolean isContains = false;
            for (int y = 0; y < itemList.size(); y++) {
                if (itemList.get(y).getVolumeInfo().getTitle().equals(modelForBooksRequest.getItems().get(x).getVolumeInfo().getTitle())) {
                    isContains = true;
                    break;
                }
            }
            if (!isContains) {
                itemList.add(modelForBooksRequest.getItems().get(x));
            }
        }
        if (tempCounter == categoryCount) {
            ModelForBooksRequest modelForBooksRequest1 = new ModelForBooksRequest();
            modelForBooksRequest1.setItems(itemList);
            booksList.setValue(modelForBooksRequest1);
        }
    }

    private void getCategories() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + UserManager.getUserInfo(application).getUId());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoryCount = (int) dataSnapshot.getChildrenCount();
                for (int x = 0; x < dataSnapshot.getChildrenCount(); x++) {
                    HashMap<String, String> retrofitParams = new HashMap<>();
                    retrofitParams.put("q", Objects.requireNonNull(dataSnapshot.child(String.valueOf(x)).getValue()).toString());
                    retrofitParams.put("langRestrict", "en");
                    retrofitParams.put("maxResults", "40");
                    retrofitParams.put("orderBy", "newest");
                    getBooks(RestApi.getBooksClient().create(IBooksRetrofit.class), retrofitParams);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

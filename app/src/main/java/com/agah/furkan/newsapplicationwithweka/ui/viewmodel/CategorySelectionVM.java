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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.agah.furkan.newsapplicationwithweka.ui.Interfaces.ICategorySelection;
import com.agah.furkan.newsapplicationwithweka.util.UserManager;

public class CategorySelectionVM extends AndroidViewModel implements ICategorySelection {
    private MutableLiveData<List<String>> categoryList;
    private List<String> categorySelectedList;

    public CategorySelectionVM(@NonNull Application application) {
        super(application);
        categoryList = new MutableLiveData<>();
        categorySelectedList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + UserManager.getUserInfo(application).getUId());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    categoryList.setValue(new ArrayList<>());
                } else {
                    for (int x = 0; x < dataSnapshot.getChildrenCount(); x++) {
                        addCategory(Objects.requireNonNull(dataSnapshot.child(String.valueOf(x)).getValue()).toString());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public LiveData<List<String>> getList() {
        return categoryList;
    }

    @Override
    public void addCategory(String newCategory) {
        if (!categorySelectedList.contains(newCategory)) {
            categorySelectedList.add(newCategory);
            categoryList.setValue(categorySelectedList);
        }
    }

    @Override
    public void removeCategory(String removeCategory) {
        categorySelectedList.remove(removeCategory);
        categoryList.setValue(categorySelectedList);
    }
}

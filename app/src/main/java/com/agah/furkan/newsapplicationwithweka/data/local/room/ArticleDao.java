package com.agah.furkan.newsapplicationwithweka.data.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomArticleModel roomArticleModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RoomArticleModel> roomArticleModel);

    @Query("DELETE FROM ArticlesTable")
    void deleteAll();

    @Query("SELECT * FROM ArticlesTable")
    LiveData<List<RoomArticleModel>> getAllNews();


}

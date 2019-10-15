package com.agah.furkan.newsapplicationwithweka.data.local.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {RoomArticleModel.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    private static NewsDatabase INSTANCE;
    private static Callback sRoomDatabaseCallback =
            new Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                }
            };

    static NewsDatabase getDatabase(Context context) {
        synchronized (NewsDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        NewsDatabase.class,
                        "newsDatabase")
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }
        return INSTANCE;
    }

    public abstract ArticleDao articleDao();
}


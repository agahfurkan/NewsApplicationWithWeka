package com.agah.furkan.newsapplicationwithweka.data.local.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NewsRepository {
    private ArticleDao mArticleDao;
    private LiveData<List<RoomArticleModel>> mAllNews;

    public NewsRepository(Context application) {
        NewsDatabase db = NewsDatabase.getDatabase(application);
        mArticleDao = db.articleDao();
        mAllNews = mArticleDao.getAllNews();
    }

    public LiveData<List<RoomArticleModel>> getAllNews() {
        return mAllNews;
    }

    public void deleteAll() {
        new deleteAllData(mArticleDao).execute();
    }

    public void insert(RoomArticleModel roomArticleModel) {
        new insertAsyncTask(mArticleDao).execute(roomArticleModel);
    }

    public void insertAll(List<RoomArticleModel> roomArticleModelList) {
        new insertAllAsyncTask(mArticleDao).execute(roomArticleModelList);
    }

    private static class deleteAllData extends AsyncTask<Void, Void, Void> {

        private ArticleDao mAsyncTaskDao;

        deleteAllData(ArticleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<RoomArticleModel, Void, Void> {

        private ArticleDao mAsyncTaskDao;

        insertAsyncTask(ArticleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RoomArticleModel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertAllAsyncTask extends AsyncTask<List<RoomArticleModel>, Void, Void> {

        private ArticleDao mAsyncTaskDao;

        insertAllAsyncTask(ArticleDao dao) {
            mAsyncTaskDao = dao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(final List<RoomArticleModel>... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }
}


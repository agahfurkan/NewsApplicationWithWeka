package com.agah.furkan.newsapplicationwithweka;

import android.app.Application;
import android.os.Environment;

import com.google.firebase.FirebaseApp;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.agah.furkan.newsapplicationwithweka.logging.DebugTree;
import com.agah.furkan.newsapplicationwithweka.logging.ReleaseTree;
import com.agah.furkan.newsapplicationwithweka.util.UserManager;
import timber.log.Timber;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.SerializationHelper;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class MainApplication extends Application {
    StringToWordVector filter = null;
    NaiveBayes naiveBayes = null;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        FirebaseApp.initializeApp(this);
        if (!BuildConfig.DEBUG) {
            Timber.plant(new ReleaseTree());
        } else {
            Timber.plant(new DebugTree());
        }
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        if (UserManager.getUserInfo(this).getUsername() != null && !UserManager.getUserInfo(this).getUsername().equals("empty")) {
            loadModel();
        }
    }

    public void loadModel() {
        if (filter == null & naiveBayes == null) {
            new BuildClassificationModel().execute();
        }
    }

    public StringToWordVector getFilter() {
        return filter;
    }

    public NaiveBayes getModel() {
        return naiveBayes;
    }

    class BuildClassificationModel extends android.os.AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                File filterFile = new File(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/filter");
                File bayesFile = new File(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/nb.model");
                if (filterFile.exists() && !filterFile.isDirectory()) {
                    Timber.tag("timber").d("Reading classification filter in MainApplication from file");
                    filter = (StringToWordVector) SerializationHelper.read(new FileInputStream(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/filter"));

                } else {
                    Timber.tag("timber").d("Reading classification filter in MainApplication from assets");
                    filter = (StringToWordVector) SerializationHelper.read(getAssets().open("filter"));

                }
                Timber.tag("timber").d("Reading classification filter in MainApplication successfull");
                if (bayesFile.exists() && !bayesFile.isDirectory()) {
                    Timber.tag("timber").d("Reading classification model in MainApplication from file");
                    naiveBayes = (NaiveBayes) SerializationHelper.read(new FileInputStream(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/nb.model"));
                } else {
                    Timber.tag("timber").d("Reading classification model in MainApplication from assets");
                    naiveBayes = (NaiveBayes) SerializationHelper.read(getAssets().open("nb.model"));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Timber.tag("timber").d("Error while reading classification model and filter in MainApplication");
            } catch (Exception e) {
                e.printStackTrace();
                Timber.tag("timber").d("Error while reading classification model and filter in MainApplication");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Timber.tag("timber").d("Reading model and filter successfull in MainApplication");
        }
    }
}

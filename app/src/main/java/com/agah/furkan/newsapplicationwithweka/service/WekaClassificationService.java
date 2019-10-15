package com.agah.furkan.newsapplicationwithweka.service;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import java.io.File;

import com.agah.furkan.newsapplicationwithweka.util.WekaOperationsUtil;
import timber.log.Timber;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class WekaClassificationService extends JobIntentService {
    static final int JOB_ID = 1000; //Unique job ID.

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, WekaClassificationService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        try {
            ConverterUtils.DataSource source = null;
            Instances dataset = null;
            Timber.tag("timber").d("Updating classification model and filter in WekaClassificationService");
            File mainDataset = new File(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/mainNews.arff");
            if (mainDataset.exists() && !mainDataset.isDirectory()) {
                //write new datas on dataset
                source = new ConverterUtils.DataSource(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/mainNews.arff");
                dataset = source.getDataSet();
            } else {
                source = new ConverterUtils.DataSource(getAssets().open("ClassWithTitles.arff"));//create dataset on device for further usage
                dataset = source.getDataSet();
                ArffSaver saver = new ArffSaver();
                saver.setInstances(dataset);
                saver.setFile(mainDataset);
                saver.writeBatch();
            }
            dataset.setClassIndex(0);
            StringToWordVector filter= WekaOperationsUtil.createFilter(dataset);
            //dataset = Filter.useFilter(dataset, filter);

            //NaiveBayes nb = new NaiveBayes();
            //nb.buildClassifier(dataset);
            //SerializationHelper.write(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/nb.model", nb); //created new model with new dataset
            SerializationHelper.write(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/filter", filter);
            Timber.tag("timber").d("Updating classification model and filter in WekaClassificationService Successfull !");
            MediaScannerConnection.scanFile(WekaClassificationService.this, new String[]{Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/nb.model", Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/filter", Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/mainNews.arff"}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            Timber.tag("timber").d("Error in WekaClassificationService while updating model and filter");
        }
    }

    @Override
    public boolean onStopCurrentWork() {
        Timber.tag("timber").d("Updating classification model and filter in WekaClassificationService onStopCurrentWork !");
        return super.onStopCurrentWork();
    }

    @Override
    public void onDestroy() {
        Timber.tag("timber").d("Updating classification model and filter in WekaClassificationService onDestroy !");
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.tag("timber").d("Updating classification model and filter in WekaClassificationService onCreate !");
    }
}

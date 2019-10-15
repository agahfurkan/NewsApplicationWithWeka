package com.agah.furkan.newsapplicationwithweka.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.data.local.room.NewsRepository;
import com.agah.furkan.newsapplicationwithweka.data.local.room.RoomArticleModel;
import com.agah.furkan.newsapplicationwithweka.data.web.INewsRetrofit;
import com.agah.furkan.newsapplicationwithweka.data.web.ModelForNewsRequest;
import com.agah.furkan.newsapplicationwithweka.data.web.RestApi;
import com.agah.furkan.newsapplicationwithweka.ui.activity.MainActivity;
import com.agah.furkan.newsapplicationwithweka.util.StringUtil;
import com.agah.furkan.newsapplicationwithweka.util.WekaOperationsUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import weka.classifiers.bayes.NaiveBayes;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class WekaClusteringService extends JobIntentService {
    static final int JOB_ID = 1001; //Unique job ID.
    static final String[] categoryArray = {"business", "entertainment", "general", "health", "science", "sports", "technology"};//getting news from all main categories
    private static boolean isDone;
    private StringToWordVector filterForClassification;
    private NaiveBayes nb;
    private int counter;
    private List<ModelForNewsRequest.Article> newsList;
    private Instances tempMainCluster, tempSeenNews;
    private NewsRepository newsRepository;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, WekaClusteringService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        isDone = false;
        counter = 0;
        newsList = new ArrayList<>();
        loadModelAndFilter();
        newsRepository = new NewsRepository(this);
        for (String s : categoryArray) {
            getAllTopNews(RestApi.getNewsClient().create(INewsRetrofit.class), s);
        }
    }

    @Override
    public boolean onStopCurrentWork() {
        Timber.tag("timber").d("Clustering model and filter in WekaClusteringService onStopCurrentWork !");
        return isDone;
        // return super.onStopCurrentWork();
    }

    @Override
    public void onDestroy() {
        Timber.tag("timber").d("Clustering model and filter in WekaClusteringService onDestroy !");
        super.onDestroy();
    }

    private void loadModelAndFilter() {
        try {
            File filterFile = new File(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/filter");
            File bayesFile = new File(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/nb.model");
            if (filterFile.exists() && !filterFile.isDirectory()) {
                Timber.tag("timber").d("Reading classification filter in WekaClusteringService from file");
                filterForClassification = (StringToWordVector) SerializationHelper.read(
                        new FileInputStream(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/filter"));

            } else {
                Timber.tag("timber").d("Reading classification filter in WekaClusteringService from assets");
                filterForClassification = (StringToWordVector) SerializationHelper.read(getAssets().open("filter"));

            }
            Timber.tag("timber").d("Reading classification filter in WekaClusteringService successfull");
            if (bayesFile.exists() && !bayesFile.isDirectory()) {
                Timber.tag("timber").d("Reading classification model in WekaClusteringService from file");
                nb = (NaiveBayes) SerializationHelper.read(new FileInputStream
                        (Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/nb.model"));
            } else {
                Timber.tag("timber").d("Reading classification model in WekaClusteringService from assets");
                nb = (NaiveBayes) SerializationHelper.read(getAssets().open("nb.model"));
            }
            Timber.tag("timber").d("Reading classification model and filter in WekaClusteringService SUCCESSFULL !");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Timber.tag("timber").d("Error while reading classification model and filter in WekaClusteringService");
        } catch (Exception e) {
            e.printStackTrace();
            Timber.tag("timber").d("Error while reading classification model and filter in WekaClusteringService");
        }
    }

    public void fireNotification(Context context, List<Integer> suggestedNews) throws Exception {
        Timber.tag("timber").d("Notification will be fired.");
        newsRepository.deleteAll();
        List<String> categoryList = new ArrayList<>();
        tempSeenNews.setClassIndex(0);
        tempMainCluster.setClassIndex(0);
        tempSeenNews = Filter.useFilter(tempSeenNews, filterForClassification);
        tempMainCluster = Filter.useFilter(tempMainCluster, filterForClassification);
        Timber.tag("timber").d("tempSeenNews will be Classified total size: %d", tempSeenNews.numInstances());
        for (int x = 0; x < tempSeenNews.numInstances(); x++) {
            try {
                double label = nb.classifyInstance(tempSeenNews.instance(x));
                tempSeenNews.instance(x).setClassValue(label);
                if (!categoryList.contains(tempSeenNews.instance(x).stringValue(0))) {
                    categoryList.add(tempSeenNews.instance(x).stringValue(0));
                }
                Timber.tag("timber").d("tempSeenNews: " + x + " classified Successfully.");
            } catch (Exception e) {
                Timber.tag("timber").d("tempSeenNews classification errror");
            }
        }
        Timber.tag("timber").d("tempSeenNews classification SUCCESSFULL !");
        List<Integer> recommendIndexList = new ArrayList<>();
        int addedNewsCounter = 0;
        for (int x = 0; x < suggestedNews.size(); x++) {
            int location = suggestedNews.get(x);
            double label = nb.classifyInstance(tempMainCluster.instance(location));
            tempMainCluster.instance(location).setClassValue(label);
            String category = tempMainCluster.instance(location).stringValue(0);
            if (categoryList.contains(category)) {
                recommendIndexList.add(location);
                RoomArticleModel roomArticleModel = new RoomArticleModel();
                roomArticleModel.setAuthor(newsList.get(location).getAuthor());
                roomArticleModel.setContent(newsList.get(location).getContent());
                roomArticleModel.setDescription(newsList.get(location).getDescription());
                roomArticleModel.setPublishedAt(newsList.get(location).getPublishedAt());
                roomArticleModel.setSource(newsList.get(location).getSource().getName());
                roomArticleModel.setTitle(newsList.get(location).getTitle());
                roomArticleModel.setUrl(newsList.get(location).getUrl());
                roomArticleModel.setUrlToImage(newsList.get(location).getUrlToImage());
                newsRepository.insert(roomArticleModel);
                addedNewsCounter++;
            }
        }
        Timber.tag("timber").d("%d News added to Room Db.", addedNewsCounter);

        final NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        final Context cc = context;
        Random r = new Random();
        final int finalRecommendeditemIndex = recommendIndexList.get(r.nextInt(recommendIndexList.size()));
        Glide.with(this)
                .asBitmap()
                .load(newsList.get(finalRecommendeditemIndex).getUrlToImage())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(cc, "default")
                                .setSmallIcon(R.drawable.launcher_icon) // notification icon
                                .setLargeIcon(resource)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(newsList.get(finalRecommendeditemIndex).getContent()))
                                .setContentTitle(newsList.get(finalRecommendeditemIndex).getTitle()) // title for notification
                                .setContentText(newsList.get(finalRecommendeditemIndex).getContent())
                                .setAutoCancel(true); // clear notification after click
                        Intent intent2 = new Intent(cc, MainActivity.class);
                        PendingIntent pi = PendingIntent.getActivity(cc, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                        mBuilder.setContentIntent(pi);
                        mNotificationManager.notify(0, mBuilder.build());
                        Timber.tag("timber").d("Notification Fired !");
                        isDone = true;
                        File newsFile = new File(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/newsForCluster.arff");
                        newsFile.delete();
                    }
                });


    }

    private void wekaCluster() {
        if (counter == categoryArray.length) {
            try {
                Timber.tag("timber").d("Clustering started");
                ConverterUtils.DataSource newsSource = new ConverterUtils.DataSource(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/newsForCluster.arff");
                ConverterUtils.DataSource newsToCluster = new ConverterUtils.DataSource(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/seenNews.arff");
                Instances seenNews = newsToCluster.getDataSet();
                Instances mainCluster = newsSource.getDataSet();
                tempMainCluster = newsSource.getDataSet();
                tempSeenNews = newsToCluster.getDataSet();
                StringToWordVector filter = WekaOperationsUtil.createFilter(mainCluster);
                mainCluster = Filter.useFilter(mainCluster, filter);
                seenNews = Filter.useFilter(seenNews, filter);
                Timber.tag("timber").d("Cluster filter created");
                SimpleKMeans clusterers = new SimpleKMeans();
                clusterers.setOptions(weka.core.Utils.splitOptions("-init 1 -max-candidates 100 -periodic-pruning 10000 -min-density 2.0 -t1 -1.25 -t2 -1.0 -N 7 -A \"weka.core.ManhattanDistance -D -R first-last\" -I 500 -O -fast -num-slots 1 -S 10"));
                clusterers.buildClusterer(mainCluster);
                Timber.tag("timber").d("%d", clusterers.getNumClusters());
                Timber.tag("timber").d("Cluster[0] Size: %d", clusterers.getClusterSizes()[0]);
                Timber.tag("timber").d("Cluster[1] Size: %d", clusterers.getClusterSizes()[1]);
                Timber.tag("timber").d("Cluster[2] Size: %d", clusterers.getClusterSizes()[2]);
                Timber.tag("timber").d("Cluster[3] Size: %d", clusterers.getClusterSizes()[3]);
                Timber.tag("timber").d("Cluster[4] Size: %d", clusterers.getClusterSizes()[4]);
                Timber.tag("timber").d("Cluster[5] Size: %d", clusterers.getClusterSizes()[5]);
                Timber.tag("timber").d("Cluster[6] Size: %d", clusterers.getClusterSizes()[6]);
                Timber.tag("timber").d("Cluster build done successfully !");
                List<Integer> tempSuggestions = new ArrayList<>();
                for (int x = 0; x < seenNews.numInstances(); x++) {
                    int clusterNum = clusterers.clusterInstance(seenNews.instance(x));
                    for (int ab = 0; ab < clusterers.getAssignments().length; ab++) {
                        if (clusterers.getAssignments()[ab] == clusterNum) {
                            if (!tempSuggestions.contains(ab)) {
                                tempSuggestions.add(ab);
                            }
                        }
                    }
                }
                Timber.tag("timber").d("All news count to be classified: %d", tempSuggestions.size());
                fireNotification(this, tempSuggestions);
            } catch (Exception e) {
                Timber.tag("timber").d("Error while building cluster");
            }
        }
    }

    private void getAllTopNews(INewsRetrofit INewsRetrofit, String category) {
        INewsRetrofit.getAllTopNews(category).enqueue(new Callback<ModelForNewsRequest>() {
            @Override
            public void onResponse(Call<ModelForNewsRequest> call, Response<ModelForNewsRequest> response) {
                counter++;
                if (response.body() != null) {
                    File newsFile = new File(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/newsForCluster.arff");
                    try {
                        boolean fileExists = newsFile.exists();
                        FileOutputStream fileOutputStream = new FileOutputStream(newsFile, true);
                        if (!fileExists) {
                            fileOutputStream.write(("@relation newsForCluster\n" +
                                    "\n" +
                                    "@attribute class_ss {CRIME,ENTERTAINMENT,WORLDNEWS,IMPACT,POLITICS,WEIRDNEWS,BLACKVOICES,WOMEN,COMEDY,QUEERVOICES,SPORTS,BUSINESS,TRAVEL,MEDIA,TECH,RELIGION,SCIENCE,LATINOVOICES,EDUCATION,COLLEGE,PARENTS,ARTS&CULTURE,STYLE,GREEN,TASTE,HEALTHYLIVING,GOODNEWS,ARTS,WELLNESS,PARENTING,HOME&LIVING,STYLE&BEAUTY,DIVORCE,WEDDINGS,FOOD&DRINK,MONEY,ENVIRONMENT,CULTURE&ARTS}\n" +
                                    "@attribute content string\n" +
                                    "\n" +
                                    "@data" +
                                    "\n").getBytes());
                        }
                        BufferedReader br = new BufferedReader(new FileReader(newsFile));
                        String st;
                        for (int x = 0; x < response.body().getArticles().size(); x++) {
                            boolean containsNews = false;
                            if (response.body().getArticles().get(x).getContent() != null) {
                                String contentToWrite = StringUtil.formatStringToWeka(response.body().getArticles().get(x).getContent());
                                while ((st = br.readLine()) != null) {
                                    if (st.contains(contentToWrite)) {
                                        containsNews = true;
                                        break;
                                    }
                                }
                                if (!containsNews) {
                                    newsList.add(response.body().getArticles().get(x));
                                    fileOutputStream.write(("?,'" + contentToWrite + "'" + "\n").getBytes());
                                }
                            }
                        }
                        fileOutputStream.close();
                        wekaCluster();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelForNewsRequest> call, Throwable t) {
                counter++;
                Timber.tag("timber").d("Error sending request in WekaClusteringService");
            }
        });
    }
}

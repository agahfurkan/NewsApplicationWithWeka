package com.agah.furkan.newsapplicationwithweka.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import com.agah.furkan.newsapplicationwithweka.MainApplication;
import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.data.web.ModelForNewsRequest;
import com.agah.furkan.newsapplicationwithweka.util.FragmentHelperUtil;
import com.agah.furkan.newsapplicationwithweka.util.MediaScannerUtil;
import com.agah.furkan.newsapplicationwithweka.util.StringUtil;
import com.agah.furkan.newsapplicationwithweka.util.UserManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;

public class NewsDetailFragment extends BaseFragment {
    @BindView(R.id.news_detail_title)
    TextView newsDetailTitle;
    @BindView(R.id.news_detail_content)
    TextView newsDetailContent;
    @BindView(R.id.news_detail_publishDate)
    TextView newsPublishDate;
    @BindView(R.id.news_detail_source)
    TextView newsSource;
    @BindView(R.id.news_detail_category)
    TextView newsCategory;
    @BindView(R.id.news_detail_image)
    ImageView newsDetailImg;
    private File myFile;
    private Uri img;
    private String title, content, publishDate, source, description, sourceUrl;
    private Unbinder unbinder;

    public static NewsDetailFragment newInstance(ModelForNewsRequest.Article newsModel) {
        Bundle args = new Bundle();
        args.putString("title", newsModel.getTitle());
        args.putString("content", newsModel.getContent());
        args.putString("img", newsModel.getUrlToImage());
        args.putString("publishDate", newsModel.getPublishedAt());
        args.putString("description", newsModel.getDescription());
        args.putString("source", newsModel.getSource().getName());
        args.putString("sourceUrl", newsModel.getUrl());
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
        newsDetailFragment.setArguments(args);
        return newsDetailFragment;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
            content = getArguments().getString("content");
            publishDate = getArguments().getString("publishDate");
            source = getArguments().getString("source");
            description = getArguments().getString("description");
            sourceUrl = getArguments().getString("sourceUrl");
            if (getArguments().getString("img") != null) {
                img = Uri.parse(getArguments().getString("img"));
            } else {
                img = null;
            }
        }
    }

    @OnClick(R.id.news_detail_source)
    void onClick(View v) {
        FragmentHelperUtil.loadWithAnimAddBackStack(getActivity(), WebviewFragment.newInstance(sourceUrl));
    }

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageView = inflater.inflate(R.layout.news_detail_fragment, container, false);
        unbinder = ButterKnife.bind(this, pageView);
        getToolbar().hide();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.no_image_icon);
        requestOptions.centerInside();
        Glide.with(pageView)
                .applyDefaultRequestOptions(requestOptions)
                .load(img)
                .into(newsDetailImg);
        newsDetailContent.setText(content);
        newsDetailTitle.setText(title);
        newsSource.setPaintFlags(newsSource.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        newsSource.setText(source);
        newsPublishDate.setText(publishDate.substring(0, 10));
        if (((MainApplication) Objects.requireNonNull(getActivity()).getApplication()).getFilter() != null) {
            try {
                if (content != null) {
                    writeData(content);
                } else if (description != null) {
                    writeData(description);
                } else if (title != null) {
                    writeData(title);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Timber.tag("timber").d("temp txt error in newsDetail");
            }
        } else {
            newsCategory.setText(getActivity().getString(R.string.model_not_available));
        }
        if (!UserManager.getEmail(getActivity()).equals("empty")) {
            if (content != null) {
                writeSeenNews(content);
            } else if (description != null) {
                writeSeenNews(description);
            } else if (title != null) {
                writeSeenNews(title);
            }
        }

        return pageView;
    }

    public ActionBar getToolbar() {
        return ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void wekaTest(Instances testData) {
        try {
            testData.setClassIndex(0);
            testData = Filter.useFilter(testData, ((MainApplication) Objects.requireNonNull(getActivity()).getApplication()).getFilter());
            NaiveBayes nb = ((MainApplication) getActivity().getApplication()).getModel();
            double label = nb.classifyInstance(testData.instance(0));
            testData.instance(0).setClassValue(label);
            newsCategory.setText(testData.instance(0).stringValue(0));
            Timber.tag("timber").d(testData.instance(0).stringValue(0));
        } catch (Exception e) {
            Timber.tag("timber").d("Error while classifying text");
        }
        myFile.delete();

    }

    private void writeData(String data) throws IOException {
        myFile = new File(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/temp.arff");
        FileOutputStream fileOutputStream = new FileOutputStream(myFile, true);
        data = StringUtil.formatStringToWeka(data);
        fileOutputStream.write(("@relation news_test\n" +
                "\n" +
                "@attribute class_ss {CRIME,ENTERTAINMENT,WORLDNEWS,IMPACT,POLITICS,WEIRDNEWS,BLACKVOICES,WOMEN,COMEDY,QUEERVOICES,SPORTS,BUSINESS,TRAVEL,MEDIA,TECH,RELIGION,SCIENCE,LATINOVOICES,EDUCATION,COLLEGE,PARENTS,ARTS&CULTURE,STYLE,GREEN,TASTE,HEALTHYLIVING,GOODNEWS,ARTS,WELLNESS,PARENTING,HOME&LIVING,STYLE&BEAUTY,DIVORCE,WEDDINGS,FOOD&DRINK,MONEY,ENVIRONMENT,CULTURE&ARTS}\n" +
                "@attribute content string\n" +
                "\n" +
                "@data" +
                "\n").getBytes());
        fileOutputStream.write(("?,'" + data + "'").getBytes());
        fileOutputStream.close();
        try {
            FileInputStream tempDataArff = new FileInputStream(myFile.getPath());
            ConverterUtils.DataSource test = new ConverterUtils.DataSource(tempDataArff);
            Instances temp = test.getDataSet();
            Timber.tag("timber").d("arff file creation successfull.");
            wekaTest(temp);
        } catch (Exception e) {
            e.printStackTrace();
            Timber.tag("timber").d("Instance from txt error in newsDetail");
        }
        MediaScannerUtil.scan(myFile, Objects.requireNonNull(getActivity()));

    }

    private void writeSeenNews(String textToClassify) {
        File newsFile = new File(Environment.getExternalStoragePublicDirectory("NewsApplicationWithWeka").getAbsolutePath() + "/seenNews.arff");
        try {
            boolean fileExists = newsFile.exists();
            FileOutputStream fileOutputStream = new FileOutputStream(newsFile, true);
            if (!fileExists) {
                fileOutputStream.write(("@relation seenNews\n" +
                        "\n" +
                        "@attribute class_ss {CRIME,ENTERTAINMENT,WORLDNEWS,IMPACT,POLITICS,WEIRDNEWS,BLACKVOICES,WOMEN,COMEDY,QUEERVOICES,SPORTS,BUSINESS,TRAVEL,MEDIA,TECH,RELIGION,SCIENCE,LATINOVOICES,EDUCATION,COLLEGE,PARENTS,ARTS&CULTURE,STYLE,GREEN,TASTE,HEALTHYLIVING,GOODNEWS,ARTS,WELLNESS,PARENTING,HOME&LIVING,STYLE&BEAUTY,DIVORCE,WEDDINGS,FOOD&DRINK,MONEY,ENVIRONMENT,CULTURE&ARTS}\n" +
                        "@attribute content string\n" +
                        "\n" +
                        "@data" +
                        "\n").getBytes());
            }
            BufferedReader br = new BufferedReader(new FileReader(newsFile));
            String st;
            boolean containsNews = false;
            while ((st = br.readLine()) != null) {
                if (st.contains(StringUtil.formatStringToWeka(textToClassify))) {
                    containsNews = true;
                    break;
                }
            }
            if (!containsNews) {
                fileOutputStream.write(("?,'" + StringUtil.formatStringToWeka(textToClassify) + "'" + "\n").getBytes());
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

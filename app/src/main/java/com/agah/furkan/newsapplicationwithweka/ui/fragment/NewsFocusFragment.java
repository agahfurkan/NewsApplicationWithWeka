package com.agah.furkan.newsapplicationwithweka.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Objects;

import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.data.web.ModelForNewsRequest;
import com.agah.furkan.newsapplicationwithweka.util.ViewUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewsFocusFragment extends DialogFragment {
    @BindView(R.id.focus_news_detail_content)
    TextView contentText;
    @BindView(R.id.focus_news_detail_image)
    ImageView previewImage;
    @BindView(R.id.focus_news_detail_publishDate)
    TextView publishDateText;
    @BindView(R.id.focus_news_detail_source)
    TextView sourceText;
    private Unbinder unbinder;
    private String publishDate, source, content;
    private Uri img;

    public static NewsFocusFragment newInstance(ModelForNewsRequest.Article newsModel) {
        Bundle args = new Bundle();
        args.putString("content", newsModel.getContent());
        args.putString("img", newsModel.getUrlToImage());
        args.putString("publishDate", newsModel.getPublishedAt());
        args.putString("source", newsModel.getSource().getName());
        NewsFocusFragment fragment = new NewsFocusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageView = inflater.inflate(R.layout.news_focus_dialog, container, false);
        unbinder = ButterKnife.bind(this, pageView);
        contentText.setText(content);
        sourceText.setText(source);
        publishDateText.setText(publishDate.substring(0, 10));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.no_image_icon);
        requestOptions.centerInside();
        Glide.with(pageView)
                .applyDefaultRequestOptions(requestOptions)
                .load(img)
                .into(previewImage);
        return pageView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            content = getArguments().getString("content");
            publishDate = getArguments().getString("publishDate");
            source = getArguments().getString("source");
            if (getArguments().getString("img") != null) {
                img = Uri.parse(getArguments().getString("img"));
            } else {
                img = null;
            }

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = (int) (ViewUtil.getScreenWidth(Objects.requireNonNull(getActivity())));
            int height = (int) (ViewUtil.getScreenHeight(getActivity()) * 0.9);
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}

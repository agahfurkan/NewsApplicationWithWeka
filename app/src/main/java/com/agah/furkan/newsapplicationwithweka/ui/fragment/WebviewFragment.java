package com.agah.furkan.newsapplicationwithweka.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.agah.furkan.newsapplicationwithweka.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WebviewFragment extends Fragment {
    @BindView(R.id.webview)
    WebView webView;
    private String sourceUrl;

    public static WebviewFragment newInstance(String sourceUrl) {
        Bundle args = new Bundle();
        args.putString("sourceUrl", sourceUrl);
        WebviewFragment webviewFragment = new WebviewFragment();
        webviewFragment.setArguments(args);
        return webviewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sourceUrl = getArguments().getString("sourceUrl");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageView = inflater.inflate(R.layout.webview_layout, container, false);
        ButterKnife.bind(this, pageView);
        webView.loadUrl(sourceUrl);
        return pageView;
    }
}

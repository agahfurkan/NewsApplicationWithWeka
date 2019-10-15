package com.agah.furkan.newsapplicationwithweka.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

import java.util.Objects;

import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.ui.viewmodel.SplashFragmentVM;
import com.agah.furkan.newsapplicationwithweka.util.FragmentHelperUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashFragment extends Fragment {
    @BindView(R.id.splash_logo)
    ImageView splashLogo;

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageView = inflater.inflate(R.layout.splash_fragment, container, false);
        ButterKnife.bind(this, pageView);
        getToolbar().hide();
        loadLogo();
        SplashFragmentVM splashFragmentVM = ViewModelProviders.of(this).get(SplashFragmentVM.class);
        splashFragmentVM.getLiveData().observe(this, s -> FragmentHelperUtil.loadWithAnim(getActivity(), LoginFragment.newInstance()));
        return pageView;
    }

    private void loadLogo() {
        Glide.with(this)
                .load(R.drawable.splash_logo)
                .into(splashLogo);
    }

    private ActionBar getToolbar() {
        return ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();
    }
}
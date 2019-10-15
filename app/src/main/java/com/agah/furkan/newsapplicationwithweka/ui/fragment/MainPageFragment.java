package com.agah.furkan.newsapplicationwithweka.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.ui.adapter.ViewPagerAdapter;
import com.agah.furkan.newsapplicationwithweka.util.DialogFactory;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MainPageFragment extends BaseFragment {
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private Unbinder unbinder;

    public static MainPageFragment newInstance() {
        return new MainPageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageView = inflater.inflate(R.layout.main_page_fragment, container, false);
        unbinder = ButterKnife.bind(this, pageView);
        ViewPagerAdapter viewPagerAdapter;
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(viewPagerAdapter = new ViewPagerAdapter(getContext(), getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        for (int a = 0; a < tabLayout.getTabCount(); a++) {
            TabLayout.Tab tab = tabLayout.getTabAt(a);
            if (tab != null) {
                tab.setCustomView(viewPagerAdapter.getTabView(a));
            }
        }
        return pageView;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}

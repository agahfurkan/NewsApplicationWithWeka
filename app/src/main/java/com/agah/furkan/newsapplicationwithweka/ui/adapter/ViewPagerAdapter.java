package com.agah.furkan.newsapplicationwithweka.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.ui.fragment.NewsFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private String[] title = {"For you", "General", "Science", "Technology", "Sports", "Health"};
    private Context context;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return NewsFragment.newInstance("For you");
        } else if (position == 1) {
            return NewsFragment.newInstance("general");
        } else if (position == 2) {
            return NewsFragment.newInstance("science");
        } else if (position == 3) {
            return NewsFragment.newInstance("technology");
        } else if (position == 4) {
            return NewsFragment.newInstance("sports");
        } else if (position == 5) {
            return NewsFragment.newInstance("health");
        }
        return null;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @SuppressLint("InflateParams")
    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_viewpager_tab, null);
        TextView t = view.findViewById(R.id.tab_textview);
        t.setText(title[position]);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}

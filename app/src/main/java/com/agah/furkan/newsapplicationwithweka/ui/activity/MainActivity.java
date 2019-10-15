package com.agah.furkan.newsapplicationwithweka.ui.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.data.local.sharedpref.SharedPrefs;
import com.agah.furkan.newsapplicationwithweka.ui.Interfaces.IDialogListener;
import com.agah.furkan.newsapplicationwithweka.ui.fragment.BooksFragment;
import com.agah.furkan.newsapplicationwithweka.ui.fragment.CategorySelectionFragment;
import com.agah.furkan.newsapplicationwithweka.ui.fragment.LoginFragment;
import com.agah.furkan.newsapplicationwithweka.ui.fragment.MainPageFragment;
import com.agah.furkan.newsapplicationwithweka.ui.fragment.NewsFragment;
import com.agah.furkan.newsapplicationwithweka.ui.fragment.RecommendedNewsFragment;
import com.agah.furkan.newsapplicationwithweka.ui.fragment.SettingsFragment;
import com.agah.furkan.newsapplicationwithweka.ui.fragment.SplashFragment;
import com.agah.furkan.newsapplicationwithweka.util.AlarmManagerHelper;
import com.agah.furkan.newsapplicationwithweka.util.DialogFactory;
import com.agah.furkan.newsapplicationwithweka.util.FragmentHelperUtil;
import com.agah.furkan.newsapplicationwithweka.util.UserManager;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IDialogListener {

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    private int showCallbackCounter = 0, dismissCallbackCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            if (UserManager.getEmail(this) != null) {
                if (UserManager.getEmail(this).equals("empty")) {
                    FragmentHelperUtil.loadWithAnim(this, NewsFragment.newInstance("General"));
                } else {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        FragmentHelperUtil.loadWithAnim(this, MainPageFragment.newInstance());
                    } else {
                        SharedPrefs sharedPrefs = new SharedPrefs(this);
                        sharedPrefs.clearPref();
                        AlarmManagerHelper.cancelModelAlarm(getApplicationContext());
                        AlarmManagerHelper.cancelNotificationAlarm(getApplicationContext());
                        FragmentHelperUtil.loadWithAnim(this, LoginFragment.newInstance());
                    }

                }
            } else {
                FragmentHelperUtil.loadWithoutAnim(this, SplashFragment.newInstance());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search_item).getActionView();
        EditText editText = searchView.findViewById(R.id.search_src_text);
        ImageView imageView = searchView.findViewById(R.id.search_close_btn);
        imageView.setImageResource(R.drawable.ic_close_white_24dp);
        editText.setTextColor(Color.WHITE);
        editText.setHintTextColor(Color.WHITE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (item.getItemId() == R.id.menu_logout_item) {
            SharedPrefs sharedPrefs = new SharedPrefs(this);
            sharedPrefs.clearPref();
            AlarmManagerHelper.cancelModelAlarm(getApplicationContext());
            AlarmManagerHelper.cancelNotificationAlarm(getApplicationContext());
            FragmentHelperUtil.loadWithAnim(this, LoginFragment.newInstance());
        } else if (item.getItemId() == R.id.menu_categorySelect_item) {
            FragmentHelperUtil.loadWithAnimAddBackStack(this, CategorySelectionFragment.newInstance());
        } else if (item.getItemId() == R.id.menu_recommended_news_item) {
            if (!(activeFragment instanceof RecommendedNewsFragment)) {
                FragmentHelperUtil.loadWithAnimAddBackStack(this, RecommendedNewsFragment.newInstance());
            }
        } else if (item.getItemId() == R.id.menu_settings_item) {
            if (!(activeFragment instanceof SettingsFragment)) {
                FragmentHelperUtil.loadWithAnimAddBackStack(this, SettingsFragment.newInstance());
            }
        } else if (item.getItemId() == R.id.menu_books_item) {
            if (!(activeFragment instanceof BooksFragment)) {
                FragmentHelperUtil.loadWithAnimAddBackStack(this, BooksFragment.newInstance());
            }
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("configurationChange", "configurationChange");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showDialog() {
        showCallbackCounter++;
        DialogFactory.showCustomFragmentDialog(this, "Please Wait", "Loading News...");
    }

    @Override
    public void dismissDialog() {
        dismissCallbackCounter++;
        if (dismissCallbackCounter == showCallbackCounter) {
            DialogFactory.dismissCustomFragmentDialog();
        }
    }
}


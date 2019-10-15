package com.agah.furkan.newsapplicationwithweka.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.ViewModelFactory;
import com.agah.furkan.newsapplicationwithweka.data.web.ModelForNewsRequest;
import com.agah.furkan.newsapplicationwithweka.ui.Interfaces.IDialogListener;
import com.agah.furkan.newsapplicationwithweka.ui.adapter.NewsListAdapter;
import com.agah.furkan.newsapplicationwithweka.ui.viewmodel.NewsFragmentVM;
import com.agah.furkan.newsapplicationwithweka.util.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewsFragment extends BaseFragment {
    @BindView(R.id.news_listview)
    RecyclerView newsListView;
    private Unbinder unbinder;
    private ModelForNewsRequest newsList;
    private NewsListAdapter newsListAdapter;
    private String category;
    private IDialogListener iDialogListener;

    public static NewsFragment newInstance(String category) {
        NewsFragment newsFragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        newsFragment.setArguments(args);
        return newsFragment;
    }

    @Override
    public void onDestroyView() {
        newsListView.setAdapter(null);
        unbinder.unbind();
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageView = inflater.inflate(R.layout.news_fragment, container, false);
        unbinder = ButterKnife.bind(this, pageView);
        setupRecyclerView(1);
        if (newsList != null) {
            newsListAdapter.setList(newsList);
        }
        return pageView;
    }

    private void setupRecyclerView(int columnNumber) {
        newsListAdapter = new NewsListAdapter((AppCompatActivity) getActivity());
        if (columnNumber > 1) {
            newsListView.setLayoutManager(new GridLayoutManager(getActivity(), columnNumber));
        } else {
            newsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        newsListView.setAdapter(newsListAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getToolbar().show();
        getToolbar().setTitle("Explore News");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iDialogListener = (IDialogListener) getActivity();
        if (getArguments() != null) {
            category = getArguments().getString("category");
        }
        ViewModelFactory viewModelFactory = new ViewModelFactory(Objects.requireNonNull(getActivity()).getApplication(), getArguments().getString("category"));
        NewsFragmentVM newsFragmentVM = ViewModelProviders.of(this, viewModelFactory).get(NewsFragmentVM.class);
        iDialogListener.showDialog();
        newsFragmentVM.getLiveData().observe(this, modelForNewsRequest -> {
            newsListAdapter.setList(modelForNewsRequest);
            newsList = modelForNewsRequest;
            iDialogListener.dismissDialog();
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (UserManager.getEmail(getActivity()) != null) {
            if (UserManager.getEmail(getActivity()).equals("empty")) {
                menu.findItem(R.id.menu_categorySelect_item).setEnabled(false);
                menu.findItem(R.id.menu_recommended_news_item).setEnabled(false);
                menu.findItem(R.id.menu_settings_item).setEnabled(false);
                menu.findItem(R.id.menu_books_item).setEnabled(false);
                menu.findItem(R.id.menu_books_item).getIcon().setAlpha(130);
            }
        }
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_item).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals("")) {
                    newsListAdapter.setList(newsList);
                } else {
                    List<ModelForNewsRequest.Article> tempList = new ArrayList<>();
                    ModelForNewsRequest tempModel = new ModelForNewsRequest();
                    tempModel.setTotalResults(newsList.getTotalResults());
                    tempModel.setStatus(newsList.getStatus());
                    tempModel.setArticles(newsList.getArticles());
                    for (int x = 0; x < newsList.getArticles().size(); x++) {
                        String title, description, content;
                        title = newsList.getArticles().get(x).getTitle();
                        description = newsList.getArticles().get(x).getDescription();
                        content = newsList.getArticles().get(x).getContent();
                        if (title != null) {
                            title = newsList.getArticles().get(x).getTitle().toLowerCase();
                        } else {
                            title = "";
                        }
                        if (description != null) {
                            description = newsList.getArticles().get(x).getDescription().toLowerCase();
                        } else {
                            description = "";
                        }
                        if (content != null) {
                            content = newsList.getArticles().get(x).getContent().toLowerCase();
                        } else {
                            content = "";
                        }
                        if (title.contains(s.toLowerCase()) || description.contains(s.toLowerCase()) || content.contains(s.toLowerCase())) {
                            tempList.add(newsList.getArticles().get(x));
                        }
                    }
                    tempModel.setArticles(tempList);
                    newsListAdapter.setList(tempModel);
                }
                return false;
            }
        });
    }
}

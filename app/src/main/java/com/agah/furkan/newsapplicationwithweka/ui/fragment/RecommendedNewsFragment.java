package com.agah.furkan.newsapplicationwithweka.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.data.web.ModelForNewsRequest;
import com.agah.furkan.newsapplicationwithweka.ui.adapter.NewsListAdapter;
import com.agah.furkan.newsapplicationwithweka.ui.viewmodel.RecommendedNewsVM;
import com.agah.furkan.newsapplicationwithweka.util.UserManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecommendedNewsFragment extends BaseFragment {
    @BindView(R.id.recommended_news)
    RecyclerView recommendedNews;
    @BindView(R.id.empty_data_image)
    ImageView emptyImage;
    @BindView(R.id.empty_data_text)
    TextView emptyText;
    private List<ModelForNewsRequest.Article> newsList;
    private NewsListAdapter newsListAdapter;
    private Unbinder unbinder;

    public static RecommendedNewsFragment newInstance() {
        return new RecommendedNewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageView = inflater.inflate(R.layout.recommended_news_fragment, container, false);
        unbinder = ButterKnife.bind(this, pageView);
        setupRecyclerView((AppCompatActivity) getActivity());
        if (newsList != null) {
            ModelForNewsRequest modelForNewsRequest = new ModelForNewsRequest();
            modelForNewsRequest.setArticles(newsList);
            modelForNewsRequest.setStatus("loaded");
            modelForNewsRequest.setTotalResults(newsList.size());
            newsListAdapter.setList(modelForNewsRequest);
            hideEmptyDataViews();
        }
        return pageView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getToolbar().show();
        getToolbar().setTitle("Recommended News");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecommendedNewsVM recommendedNewsVM = ViewModelProviders.of(this).get(RecommendedNewsVM.class);
        recommendedNewsVM.getLiveData().observe(this, roomArticleModels -> {
            if (roomArticleModels != null) {
                if (roomArticleModels.size() != 0) {
                    hideEmptyDataViews();
                    ModelForNewsRequest modelForNewsRequest = new ModelForNewsRequest();
                    newsList = new ArrayList<>();
                    for (int x = 0; x < roomArticleModels.size(); x++) {
                        ModelForNewsRequest.Article article = modelForNewsRequest.new Article();
                        ModelForNewsRequest.Source source = modelForNewsRequest.new Source();
                        source.setId(null);
                        source.setName(roomArticleModels.get(x).getSource());
                        article.setAuthor(roomArticleModels.get(x).getAuthor());
                        article.setContent(roomArticleModels.get(x).getContent());
                        article.setDescription(roomArticleModels.get(x).getDescription());
                        article.setPublishedAt(roomArticleModels.get(x).getPublishedAt());
                        article.setSource(source);
                        article.setTitle(roomArticleModels.get(x).getTitle());
                        article.setUrl(roomArticleModels.get(x).getUrl());
                        article.setUrlToImage(roomArticleModels.get(x).getUrlToImage());
                        newsList.add(article);
                    }
                    modelForNewsRequest.setArticles(newsList);
                    modelForNewsRequest.setStatus("loaded");
                    modelForNewsRequest.setTotalResults(newsList.size());
                    newsListAdapter.setList(modelForNewsRequest);
                }
            }
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
                    ModelForNewsRequest tempModel = new ModelForNewsRequest();
                    tempModel.setTotalResults(newsList.size());
                    tempModel.setStatus("loaded");
                    tempModel.setArticles(newsList);
                    newsListAdapter.setList(tempModel);
                } else {
                    List<ModelForNewsRequest.Article> tempList = new ArrayList<>();
                    ModelForNewsRequest tempModel = new ModelForNewsRequest();
                    tempModel.setTotalResults(newsList.size());
                    tempModel.setStatus("loaded");
                    for (int x = 0; x < newsList.size(); x++) {
                        String title, description, content;
                        title = newsList.get(x).getTitle();
                        description = newsList.get(x).getDescription();
                        content = newsList.get(x).getContent();
                        if (title != null) {
                            title = newsList.get(x).getTitle().toLowerCase();
                        } else {
                            title = "";
                        }
                        if (description != null) {
                            description = newsList.get(x).getDescription().toLowerCase();
                        } else {
                            description = "";
                        }
                        if (content != null) {
                            content = newsList.get(x).getContent().toLowerCase();
                        } else {
                            content = "";
                        }
                        if (title.contains(s.toLowerCase()) || description.contains(s.toLowerCase()) || content.contains(s.toLowerCase())) {
                            tempList.add(newsList.get(x));
                        }
                    }
                    tempModel.setArticles(tempList);
                    newsListAdapter.setList(tempModel);
                }
                return false;
            }
        });
    }

    private void hideEmptyDataViews() {
        emptyImage.setVisibility(View.GONE);
        emptyText.setVisibility(View.GONE);

    }

    private void setupRecyclerView(AppCompatActivity activity) {
        newsListAdapter = new NewsListAdapter(activity);
        int columnNumber = 1;
        if (columnNumber > 1) {
            recommendedNews.setLayoutManager(new GridLayoutManager(activity, columnNumber));
        } else {
            recommendedNews.setLayoutManager(new LinearLayoutManager(activity));
        }
        recommendedNews.setAdapter(newsListAdapter);
    }

    @Override
    public void onDestroyView() {
        recommendedNews.setAdapter(null);
        unbinder.unbind();
        super.onDestroyView();
    }
}

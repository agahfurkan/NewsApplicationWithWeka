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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.agah.furkan.newsapplicationwithweka.GlobalCons;
import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.data.web.ModelForBooksRequest;
import com.agah.furkan.newsapplicationwithweka.ui.adapter.BooksListAdapter;
import com.agah.furkan.newsapplicationwithweka.ui.viewmodel.BooksFragmentVM;
import com.agah.furkan.newsapplicationwithweka.util.DialogFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BooksFragment extends BaseFragment {
    @BindView(R.id.books_recyclerView)
    RecyclerView booksRecyclerView;
    private BooksListAdapter booksListAdapter;
    private ModelForBooksRequest booksList;
    private Unbinder unbinder;

    public static BooksFragment newInstance() {
        return new BooksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageView = inflater.inflate(R.layout.books_fragment, container, false);
        unbinder = ButterKnife.bind(this, pageView);
        setupRecyclerView(1);
        if (booksList != null) {
            booksListAdapter.setList(booksList);
        }
        return pageView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BooksFragmentVM booksFragmentVM = ViewModelProviders.of(this).get(BooksFragmentVM.class);
        booksFragmentVM.getBooksList().observe(this, modelForBooksRequest -> {
            booksList = modelForBooksRequest;
            booksListAdapter.setList(modelForBooksRequest);
            DialogFactory.dismissCustomFragmentDialog();
        });
        CustomDialogFragment dialogFragment = (CustomDialogFragment) Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                .findFragmentByTag(GlobalCons.CUSTOM_FRAGMENT_DIALOG_TAG + "Books");
        if (booksList == null && dialogFragment == null) {
            DialogFactory.showCustomFragmentDialog((AppCompatActivity) Objects.requireNonNull(getActivity()), "Please Wait", "Loading Books...");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getToolbar().setTitle("Recommended Books");
    }

    private void setupRecyclerView(int columnNumber) {
        booksListAdapter = new BooksListAdapter((AppCompatActivity) getActivity());
        if (columnNumber > 1) {
            booksRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnNumber));
        } else {
            booksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        }
        booksRecyclerView.setAdapter(booksListAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_item).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals("")) {
                    booksListAdapter.setList(booksList);
                } else {
                    List<ModelForBooksRequest.Item> tempList = new ArrayList<>();
                    ModelForBooksRequest tempModel = new ModelForBooksRequest();
                    tempModel.setKind(booksList.getKind());
                    tempModel.setTotalItems(booksList.getTotalItems());
                    tempModel.setItems(booksList.getItems());
                    for (int x = 0; x < booksList.getItems().size(); x++) {
                        String title, description;
                        title = booksList.getItems().get(x).getVolumeInfo().getTitle();
                        description = booksList.getItems().get(x).getVolumeInfo().getDescription();
                        if (title != null) {
                            title = booksList.getItems().get(x).getVolumeInfo().getTitle().toLowerCase();
                        } else {
                            title = "";
                        }
                        if (description != null) {
                            description = booksList.getItems().get(x).getVolumeInfo().getDescription().toLowerCase();
                        } else {
                            description = "";
                        }
                        if (title.contains(s.toLowerCase()) || description.contains(s.toLowerCase())) {
                            tempList.add(booksList.getItems().get(x));
                        }
                    }
                    tempModel.setItems(tempList);
                    booksListAdapter.setList(tempModel);
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        booksRecyclerView.setAdapter(null);
        unbinder.unbind();
        super.onDestroyView();
    }
}

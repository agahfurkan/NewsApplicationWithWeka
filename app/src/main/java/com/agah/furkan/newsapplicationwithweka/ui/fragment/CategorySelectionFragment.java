package com.agah.furkan.newsapplicationwithweka.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.agah.furkan.newsapplicationwithweka.GlobalCons;
import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.ui.adapter.CategorySelectionAdapter;
import com.agah.furkan.newsapplicationwithweka.ui.viewmodel.CategorySelectionVM;
import com.agah.furkan.newsapplicationwithweka.util.DialogFactory;
import com.agah.furkan.newsapplicationwithweka.util.FragmentHelperUtil;
import com.agah.furkan.newsapplicationwithweka.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CategorySelectionFragment extends BaseFragment {
    @BindView(R.id.category_select_list)
    RecyclerView categoryList;
    @BindView(R.id.empty_categoryList_image)
    ImageView emptyImage;
    @BindView(R.id.confirm_categories)
    FloatingActionButton confirmButton;
    private AppCompatActivity appCompatActivity;
    private List<String> dummyCategories, tempList, selectedCategories;
    private CategorySelectionAdapter categorySelectionAdapter;
    private boolean showCategoriesList;
    private CategorySelectionVM categorySelectionVM;
    private Unbinder unbinder;
    private SearchView searchView;

    public static CategorySelectionFragment newInstance() {
        return new CategorySelectionFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getToolbar().setTitle("Select Categories");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_selection, container, false);
        unbinder = ButterKnife.bind(this, view);
        showCategoriesList = true;
        categoryList.setAdapter(categorySelectionAdapter);
        selectedCategories = new ArrayList<>();
        dummyCategories = new ArrayList<>();
        dummyCategories.add("Android Development");
        dummyCategories.add("Ios Development");
        dummyCategories.add("Flutter");
        dummyCategories.add("Disease");
        dummyCategories.add("Lebron");
        dummyCategories.add("Google");
        appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            Objects.requireNonNull(appCompatActivity.getSupportActionBar()).show();
        }
        setupRecyclerView();
        confirmButton.setOnClickListener(v -> {
            if (selectedCategories.size() >= 1) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users/" + FirebaseAuth.getInstance().getUid());
                Map<String, String> categoriesMap = new HashMap<>();
                for (int x = 0; x < selectedCategories.size(); x++) {
                    categoriesMap.put(String.valueOf(x), selectedCategories.get(x));
                }
                myRef.setValue(categoriesMap);
                clearBackStack();
                FragmentHelperUtil.loadWithAnim(appCompatActivity, MainPageFragment.newInstance());
            } else {
                ToastUtil.showToast("At least 1 keyword must be selected.", appCompatActivity);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categorySelectionVM = ViewModelProviders.of(this).get(CategorySelectionVM.class);
        CustomDialogFragment dialogFragment = (CustomDialogFragment) Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                .findFragmentByTag(GlobalCons.CUSTOM_FRAGMENT_DIALOG_TAG);
        if (dialogFragment == null) {
            DialogFactory.showCustomFragmentDialog((AppCompatActivity) getActivity(), "Please Wait", "Loading Categories...");
        }
        categorySelectionVM.getList().observe(this, strings -> {
            DialogFactory.dismissCustomFragmentDialog();
            if (strings != null) {
                if (strings.size() == 0) {
                    emptyImage.setVisibility(View.VISIBLE);
                    categorySelectionAdapter.setList(strings);
                } else {
                    selectedCategories = strings;
                    if (showCategoriesList) {
                        categorySelectionAdapter.setList(strings);
                        emptyImage.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.menu_categorySelect_item).setVisible(false);
        menu.findItem(R.id.menu_recommended_news_item).setVisible(false);
        menu.findItem(R.id.menu_settings_item).setVisible(false);
        menu.findItem(R.id.menu_books_item).setVisible(false);
        searchView = (SearchView) menu.findItem(R.id.menu_search_item).getActionView();
        ImageView clearButton = searchView.findViewById(R.id.search_close_btn);
        final EditText searchText = searchView.findViewById(R.id.search_src_text);
        clearButton.setImageResource(R.drawable.ic_add_white_24dp);
        clearButton.setOnClickListener(v -> {
            categorySelectionVM.addCategory(searchText.getText().toString());
            menu.findItem(R.id.menu_search_item).collapseActionView();
            ToastUtil.showToast("New Category Added.", getActivity());
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.equals("")) {
                    tempList = new ArrayList<>();
                    for (int x = 0; x < dummyCategories.size(); x++) {
                        if (dummyCategories.get(x).toLowerCase().contains(s.toLowerCase())) {
                            tempList.add(dummyCategories.get(x));
                        }
                    }
                    categorySelectionAdapter.setList(tempList);
                }
                return false;
            }
        });
        menu.findItem(R.id.menu_search_item).setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                showCategoriesList = false;
                emptyImage.setVisibility(View.GONE);
                categorySelectionAdapter.setList(dummyCategories);
                categorySelectionAdapter.setRemoveFlag(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                categorySelectionAdapter.setList(selectedCategories);
                if (!selectedCategories.isEmpty()) {
                    emptyImage.setVisibility(View.GONE);
                } else {
                    emptyImage.setVisibility(View.VISIBLE);
                }
                showCategoriesList = true;
                categorySelectionAdapter.setRemoveFlag(true);
                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        categoryList.setAdapter(null);
        unbinder.unbind();
        super.onDestroyView();
    }

    private void clearBackStack() {
        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private void setupRecyclerView() {
        categorySelectionAdapter = new CategorySelectionAdapter(categorySelectionVM);
        int columnNumber = 1;
        if (columnNumber > 1) {
            categoryList.setLayoutManager(new GridLayoutManager(appCompatActivity, columnNumber));
        } else {
            categoryList.setLayoutManager(new LinearLayoutManager(appCompatActivity));
        }
        categoryList.setAdapter(categorySelectionAdapter);
    }
}

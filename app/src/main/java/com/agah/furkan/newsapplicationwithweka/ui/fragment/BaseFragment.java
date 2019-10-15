package com.agah.furkan.newsapplicationwithweka.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.util.FragmentHelperUtil;
import com.agah.furkan.newsapplicationwithweka.util.PermissionUtil;
import com.agah.furkan.newsapplicationwithweka.util.UserManager;

public class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        PermissionUtil.checkPerms(getContext(), getActivity());
        if (UserManager.getUserInfo(getActivity()).getUsername() == null) {
            if (getFragmentManager() != null && !(getFragmentManager().findFragmentById(R.id.fragment_container) instanceof LoginFragment)) {
                FragmentHelperUtil.loadWithAnim(getActivity(), LoginFragment.newInstance());
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public ActionBar getToolbar() {
        return ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();
    }
}

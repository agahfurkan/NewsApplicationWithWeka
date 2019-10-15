package com.agah.furkan.newsapplicationwithweka.ui.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    public static CustomDialogFragment newInstance(String title, String message) {
        Bundle args = new Bundle();
        args.putString("dialogTitle", title);
        args.putString("dialogMessage", message);
        CustomDialogFragment fragment = new CustomDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            return ProgressDialog.show(getActivity(), getArguments().getString("dialogTitle"), getArguments().getString("dialogMessage"));
        } else {
            return ProgressDialog.show(getActivity(), "Empty Title", "Empty Message.");
        }
    }
}


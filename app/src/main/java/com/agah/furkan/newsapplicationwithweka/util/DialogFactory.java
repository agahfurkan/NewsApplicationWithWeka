package com.agah.furkan.newsapplicationwithweka.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.agah.furkan.newsapplicationwithweka.GlobalCons;
import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.data.web.ModelForNewsRequest;
import com.agah.furkan.newsapplicationwithweka.ui.fragment.CustomDialogFragment;
import com.agah.furkan.newsapplicationwithweka.ui.fragment.NewsFocusFragment;

public class DialogFactory {
    private static DialogFragment dialog;

    public static void createAlertDialog(String dialogMessage, String dialogTitle, Activity activity) {
        TextView titleTextView, messageTextView;
        Button positiveButton, negativeButton;
        LayoutInflater inflater = activity.getLayoutInflater();
        final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        final View layoutView = inflater.inflate(R.layout.custom_dialog_1, null);
        positiveButton = layoutView.findViewById(R.id.dialog_positive_button);
        negativeButton = layoutView.findViewById(R.id.dialog_negative_button);
        titleTextView = layoutView.findViewById(R.id.dialog_title);
        messageTextView = layoutView.findViewById(R.id.dialog_message);
        positiveButton.setOnClickListener(v -> {
            AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
            fadeOut.setDuration(500);
            layoutView.startAnimation(fadeOut);
            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    alertDialog.dismiss();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        });
        negativeButton.setOnClickListener(v -> {
        });
        titleTextView.setText(dialogTitle);
        messageTextView.setText(dialogMessage);
        alertDialog.setView(layoutView);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public static void showCustomFragmentDialog(AppCompatActivity activity, String title, String message) {
        if (dialog == null) {
            dialog = CustomDialogFragment.newInstance(title, message);
            dialog.setCancelable(false);
            dialog.show(activity.getSupportFragmentManager(), GlobalCons.CUSTOM_FRAGMENT_DIALOG_TAG);
        }
    }

    public static void focusNewsDetailDialog(ModelForNewsRequest.Article newsModel, FragmentManager fragmentManager) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("focusNewsDetail");
        if (prev != null) {
            ft.remove(prev).commit();
        }
        NewsFocusFragment.newInstance(newsModel).show(fragmentManager, "focusNewsDetail");
    }

    public static void dismissFocusNewsDetailDialog(FragmentManager fragmentManager) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("focusNewsDetail");
        if (prev != null) {
            ft.remove(prev).commit();
        }
    }

    public static void dismissCustomFragmentDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}

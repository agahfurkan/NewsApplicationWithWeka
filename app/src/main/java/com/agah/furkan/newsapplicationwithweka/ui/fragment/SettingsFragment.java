package com.agah.furkan.newsapplicationwithweka.ui.fragment;

import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Objects;

import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.broadcast.NotificationBroadCast;
import com.agah.furkan.newsapplicationwithweka.broadcast.WekaBroadCast;
import com.agah.furkan.newsapplicationwithweka.util.AlarmManagerHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingsFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.updateModel_switch)
    Switch updateModel;
    @BindView(R.id.recommendationSwitch)
    Switch recommendationSwitch;
    private Unbinder unbinder;

    static public SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageView = inflater.inflate(R.layout.settings_fragment, container, false);
        unbinder = ButterKnife.bind(this, pageView);
        boolean notificationAlarm = (PendingIntent.getBroadcast(getContext(), 0, new Intent(getContext(), NotificationBroadCast.class), PendingIntent.FLAG_NO_CREATE) != null);
        boolean modelUpdateAlarm = (PendingIntent.getBroadcast(getContext(), 1, new Intent(getContext(), WekaBroadCast.class), PendingIntent.FLAG_NO_CREATE) != null);
        if (notificationAlarm) {
            recommendationSwitch.setChecked(true);
        } else {
            recommendationSwitch.setChecked(false);
        }
        if (modelUpdateAlarm) {
            updateModel.setChecked(true);
        } else {
            updateModel.setChecked(false);
        }
        updateModel.setOnCheckedChangeListener(this);
        recommendationSwitch.setOnCheckedChangeListener(this);
        return pageView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getToolbar().setTitle("Settings");
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.updateModel_switch) {
            if (isChecked) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> AlarmManagerHelper.setWekaModelUpdateAlarm(Objects.requireNonNull(getContext()), selectedHour, selectedMinute), hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            } else {
                AlarmManagerHelper.cancelModelAlarm(Objects.requireNonNull(getActivity()).getApplicationContext());
            }
        } else if (buttonView.getId() == R.id.recommendationSwitch) {
            if (isChecked) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) -> AlarmManagerHelper.setNotificationAlarm(Objects.requireNonNull(getContext()), selectedHour, selectedMinute), hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            } else {
                AlarmManagerHelper.cancelNotificationAlarm(Objects.requireNonNull(getActivity()).getApplicationContext());
            }

        }
    }
}

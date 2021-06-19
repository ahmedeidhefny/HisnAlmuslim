package com.ahmed.hisnalmuslimapp.ui.hadithsList;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmed.hisnalmuslimapp.Constants;
import com.ahmed.hisnalmuslimapp.R;
import com.ahmed.hisnalmuslimapp.data.binding.FragmentDataBindingComponent;
import com.ahmed.hisnalmuslimapp.data.local.entity.Hadiths;
import com.ahmed.hisnalmuslimapp.databinding.FragmentHadithListBinding;
import com.ahmed.hisnalmuslimapp.ui.base.BaseFragment;
import com.ahmed.hisnalmuslimapp.ui.base.DataBoundListAdapter;
import com.ahmed.hisnalmuslimapp.util.AutoClearedValue;
import com.ahmed.hisnalmuslimapp.util.Utils;
import com.ahmed.hisnalmuslimapp.viewModel.HadithsViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 1/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class HadithsListFragment extends BaseFragment implements DataBoundListAdapter.DiffUtilDispatchedInterface,
        HadithsListAdapter.BankClickCallback {//, TimePickerDialog.OnTimeSetListener {

    private final androidx.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private HadithsViewModel hadithsViewModel;
    public HadithsListAdapter nvAdapter;


    @VisibleForTesting
    private AutoClearedValue<FragmentHadithListBinding> binding;
    private AutoClearedValue<HadithsListAdapter> adapter;

    private String collectionId, lang, total;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHadithListBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hadith_list, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);

        setHasOptionsMenu(true);
        return binding.get().getRoot();
    }

    @Override
    public void onDispatched() {
        if (hadithsViewModel.loadingDirection == Utils.LoadingDirection.top) {

            if (binding.get().notificationList != null) {

                LinearLayoutManager layoutManager = (LinearLayoutManager)
                        binding.get().notificationList.getLayoutManager();

                layoutManager.setOrientation(RecyclerView.HORIZONTAL);

                if (layoutManager != null) {
                    layoutManager.scrollToPosition(0);
                }
            }
        }
    }

    @Override
    protected void initUIAndActions() {

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.get().notificationList.setLayoutManager(layoutManager);
        binding.get().notificationList.setHasFixedSize(true);
    }

    @Override
    protected void initViewModels() {
        hadithsViewModel = new ViewModelProvider(this, viewModelFactory).get(HadithsViewModel.class);
    }

    @Override
    protected void initAdapters() {
        try {
            if (getActivity() != null) {
                if (getActivity().getIntent().getExtras() != null) {
                    collectionId = getActivity().getIntent().getExtras().getString(Constants.COLLECTION_ID);
                    lang = getActivity().getIntent().getExtras().getString(Constants.lang);
                    total = getActivity().getIntent().getExtras().getString(Constants.TOTAL);
                }
            }
        } catch (Exception e) {
            Utils.errorLog("", e);
        }

        nvAdapter = new HadithsListAdapter(dataBindingComponent,
                this,
                total,
                pref,
                this);

        this.adapter = new AutoClearedValue<>(this, nvAdapter);
        binding.get().notificationList.setAdapter(nvAdapter);
    }

    @Override
    protected void initData() {


        LoadData();

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int fontSize = pref.getFontSize();
        int id = item.getItemId();

        if (id == R.id.action_increase) {
            if (fontSize < 30) {
                fontSize++;
                pref.setFontSize(fontSize);
                nvAdapter.notifyDataSetChanged();
            }
            return true;

        } else if (id == R.id.action_decrease) {
            if (fontSize > 10) {
                fontSize--;
                pref.setFontSize(fontSize);
                nvAdapter.notifyDataSetChanged();
            }
            return true;
        } else if (id == R.id.action_alarm) {
            showCustomDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCustomDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.alarm_custom_dialog);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        RelativeLayout dialog_cancel = dialog.findViewById(R.id.dialog_cancel);
        Button changeBtn = dialog.findViewById(R.id.change_time);
        TextView timeChosen = dialog.findViewById(R.id.time_chosen);

        showChosenTime(timeChosen);

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
//                DialogFragment timePicker = new TimePickerFragment();
//                timePicker.show(getActivity().getSupportFragmentManager(), "time picker");
//
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = 0, minute = 0;
                if (pref.getHourTime() != 0 && pref.getMinuteTime() != 0) {
                    hour = pref.getHourTime();
                    minute = pref.getMinuteTime();
                } else {
                    hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    minute = mcurrentTime.get(Calendar.MINUTE);
                }
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        pref.saveHourTime(selectedHour);
                        pref.saveMinuteTime(selectedMinute);

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, selectedHour);
                        c.set(Calendar.MINUTE, selectedMinute);
                        c.set(Calendar.SECOND, 0);
                        updateTimeText(c, timeChosen);
                        startAlarm(c);

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void showChosenTime(TextView timeChosen) {
        int hour = 0, minute = 0;
        if (pref.getHourTime() != 0 && pref.getMinuteTime() != 0) {
            hour = pref.getHourTime();
            minute = pref.getMinuteTime();

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, 0);
            updateTimeText(c, timeChosen);
        }
    }

    private void updateTimeText(Calendar c, TextView timeChosen) {
        String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        timeChosen.setText(timeText);
    }

    private void startAlarm(Calendar c) {
        Log.e("startAlarm", "startAlarm");
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), ZkrAlarmReceiver.class);
        intent.putExtra(Constants.COLLECTION_NAME, lang);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void LoadData() {

        List<Hadiths> hadiths = hadithsViewModel.getHadiths(collectionId);

        if (hadiths != null) {
            // Update the data
            replaceData(hadiths);
        } else {
            Utils.log("Hadiths list are empty");
        }

        hadithsViewModel.setLoadingState(false);

    }

    private void replaceData(List<Hadiths> list) {

        adapter.get().replace(list);
        binding.get().executePendingBindings();

    }

    @Override
    public void onNavigateToNextItem(int position) {
        Log.e("x3", position + "");
        binding.get().notificationList.scrollToPosition(position + 1);
        binding.get().notificationList.scheduleLayoutAnimation();
    }

//    @Override
//    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOf) {
//
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        c.set(Calendar.MINUTE, minuteOf);
//        c.set(Calendar.SECOND, 0);
//        updateTimeText(c);
//        startAlarm(c);
//
//    }
//
//    private void updateTimeText(Calendar c) {
//        String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
//        timeChosen.setText(timeText);
//    }
}

package com.ashzel.period.ui.home;

import static com.ashzel.period.MainActivity.PERIOD_CYCLE;
import static com.ashzel.period.MainActivity.PERIOD_DATE_BEGIN;
import static com.ashzel.period.MainActivity.PREFS_NAME;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.EventDay;
import com.ashzel.period.R;
import com.ashzel.period.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * https://github.com/Applandeo/Material-Calendar-View
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        com.applandeo.materialcalendarview.CalendarView calendarView = binding.calendar;

        SharedPreferences settings = requireActivity().getSharedPreferences(PREFS_NAME, 0);
        String period_begin = settings.getString(PERIOD_DATE_BEGIN, "");
        Integer period_cycle = settings.getInt(PERIOD_CYCLE, 1);
        Date date = new Date();
        date.setTime(Long.valueOf(period_begin));

        binding.lastPeriod.setText(date.getDate());

        if(!period_begin.equals("")) {

            List<Calendar> calendars = new ArrayList<>();
            List<EventDay> events = new ArrayList<>();
            Calendar firstPeriod = Calendar.getInstance();
            firstPeriod.setTime(date);
            events.add(new EventDay(firstPeriod, R.drawable.ic_baseline_local_fire_department_24));

            for(int i=1; i <= 60; i++) {
                //Periods
                Calendar period = Calendar.getInstance();
                period.setTime(date);
                int periodDay = period_cycle*i;
                period.add(Calendar.DATE, periodDay);
                events.add(new EventDay(period, R.drawable.ic_baseline_local_fire_department_24));
                calendars.add(period);

                //Ovulation
                Calendar ovulation = Calendar.getInstance();
                ovulation.setTime(date);
                int ovulationDay = periodDay-14;
                ovulation.add(Calendar.DATE, ovulationDay);
                events.add(new EventDay(ovulation, R.drawable.ic_baseline_donut_small_24));

                //Fertility
                for(int j=2; j>=0; j--){
                    Calendar fertilityDay = Calendar.getInstance();
                    fertilityDay.setTime(date);
                    fertilityDay.add(Calendar.DATE, ovulationDay-j);
                    events.add(new EventDay(fertilityDay, R.drawable.ic_baseline_child_friendly_24));
                }
            }
            calendarView.setHighlightedDays(calendars);
            calendarView.setEvents(events);
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
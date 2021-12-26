package com.ashzel.period.ui.datePicker;

import static com.ashzel.period.MainActivity.PERIOD_CYCLE;
import static com.ashzel.period.MainActivity.PERIOD_DATE_BEGIN;
import static com.ashzel.period.MainActivity.PREFS_NAME;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ashzel.period.MainActivity;
import com.ashzel.period.R;
import com.ashzel.period.databinding.FragmentDatepickerBinding;
import com.ashzel.period.ui.home.HomeFragment;
import com.google.android.material.datepicker.MaterialDatePicker;

public class PeriodDatePickerFragment extends Fragment {

    private FragmentDatepickerBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDatepickerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView showSelectedDate = binding.showSelectedDate;
        final Button mPickDateButton = binding.pickDateButton;

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText(getResources().getText(R.string.calendar_title));

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();


        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        mPickDateButton.setOnClickListener(
                v -> materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER")
        );

        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {
                    int cycle = 1;
                    if (binding.cycleEdit.getText().toString().equals("")) {
                        cycle = 28;

                    } else {
                        cycle = Math.abs(Integer.parseInt(binding.cycleEdit.getText().toString()));
                    }
                    showSelectedDate.setText(getResources().getText(R.string.selected_date) + materialDatePicker.getHeaderText());
                    editor.putInt(PERIOD_CYCLE, cycle);

                    editor.putString(PERIOD_DATE_BEGIN, materialDatePicker.getSelection().toString());
                    editor.commit();

                    Fragment homeFragment = new HomeFragment();
                    ((MainActivity) getActivity()).replaceFragments(homeFragment.getClass());
                });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
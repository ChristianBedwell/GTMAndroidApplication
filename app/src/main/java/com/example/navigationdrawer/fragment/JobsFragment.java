package com.example.navigationdrawer.fragment;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

import com.example.navigationdrawer.R;
import com.kd.dynamic.calendar.generator.ImageGenerator;

public class JobsFragment extends Fragment {

    private View myView;
    private EditText mDateEditText;
    private Calendar mCurrentDate;
    private Bitmap mGeneratedDateIcon;
    private ImageGenerator mImageGenerator;
    private ImageView mDisplayGeneratedImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_jobs, container,false);
        mImageGenerator = new ImageGenerator(this.getActivity());
        mDateEditText = (EditText) myView.findViewById(R.id.txtDateEntered);
        mDisplayGeneratedImage = (ImageView) myView.findViewById(R.id.imageGen);

        mImageGenerator.setIconSize(50,50);
        mImageGenerator.setDateSize(30);
        mImageGenerator.setMonthSize(10);

        mImageGenerator.setDatePosition(42);
        mImageGenerator.setMonthPosition(14);

        mImageGenerator.setDateColor(Color.parseColor("#3c6eaf"));
        mImageGenerator.setMonthColor(Color.WHITE);

        mImageGenerator.setStorageToSDCard(true);

        mDateEditText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mCurrentDate = Calendar.getInstance();
                int year = mCurrentDate.get(Calendar.YEAR);
                int month = mCurrentDate.get(Calendar.MONTH);
                int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog((FragmentActivity)view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        mDateEditText.setText(selectedDay + "-"+selectedMonth + "-"+selectedYear);

                        mCurrentDate.set(selectedYear, selectedMonth, selectedDay);
                        mGeneratedDateIcon = mImageGenerator.generateDateImage(mCurrentDate, R.drawable.empty_calendar);
                        mDisplayGeneratedImage.setImageBitmap(mGeneratedDateIcon);
                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });
        return myView;
    }
}

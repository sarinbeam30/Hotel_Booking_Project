package com.example.user.hotel_booking;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Calendar mCurrentDate;
    int day, month, year;
    String day_of_week, month_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.check_in_date);

        //GET_NOW_DATE
        mCurrentDate = Calendar.getInstance();
        Date now = new Date();

        //INTEGER_DAY_MONTH_YEAR
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);
        System.out.println("CHECK THIS");

        //GET_TIME_IN_STRING
        SimpleDateFormat dateFormatter = new SimpleDateFormat("E MMM d yyyy");
        String day_of_week = dateFormatter.format(now).substring(0,3);
        String month_string = dateFormatter.format(now).substring(4,8);



        //tv.setText(day + " /" + (month+1) + " /" + year);
        tv.setText(day + "\t" + day_of_week + "\t" + month_string);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            Date setDate = new Date(year, month, dayOfMonth-1);
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("E MMM d yyyy");
                            String day = dateFormatter.format(setDate).substring(8,11);
                            String day_of_week = dateFormatter.format(setDate).substring(0,3);
                            String month_string = dateFormatter.format(setDate).substring(4,8);

                            //tv.setText(dayOfMonth + " /" + (month + 1) + " /" + year);
                            tv.setText(dayOfMonth + "\t" + day_of_week + "\t " + month_string);
                    }
                } ,year, month, day);
                datePickerDialog.show();
            }
        });
    }
}

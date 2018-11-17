package com.example.user.hotel_booking;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public String check_in_day, check_in_month_string, check_in_year;
    public String check_out_day, check_out_month, check_out_year;
    int check_in_month_int;
    TextView text_view_check_in_date, text_view_check_out_date;
    Calendar mCurrentDate;
    int current_day, current_month, current_year;

    private View.OnClickListener check_out_date_function = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    //SET_DATE
                    Date setDate = new Date(year, month, dayOfMonth);
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("E MMM d yyyy");

                    String day_of_week = dateFormatter.format(setDate).substring(0,3);
                    String month_string = dateFormatter.format(setDate).substring(4,8);

                    check_out_day = Integer.toString(dayOfMonth);
                    check_out_month = month_string;
                    check_out_year = Integer.toString(year);

                    //STORE_VALUE_IN_ANDROID_SHARED_PREFERENCES
                    SharedPreferences checkout = getSharedPreferences("check_out",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = checkout.edit();
                    editor.putString("check_out_day",check_out_day);
                    editor.putString("check_out_month", check_out_month);
                    editor.putString("check_out_year", check_out_year);
                    editor.commit();

                    //SET_TEXT_IN_TEXT_VIEW
                    text_view_check_out_date.setText(dayOfMonth + "\t " + day_of_week + "\t " + month_string);

                    //TOAST
                    int duration = Toast.LENGTH_LONG;
                    Context context = getApplicationContext();
                    CharSequence text = "Check out : " + check_out_day + " " + check_out_month + " " + check_out_year;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }, current_year, current_month, current_day+1);

             SharedPreferences sp = getSharedPreferences("check_in", Context.MODE_PRIVATE);
             String check_in_day = sp.getString("check_in_day","1");
             int check_in_month = sp.getInt("check_in_month",2);
             String check_in_year = sp.getString("check_in_year","3");

             Date set_min_date = new Date(Integer.parseInt(check_in_year), check_in_month, Integer.parseInt(check_in_day) + 1);
             System.out.println(set_min_date.toString());
             datePickerDialog.getDatePicker().setMinDate(set_min_date.getTime());
            datePickerDialog.show();
        }
    };

    private View.OnClickListener check_in_date_function = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    //SET_DATE
                    Date setDate = new Date(year, month, dayOfMonth-1);
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("E MMM d yyyy");

                    String day_of_week = dateFormatter.format(setDate).substring(0,3);
                    String month_string = dateFormatter.format(setDate).substring(4,8);

                    check_in_day = Integer.toString(dayOfMonth);
                    check_in_month_string = month_string;
                    check_in_month_int = month + 1;
                    check_in_year = Integer.toString(year);

                    //STORE_VALUE_IN_ANDROID_SHARED_PREFERENCES
                    SharedPreferences checkin = getSharedPreferences("check_in",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = checkin.edit();
                    editor.putString("check_in_day",check_in_day);
                    editor.putInt("check_in_month", check_in_month_int);
                    editor.putString("check_in_year", check_in_year);
                    editor.commit();

                    //SET_TEXT_IN_TEXT_VIEW
                    text_view_check_in_date.setText(dayOfMonth + "\t " + day_of_week + "\t " + month_string);

                    //TOAST
                    int duration = Toast.LENGTH_LONG;
                    Context context = getApplicationContext();
                    CharSequence text = "Check in : " + check_in_day + " " + check_in_month_string + "(" + check_in_month_int + ")" + " " + check_in_year;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            } , current_year, current_month, current_day);
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            datePickerDialog.show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TEXT_VIEW_CHECK_IN_DATE
        text_view_check_in_date = (TextView) findViewById(R.id.check_in_DatePickerDialog);

        //GET_NOW_DATE
        mCurrentDate = Calendar.getInstance();
        Date now = new Date();

        //INTEGER_DAY_MONTH_YEAR
        current_day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        current_month = mCurrentDate.get(Calendar.MONTH);
        current_year = mCurrentDate.get(Calendar.YEAR);


        //GET_TIME_IN_STRING
        SimpleDateFormat dateFormatter = new SimpleDateFormat("E MMM d yyyy");
        String day_of_week = dateFormatter.format(now).substring(0,3);
        String month_string = dateFormatter.format(now).substring(4,8);

        //SET_TEXT_CHECK_IN_DATE
        text_view_check_in_date.setText(current_day + "\t " + day_of_week + "\t " + month_string);
        text_view_check_in_date.setOnClickListener(check_in_date_function);


        //TEXT_VIEW_CHECK_OUT_DATE
        text_view_check_out_date = (TextView) findViewById(R.id.check_out_DatePickerDialog);

        //SET_TEXT_CHECK_OUT_DATE
        text_view_check_out_date.setText(current_day + 1 + "\t " + day_of_week + "\t " + month_string);
        text_view_check_out_date.setOnClickListener(check_out_date_function);



    }


}

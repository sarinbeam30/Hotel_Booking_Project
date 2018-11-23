package com.example.user.hotel_booking;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN";
    public String check_in_day, check_in_month_string, check_in_year;
    public String check_out_day, check_out_month, check_out_year;

    int check_in_month_int;

    TextView text_view_check_in_date, text_view_check_out_date;
    TextView room_and_family;

    EditText f, l,e, p;

    Button next_btn;

    ConstraintLayout check_in_and_check_out ;


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
                    text_view_check_out_date.setText(dayOfMonth + "\t " + month_string + "\t " + year);

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

             //Date set_min_date = new Date(Integer.parseInt(check_in_year), check_in_month, Integer.parseInt(check_in_day) + 1);
             Calendar set_min_date_calender = new GregorianCalendar(Integer.parseInt(check_in_year), check_in_month-1, Integer.parseInt(check_in_day));
             set_min_date_calender.add(Calendar.DATE,1);
             //System.out.println(set_min_date.toString());
             datePickerDialog.getDatePicker().setMinDate(set_min_date_calender.getTimeInMillis());
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

                    String day_of_week = dateFormatter.format(setDate).substring(0,3);              //Mon TO Sun
                    String month_string = dateFormatter.format(setDate).substring(4,8);             //Jan To Dec

                    check_in_day = Integer.toString(dayOfMonth);                                    //1..31
                    check_in_month_string = month_string;                                           //Jan To Dec
                    check_in_month_int = month + 1;                                                 //1...12
                    check_in_year = Integer.toString(year);                                         //2018

                    //CHECK_OUT_DAY_OF_WEEKS_STRING
                    Date set_check_out_day_of_weeks_string = new Date(year, month, dayOfMonth);
                    String check_out_days_of_week_string = dateFormatter.format(set_check_out_day_of_weeks_string).substring(0,3);

                    //STORE_VALUE_IN_ANDROID_SHARED_PREFERENCES
                    SharedPreferences checkin = getSharedPreferences("check_in",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = checkin.edit();
                    editor.putString("check_in_day",check_in_day);
                    editor.putString("check_in_day_of_week", check_out_days_of_week_string);
                    editor.putInt("check_in_month", check_in_month_int);
                    editor.putString("check_in_year", check_in_year);
                    editor.commit();

                    //SET_TEXT_IN_TEXT_VIEW
                    text_view_check_in_date.setText(dayOfMonth + "\t " + month_string + "\t " + year);

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

    //SECOND_PAGE
    private  View.OnClickListener second_page = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent room_and_fam = new Intent(MainActivity.this, room_and_fam_activity.class);
            room_and_fam.putExtra("CHECK_IN_DATE", check_in_day);
            startActivity(room_and_fam);
            finish();
        }
    };

    //NAME_VALIDATION
    //FIRST_NAME
    private View.OnFocusChangeListener first_name_validation = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(f.getText().toString().matches("^[A-Z][a-z]+$")){
                /*
                Drawable customErrorDrawable = getResources().getDrawable(R.drawable.ic_error_black_24dp);
                customErrorDrawable.setBounds(0,0,customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                */
            }
            else {
                f.setError("Invalid first_name");
            }
        }
    };

    //LAST_NAME
    private View.OnFocusChangeListener last_name_validation = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(l.getText().toString().matches("^[A-Z][a-z]+$")){
            }
            else{
                l.setError("Invalid Last_name");
            }
        }
    };

    //EMAIL_VALIDATION
    private View.OnFocusChangeListener email_validation = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(e.getText().toString().matches("    ")){
                e.setError("Invalid email");
            }
        }
    };

    //PHONE_VALIDATION
    private View.OnFocusChangeListener phone_validation = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(p.getText().toString().length() > 10 || p.getText().toString().length() < 8)
            {
                p.setError("Invalid phone number");
            }
            if(p.getText().toString() == null || p.getText().toString() == ""){
                p.setText("000000000");
            }
        }
    };

    //NEXT_BUTTON
    private View.OnClickListener select_room = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //STORE_VALUE_OF_NAME_AND_LAST_NAME_IN_ANDROID_SHARED_PREFERENCES
            SharedPreferences name = getSharedPreferences("NAME_AND_LAST_NAME", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_name = name.edit();
            editor_name.putString("FIRST_NAME", f.getText().toString());
            editor_name.putString("LAST_NAME", l.getText().toString());
            editor_name.commit();

            SharedPreferences email = getSharedPreferences("EMAIL", Context.MODE_PRIVATE);
            SharedPreferences.Editor  editor_email = email.edit();
            editor_email.putString("EMAIL", e.getText().toString());
            editor_email.commit();

            SharedPreferences phone_number = getSharedPreferences("PHONE_NUMBER", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_phone_number = phone_number.edit();
            editor_phone_number.putString("PHONE_NUMBER", p.getText().toString());
            editor_phone_number.commit();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent_get_from_second_page_to_first_page
        Intent intent = getIntent();
        int room = intent.getIntExtra("ROOM",1);
        int adult = intent.getIntExtra("ADULT", 1);
        int children = intent.getIntExtra("CHILDREN",0);


        Log.d(TAG, "OnCreate");

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
        if(check_in_day == null && check_in_month_string == null && check_in_year == null){
            text_view_check_in_date.setText(current_day + "\t " + month_string + "\t " + current_year);
        }
        else{
            text_view_check_in_date.setText(check_in_day + "\t " + check_in_month_string + "\t " + check_in_year);
        }
        text_view_check_in_date.setOnClickListener(check_in_date_function);


        //TEXT_VIEW_CHECK_OUT_DATE
        text_view_check_out_date = (TextView) findViewById(R.id.check_out_DatePickerDialog);

        //SET_TEXT_CHECK_OUT_DATE
        SharedPreferences sp = getSharedPreferences("check_in", Context.MODE_PRIVATE);
        String check_in_day_int = sp.getString("check_in_day","1");                         // 1-21
        String check_in_day_of_week = sp.getString("check_in_day_of_week", "2");        // MONDAY TO FRIDAY
        int check_in_month = sp.getInt("check_in_month",3);                             // 1-12
        String check_in_year = sp.getString("check_in_year","4");                       // 2018....

        text_view_check_out_date.setText(current_day+1 + "\t " + month_string + "\t " + check_in_year);
        text_view_check_out_date.setOnClickListener(check_out_date_function);


        //TEXT_VIEW_ROOM_AND_FAM
        room_and_family =  (TextView) findViewById(R.id.room_and_family_textview);
        room_and_family.setText(String.valueOf(room) + " Room " + String.valueOf(adult) + " Adult " + String.valueOf(children) + " Children");
        room_and_family.setOnClickListener(second_page);

        //FRAME_LAYOUT_CHECK_IN_AND_CHECK_OUT
        check_in_and_check_out = (ConstraintLayout) findViewById(R.id.check_in_and_check_out);
        //check_in_and_check_out.setBackgroundColor(getResources().getColor(R.color.Beige));
        //check_in_and_check_out.setForeground(getResources().getColor(R.color.Black));


        //GET_FIRST_NAME_AND_LAST_NAME
        f = (EditText) findViewById(R.id.first_name_editText);
        f.setOnFocusChangeListener(first_name_validation);
        l = (EditText) findViewById(R.id.last_name_editText);
        l.setOnFocusChangeListener(last_name_validation);

        //GET_E_MAIL_ADRRESS
        e = (EditText) findViewById(R.id.email_editText);
        e.setOnFocusChangeListener(email_validation);

        //GET_PHONE_NUMBER
        p = (EditText) findViewById(R.id.phone_editText);
        p.setOnFocusChangeListener(phone_validation);

        next_btn = (Button) findViewById(R.id.next_button);
        next_btn.setOnClickListener(select_room);

    }


}

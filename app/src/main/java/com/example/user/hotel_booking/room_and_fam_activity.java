package com.example.user.hotel_booking;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;


public class room_and_fam_activity extends AppCompatActivity {
    private static final String TAG = "ROOM";
    private Button room_minus, room_add , adults_minus, adults_add, children_minus, children_add , ok_button;

    TextView room_textview, adults_textview, children_textview, room_and_family_textview;

    int room = 1,  adult = 1 , children = 0;

    private View.OnClickListener first_page = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent second_page_to_first_page = new Intent(room_and_fam_activity.this, MainActivity.class);
            if(room == 0){room = 1;}
            if(adult == 0){ adult = 1;}
            if(children == 0){ children = 0;}

            second_page_to_first_page.putExtra("ROOM",room);
            second_page_to_first_page.putExtra("ADULT", adult);
            second_page_to_first_page.putExtra("CHILDREN",children);


            //STORE_VALUE_IN_ANDROID_SHARED_PREFERENCES
            SharedPreferences room_and_family = getSharedPreferences("room_and_family", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = room_and_family.edit();
            editor.putInt("ROOM", room);
            editor.putInt("ADULT", adult);
            editor.putInt("CHILDREN", children);
            editor.commit();

            startActivity(second_page_to_first_page);

            //CHECK_VALUE

            System.out.println("ROOM : " + room);
            System.out.println("ADULT : " + adult);
            System.out.println("CHILDREN : " + children);


        }
    };

    //DECREASE
    private View.OnClickListener decrease_room = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //room = 1;
            if(room <= 1){
                room = 1;
                room_minus.setEnabled(false);
                room_textview.setText("1 Room");
                //System.out.println("room == 1");
            }

            else if(room >= 2){
                System.out.println("ROOM >= 2 : " + room);
                if(room <= 2){
                    room = 1;
                    room_minus.setEnabled(false);
                    room_textview.setText("1 Room");
                    //System.out.println("room == 1 in room >= 2");
                }

                else{
                    room_minus.setEnabled(true);
                    room -= 1;
                    room_textview.setText(String.valueOf(room) + " Rooms");
                    //System.out.println("room -= 1");
                }
            }


        }
    };

    private View.OnClickListener decrease_adult = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //adult = 1;
            if(adult <= 1){
                adult = 1;
                adults_minus.setEnabled(false);
                adults_textview.setText("1 Adult");
                //System.out.println("room == 1");
            }

            else if(adult >= 2){
                //System.out.println("ROOM >= 2 : " + room);
                if(adult <= 2){
                    if(adult == room){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(room_and_fam_activity.this);
                        alertDialogBuilder.setMessage("Each room needs to have at least one adult").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                        adult = adult;
                        adults_textview.setText(String.valueOf(adult) + " Adults");

                    }

                    else if(adult != room){
                        adult = 1;
                        adults_minus.setEnabled(false);
                        adults_textview.setText("1 Adult");
                        //System.out.println("room == 1 in room >= 2");
                    }
                }

                else{
                    if(adult == room){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(room_and_fam_activity.this);
                        alertDialogBuilder.setMessage("Each room needs to have at least one adult").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                        adult = adult;
                        adults_textview.setText(String.valueOf(adult) + " Adults");

                    }
                    else if(adult != room){
                        adults_minus.setEnabled(true);
                        adult -= 1;
                        adults_textview.setText(String.valueOf(adult) + " Adults");
                        //System.out.println("room -= 1");
                    }
                }
            }
        }
    };

    private View.OnClickListener decrease_children = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           if(children <= 1){
               children = 0;
               children_minus.setEnabled(false);
               children_textview.setText(String .valueOf(children + " Child"));
               //System.out.println("Child_1");
           }
           else{
               children -= 1;
               if(children == 1){
                   children_textview.setText(String.valueOf(children) + " Child");
                   //System.out.println("Child_2");
               }
               else if(children > 1){
                   children_textview.setText(String.valueOf(children) + " Children");
               }

           }
        }
    };

    //INCREASE
    private View.OnClickListener increse_room = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            room += 1;
            room_minus.setEnabled(true);
            //System.out.println("room += 1");
            room_textview.setText(String.valueOf(room) + " Rooms");

        }
    };

    private View.OnClickListener increase_adult = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adult += 1;
            adults_minus.setEnabled(true);
            adults_textview.setText(String.valueOf(adult) + " Adults");
        }
    };

    private View.OnClickListener increase_children = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            children += 1;
            children_minus.setEnabled(true);
            if(children == 1){children_textview.setText(String.valueOf(children) + " Child");}
            else{children_textview.setText(String.valueOf(children) + " Children");}
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_and_fam);
        Log.d(TAG, "OnCreate");

        TextView back_arrow = (TextView) findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(first_page);


        //ROOM
        room_textview = (TextView) findViewById(R.id.room_textview);
        room_minus = (Button) findViewById(R.id.room_minus_button);
        room_minus.setEnabled(false);
        room_minus.setOnClickListener(decrease_room);
        room_add = (Button) findViewById(R.id.room_plus_button);
        room_add.setOnClickListener(increse_room);

        //ADULT
        adults_textview = (TextView) findViewById(R.id.adult_textview);
        adults_minus = (Button) findViewById(R.id.adult_minus_button);
        adults_minus.setEnabled(false);
        adults_minus.setOnClickListener(decrease_adult);
        adults_add = (Button) findViewById(R.id.adult_plus_button);
        adults_add.setOnClickListener(increase_adult);

        //CHILDREN
        children_textview = (TextView) findViewById(R.id.children_textview);
        children_minus = (Button) findViewById(R.id.children_minus_button);
        children_minus.setEnabled(false);
        children_minus.setOnClickListener(decrease_children);
        children_add = (Button) findViewById(R.id.children_plus_button);
        children_add.setOnClickListener(increase_children);

        //BUTTON
        ok_button = (Button) findViewById(R.id.ok_button);
        ok_button.setOnClickListener(first_page);
    }
}

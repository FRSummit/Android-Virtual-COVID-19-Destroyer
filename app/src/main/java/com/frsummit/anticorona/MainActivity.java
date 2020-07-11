package com.frsummit.anticorona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;

import org.xmlpull.v1.XmlPullParser;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private Intent intent_fromAbout;
    private boolean isChecked = true;
//    private Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*intent_fromAbout = getIntent();
        if(intent_fromAbout.getExtras() != null) {
            if(intent_fromAbout.getExtras().get("activity").equals("AboutThisApp") || intent_fromAbout.getExtras().get("activity").equals("Index")) {
                loadFragment(new Sanitizer());
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        } else {
            loadFragment(new WelcomeFragment());
            changeFragment();
        }*/
//        sw = (Switch) findViewById(R.id.sound_switch);

        loadFragment(new WelcomeFragment());
        changeFragment();
    }

    private void changeFragment() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                loadFragment(new Sanitizer());
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                finishscreen();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1500);
    }

//    private void finishscreen() {
//        this.finish();
//    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }

    public void fragmentClick(View view) {
        loadFragment(new Sanitizer());
    }

    public void privacyBtnClick(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_privacy, null);
        showPopup(popupView);
    }

    public void aboutBtnClick(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.about_popup, null);
        showPopup(popupView);
    }

    public void ratingBtnClick(View view) {
        Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=$FRSummit$&hl=en");
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void settingsBtnClick(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup, null);
        showPopup(popupView);
    }

    public void soundOn() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.gunfire_);
        mp.start();
    }

    /*public void switchClick() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
//                RelativeLayout layout = (RelativeLayout)findViewById(R.id.popup_layout);
////                sw.setTextOff("OFF");
////                sw.setTextOn("ON");
//                sw.setChecked(true);
//                layout.addView(sw);

                sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            System.out.println("True");
                        } else {
                            System.out.println("False");
                        }
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1500);
    }*/

    public void showPopup(View view) {

        // inflate the layout of the popup window
//        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        final View popupView = inflater.inflate(R.layout.popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(view, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}


/**
 * Virtual Covid 19 Destroyer
 **/
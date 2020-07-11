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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.xmlpull.v1.XmlPullParser;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private Intent intent_fromAbout;
    private boolean isChecked = true;
    AdView adView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new WelcomeFragment());
        changeFragment();

        adView = findViewById(R.id.adView);
        MobileAds.initialize(this, "ca-app-pub-4861848901455235~7992147502"); //Test App Id
//        MobileAds.initialize(this, "ca-app-pub-4861848901455235~1288783011");
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4861848901455235/5674109958"); // Test Interstitial Video
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/8691691433");
        AdRequest request = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(request);

        adEventForBannerAd();
        adEventForInterstitialAd();
    }

    private void adEventForBannerAd() {
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(getApplicationContext(), "you get loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "you are failed to load" + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(getApplicationContext(), "you opened ad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked() {
                Toast.makeText(getApplicationContext(), "you have clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "you left application", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "your ad is closed", Toast.LENGTH_SHORT).show();
//                adView.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    private void adEventForInterstitialAd() {
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(getApplicationContext(), "you are loaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "you are failed to load", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(getApplicationContext(), "you opened ad", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdClicked() {
                Toast.makeText(getApplicationContext(), "you have clicked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "you left application", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "your ad is closed", Toast.LENGTH_LONG).show();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    private void changeFragment() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                loadFragment(new Sanitizer());
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1500);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public void fragmentClick(View view) {
        loadFragment(new Sanitizer());

        if(mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    public void privacyBtnClick(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_privacy, null);
        showPopup(popupView);

        if(mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    public void aboutBtnClick(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.about_popup, null);
        showPopup(popupView);

        if(mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    public void ratingBtnClick(View view) {
        if(mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

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
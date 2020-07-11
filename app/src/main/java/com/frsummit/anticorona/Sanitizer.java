package com.frsummit.anticorona;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Timer;
import java.util.TimerTask;

public class Sanitizer extends Fragment {
    private View view;
    private ImageView imageView;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_sanitizer, container, false);

        imageView = view.findViewById(R.id.imageView);
//        imageView.post(new Runnable() {
//            @Override
//            public void run() {
//                ((AnimationDrawable) imageView.getBackground()).start();
//                stopAnimation();
//            }
//        });

        button = view.findViewById(R.id.my_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        ((AnimationDrawable) imageView.getBackground()).start();
                        stopAnimation();
                    }
                });
            }
        });

        return view;
    }

    public void stopAnimation() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ((AnimationDrawable) imageView.getBackground()).stop();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1400);
    }
}

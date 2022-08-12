package com.example.clickertwo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.example.clickertwo.GameScreen.GameHard;
import com.example.clickertwo.Screen.FirstScreen;
import com.example.clickertwo.Screen.FragmentScreen.MainFragment;
import com.example.clickertwo.Screen.GameScreen;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class LifeService extends Service {

    TextView time;
    public LifeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        int countLife, lifeTotal;
        Context mContext = this;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        new CountDownTimer(200000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                int time = 10;
                editor.putInt("time", time);
                editor.commit();
                time--;
            }

            @Override
            public void onFinish() {
                int life = sharedPreferences.getInt("life", 7) + 1;
                editor.putInt("life", life);
                editor.commit();
            }
        }.start();
    }
}
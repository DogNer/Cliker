package com.example.clickertwo.Screen.FragmentScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Dimension;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.viewpager.widget.ViewPager;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clickertwo.CardView.AdapterCard;
import com.example.clickertwo.CardView.MeModelCard;
import com.example.clickertwo.LifeService;
import com.example.clickertwo.R;
import com.example.clickertwo.Screen.GameScreen;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainFragment extends Fragment {
    private static final long START_TIME_IN_MILLS = 1200000;

    AppCompatButton btnPlay;
    LinearLayout textLeftTime;

    ArrayList<MeModelCard> modelArrayList;
    AdapterCard myAdapter;
    ViewPager viewPager;

    TextView count_point, countLife, timerr;

    private int mButtonPressed = 0;

    private TextView mCounterTextView;
    private TextView mTimeTextView;

    public long mTimeLeftInMills = START_TIME_IN_MILLS;
    public long mEndTime;
    private boolean mTimeRunning = false;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;



    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong("millisLeft", mTimeLeftInMills);
        editor.putLong("endTime", mEndTime);

        editor.apply();
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int life = sharedPreferences.getInt("life", 7);

        mTimeLeftInMills = sharedPreferences.getLong("millisLeft", START_TIME_IN_MILLS);
        updateCountDownText();

        mEndTime = sharedPreferences.getLong("endTime", 0);
        mTimeLeftInMills = mEndTime - System.currentTimeMillis();

        if(mTimeLeftInMills < 0){
            mTimeLeftInMills = 0;
            updateCountDownText();
            if (life < 7 && !mTimeRunning){
                resetTimer();
            }
        }
        else {
            mTimeRunning = false;
            startTimer();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        loadCard();

        count_point = view.findViewById(R.id.countPoint);
        countLife = view.findViewById(R.id.count_life);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int score = sharedPreferences.getInt("score", 0);
        int life = sharedPreferences.getInt("life", 7);

        timerr = view.findViewById(R.id.timer_life);

        /*if (life < 7){
            mTimeLeftInMills = sharedPreferences.getLong("millisLeft", START_TIME_IN_MILLS) + START_TIME_IN_MILLS;
        }*/

        countLife.setText(String.valueOf(life));
        count_point.setText(String.valueOf(score));


        textLeftTime = view.findViewById(R.id.text_left_time);
        if (life == 7){
            textLeftTime.setVisibility(View.GONE);
        }

        return view;
    }

    private void startTimer(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        mEndTime = System.currentTimeMillis() + mTimeLeftInMills;

        new CountDownTimer(mTimeLeftInMills, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMills = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                int life = sharedPreferences.getInt("life", 7) + 1;
                editor.putInt("life", life);
                editor.commit();
                countLife.setText(String.valueOf(life));

                if (life < 7){
                    resetTimer();
                }
                else {
                    textLeftTime.setVisibility(View.GONE);
                }
            }
        }.start();
    }

    private void updateCountDownText() {
        int minuts = (int) (mTimeLeftInMills / 1000) / 60;
        int sec = (int) (mTimeLeftInMills / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minuts, sec);

        timerr.setText(timeLeftFormatted);
    }

    private void resetTimer(){
        mTimeLeftInMills = START_TIME_IN_MILLS;
        updateCountDownText();
        startTimer();
    }

    private void loadCard() {
        modelArrayList = new ArrayList<>();

        modelArrayList.add(new MeModelCard(
                "Get 5\n" + "points", "Up to 50 clicks\n" +
                "10 second", true, "Easy"
        ));

        modelArrayList.add(new MeModelCard(
                "Get 10\n" + "points", "Up to 60 clicks\n" +
                "20 second", false, "Norm"
        ));

        modelArrayList.add(new MeModelCard(
                "Get 15\n" + "points", "Up to 100 clicks\n" +
                "25 second", false, "Difficult"
        ));

        DisplayMetrics dm = getResources().getDisplayMetrics();
        float fwidth = dm.density * dm.widthPixels;
        float fheight = dm.density * dm.heightPixels;

        myAdapter = new AdapterCard(getActivity(), modelArrayList);
        viewPager.setAdapter(myAdapter);
        viewPager.setPadding(0, 50, 0, 0);
    }
}
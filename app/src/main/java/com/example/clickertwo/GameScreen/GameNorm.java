package com.example.clickertwo.GameScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clickertwo.R;
import com.example.clickertwo.Screen.FirstScreen;
import com.example.clickertwo.Screen.GameScreen;

import java.util.Random;

public class GameNorm extends AppCompatActivity {

    TextView timer, countClick, clickNeed;
    int count_clicks = 0, needcl = 0;

    AppCompatButton btn_click;

    TextView needsClick, yourClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_norm);

        timer = findViewById(R.id.timer);

        countClick = findViewById(R.id.yourClicks);
        btn_click = findViewById(R.id.btn_click);
        clickNeed = findViewById(R.id.needClicks);


        final int random = new Random().nextInt(40) + 20;
        clickNeed.setText(String.valueOf(random));

        ProgressDialog pd = new ProgressDialog(GameNorm.this);
        pd.setMessage("Please wait...");
        pd.show();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(GameNorm.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        new CountDownTimer(23000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 <= 20){
                    timer.setText(millisUntilFinished / 1000 + " sec");
                    btn_click.setEnabled(true);
                    pd.dismiss();
                }
                else {
                    btn_click.setEnabled(false);
                }
            }

            @Override
            public void onFinish() {
                timer.setText("Time finished");
                countClick.setVisibility(View.VISIBLE);
                btn_click.setEnabled(false);

                if (random == count_clicks){
                    //Toast.makeText(GameNorm.this, "You win", Toast.LENGTH_SHORT).show();
                    showDialogWin();

                    int age = sharedPreferences.getInt("score", 0) + 5;
                    editor.putInt("score", age);
                    editor.commit();
                }
                else {
                    showDialogLose(random, count_clicks);

                    int life = sharedPreferences.getInt("life", 7) - 1;
                    editor.putInt("life", life);
                    editor.commit();
                }
            }
        }.start();


        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count_clicks++;
                String s = String.valueOf(count_clicks);
                countClick.setText(s);
                if (count_clicks > 6) countClick.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showDialogWin() {
        final Dialog dialog = new Dialog(GameNorm.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_win);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(false);

        CardView btnHome = dialog.findViewById(R.id.btn_menu);
        CardView btnRestart = dialog.findViewById(R.id.btn_restart);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(GameNorm.this, FirstScreen.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e){

                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDialogLose(int needcl, int yourcl) {
        final Dialog dialog = new Dialog(GameNorm.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_lose);


        needsClick = dialog.findViewById(R.id.need_click);
        yourClick = dialog.findViewById(R.id.your_click);

        needsClick.setText(String.valueOf(needcl));
        yourClick.setText(String.valueOf(yourcl));

        CardView btnHome = dialog.findViewById(R.id.btn_menu);
        CardView btnRestart = dialog.findViewById(R.id.btn_restart);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(GameNorm.this, FirstScreen.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e){

                }
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }
}
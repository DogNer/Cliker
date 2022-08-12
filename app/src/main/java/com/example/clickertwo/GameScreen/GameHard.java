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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class GameHard extends AppCompatActivity {

    TextView timer, countClick, clickNeed;
    int count_clicks = 0, needcl = 0;

    AppCompatButton btn_click;

    TextView needsClick, yourClick;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_hard);


        timer = findViewById(R.id.timer);

        countClick = findViewById(R.id.yourClicks);
        btn_click = findViewById(R.id.btn_click);
        clickNeed = findViewById(R.id.needClicks);

        FirebaseAuth firebaseAuth;

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final int random = new Random().nextInt(60) + 40;
        clickNeed.setText(String.valueOf(random));

        ProgressDialog pd = new ProgressDialog(GameHard.this);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(GameHard.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        new CountDownTimer(28000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 <= 25){
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
                    //Toast.makeText(GameHard.this, "You win", Toast.LENGTH_SHORT).show();
                    showDialogWin();

                    int score = sharedPreferences.getInt("score", 0) + 15;
                    editor.putInt("score", score);
                    editor.commit();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("score", String.valueOf(score));

                    reference.updateChildren(hashMap);
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
        final Dialog dialog = new Dialog(GameHard.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_win);
        dialog.setCancelable(false);
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
                    Intent intent = new Intent(GameHard.this, FirstScreen.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e){

                }
                dialog.dismiss();
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
                catch (Exception e){

                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDialogLose(int needcl, int yourcl) {
        final Dialog dialog = new Dialog(GameHard.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_lose);

        dialog.setCancelable(false);
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
                    Intent intent = new Intent(GameHard.this, FirstScreen.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e){

                }
                dialog.dismiss();
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
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
package com.example.clickertwo.Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clickertwo.R;

import java.util.Random;

public class GameScreen extends AppCompatActivity {

    TextView timer, countClick, clickNeed;
    int count_clicks = 0;

    AppCompatButton btn_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        timer = findViewById(R.id.timer);

        countClick = findViewById(R.id.click_count);
        btn_click = findViewById(R.id.btn_click);
        clickNeed = findViewById(R.id.count_need);


        final int random = new Random().nextInt(40);
        clickNeed.setText(String.valueOf(random));

        ProgressDialog pd = new ProgressDialog(GameScreen.this);
        pd.setMessage("Please wait...");
        pd.show();

        new CountDownTimer(13000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 <= 10){
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
                    Toast.makeText(GameScreen.this, "You win", Toast.LENGTH_SHORT).show();
                }
            }
        }.start();


        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count_clicks++;
                String s = String.valueOf(count_clicks);
                countClick.setText(s);
                //if (count_clicks > 6) countClick.setVisibility(View.GONE);
            }
        });
    }
}
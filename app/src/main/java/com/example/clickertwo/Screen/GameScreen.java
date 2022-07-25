package com.example.clickertwo.Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.clickertwo.R;

public class GameScreen extends AppCompatActivity {

    TextView timer, countClick;
    int count_clicks = 0;

    AppCompatButton btn_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        timer = findViewById(R.id.timer);

        countClick = findViewById(R.id.click_count);
        btn_click = findViewById(R.id.btn_click);

        new CountDownTimer(23000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 <= 20){
                    timer.setText(millisUntilFinished / 1000 + " sec");
                }
            }

            @Override
            public void onFinish() {
                timer.setText("Time finished");
                countClick.setVisibility(View.VISIBLE);
                btn_click.setEnabled(false);
            }
        }.start();


        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count_clicks++;
                String s = String.valueOf(count_clicks);
                countClick.setText(s);
                if (count_clicks > 6) countClick.setVisibility(View.GONE);
            }
        });
    }
}
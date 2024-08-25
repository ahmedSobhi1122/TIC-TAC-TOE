package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class ScoreActivity extends BaseActivity {
    TextView tvPlayer1, tvPlayer2, tvNamePlayer1, tvNamePlayer2, tvWinner;
    Button btnExit;
    ImageButton settingsBtn, modeBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadSound();
        loadMode();
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        tvPlayer1 = findViewById(R.id.tv_score_player1);
        tvPlayer2 = findViewById(R.id.tv_score_player2);
        tvNamePlayer1 = findViewById(R.id.tv_score_name1);
        tvNamePlayer2 = findViewById(R.id.tv_score_name2);
        tvWinner = findViewById(R.id.tv_winner);
        btnExit = findViewById(R.id.btn_exit);
        settingsBtn = findViewById(R.id.settings);
        modeBtn = findViewById(R.id.mode);

        tvNamePlayer1.setText(loadDataString(KEY_PLAYER1_NAME));
        tvNamePlayer2.setText(loadDataString(KEY_PLAYER2_NAME));
        tvPlayer1.setText(String.valueOf(loadDataInt(KEY_PLAYER1_SCORE)));
        tvPlayer2.setText(String.valueOf(loadDataInt(KEY_PLAYER2_SCORE)));

        if (loadDataInt(KEY_PLAYER1_SCORE) > loadDataInt(KEY_PLAYER2_SCORE)) {
            tvWinner.setText(loadDataString(KEY_PLAYER1_NAME) + " " + getString(R.string.is_win));
            tvWinner.setVisibility(TextView.VISIBLE);
        } else if (loadDataInt(KEY_PLAYER1_SCORE) < loadDataInt(KEY_PLAYER2_SCORE)) {
            tvWinner.setText(loadDataString(KEY_PLAYER2_NAME) + " " + getString(R.string.is_win));
            tvWinner.setVisibility(TextView.VISIBLE);
        } else {
            tvWinner.setText(R.string.it_s_a_draw);
            tvWinner.setVisibility(TextView.VISIBLE);
        }

        settingsBtn.setOnClickListener(v -> goTo(getApplication(), SettingActivity.class));
        modeBtn.setOnClickListener(v -> toggleNightMode());

        btnExit.setOnClickListener(v -> {
            finishAffinity(); // Close all activities
            saveDataInt(KEY_PLAYER1_SCORE, 0);
            saveDataInt(KEY_PLAYER2_SCORE, 0);
            goTo(ScoreActivity.this, MainActivity.class);
        });
    }
}
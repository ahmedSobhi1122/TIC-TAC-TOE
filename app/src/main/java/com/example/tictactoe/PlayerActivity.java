package com.example.tictactoe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PlayerActivity extends BaseActivity {
    EditText etPlayer1, etPlayer2;
    Button nextBtn;
    ImageButton settingsBtn, modeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadSound();
        loadMode();
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        etPlayer1 = findViewById(R.id.player_et_name1);
        etPlayer2 = findViewById(R.id.player_et_name2);
        nextBtn = findViewById(R.id.player_next);
        settingsBtn = findViewById(R.id.settings);
        modeBtn = findViewById(R.id.mode);

        settingsBtn.setOnClickListener(v -> goTo(getApplication(),SettingActivity.class));
        modeBtn.setOnClickListener(v -> toggleNightMode());

        nextBtn.setOnClickListener(v -> {
            saveDataInt(KEY_PLAYER1_SCORE, 0);
            saveDataInt(KEY_PLAYER2_SCORE, 0);
            String player1 = etPlayer1.getText().toString(), player2 = etPlayer2.getText().toString();
            if(!player1.isEmpty() && !player2.isEmpty()){
                //save data
                saveDataString(KEY_PLAYER1_NAME, player1);
                saveDataString(KEY_PLAYER2_NAME, player2);
                //go to game
                goTo(PlayerActivity.this, GameHumanActivity.class);
            }
        });
    }
}
package com.example.tictactoe;

import android.os.Bundle;
import android.widget.ImageButton;

public class LevelActivity extends BaseActivity {
    ImageButton easy,mid,hard;
    ImageButton settingsBtn, modeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadSound();
        loadMode();
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        easy = findViewById(R.id.easy_btn);
        mid = findViewById(R.id.mid_btn);
        hard = findViewById(R.id.hard_btn);
        settingsBtn = findViewById(R.id.settings);
        modeBtn = findViewById(R.id.mode);

        settingsBtn.setOnClickListener(v -> goTo(getApplication(),SettingActivity.class));
        modeBtn.setOnClickListener(v -> toggleNightMode());

        easy.setOnClickListener(view -> {
            saveDataString(KEY_BOT_LEVEL,"Easy");
            goTo(LevelActivity.this,BotActivity.class);
        });
        mid.setOnClickListener(view -> {
            saveDataString(KEY_BOT_LEVEL,"Mid");
            goTo(LevelActivity.this,BotActivity.class);
        });
        hard.setOnClickListener(view -> {
            saveDataString(KEY_BOT_LEVEL,"Hard");
            goTo(LevelActivity.this,BotActivity.class);
        });
    }
}
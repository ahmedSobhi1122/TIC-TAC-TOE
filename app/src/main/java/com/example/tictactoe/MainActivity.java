package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends BaseActivity {
    ImageButton botBtn, playerBtn, settingsBtn, modeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadSound();
        loadMode();
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botBtn = findViewById(R.id.bot_btn);
        playerBtn = findViewById(R.id.player_btn);
        settingsBtn = findViewById(R.id.settings);
        modeBtn = findViewById(R.id.mode);

        botBtn.setOnClickListener(v -> goTo(MainActivity.this,LevelActivity.class));
        playerBtn.setOnClickListener(v -> goTo(MainActivity.this,PlayerActivity.class));
        settingsBtn.setOnClickListener(v -> goTo(getApplication(),SettingActivity.class));
        modeBtn.setOnClickListener(v ->toggleNightMode());
    }
}
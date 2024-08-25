package com.example.tictactoe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class BotActivity extends BaseActivity {
    Button next;
    EditText etName;
    ImageButton settingsBtn, modeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadSound();
        loadMode();
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot);
        next = findViewById(R.id.bot_next);
        etName = findViewById(R.id.bot_et_name);
        settingsBtn = findViewById(R.id.settings);
        modeBtn = findViewById(R.id.mode);

        next.setOnClickListener(view -> {
            saveDataInt(KEY_PLAYER1_SCORE, 0);
            saveDataInt(KEY_PLAYER2_SCORE, 0);
            String name = etName.getText().toString();
            if(!name.isEmpty()){
                saveDataString(KEY_PLAYER1_NAME, name);
                goTo(BotActivity.this, GameBotActivity.class);
            }
        });
        settingsBtn.setOnClickListener(v -> goTo(getApplication(),SettingActivity.class));
        modeBtn.setOnClickListener(v -> toggleNightMode());
    }
}
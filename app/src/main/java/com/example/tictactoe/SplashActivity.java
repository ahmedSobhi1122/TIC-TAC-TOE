package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.os.Bundle;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadMode();
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        goToWithDelay(SplashActivity.this, MainActivity.class);
    }
}
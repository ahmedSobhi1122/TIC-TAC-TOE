package com.example.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import androidx.preference.PreferenceManager;

public class BaseActivity extends AppCompatActivity {
    public final String KEY_SOUND = "sound";
    public final String KEY_VOLUME = "volume";
    public final String KEY_PLAYER1_NAME = "player1";
    public final String KEY_PLAYER2_NAME = "player2";
    public final String KEY_PLAYER1_SCORE = "score1";
    public final String KEY_PLAYER2_SCORE = "score2";
    public final String KEY_BOT_LEVEL = "level";
    public final String KEY_LANGUAGE = "language";
    public final String KEY_NIGHT_MODE = "nightMode";
    public final String FILE_SETTINGS = "AppSettings";
    public final int GAME_SOUND = R.raw.game_play;
    public final int CLICK_SOUND = R.raw.click;
    public final int WIN_SOUND = R.raw.win;
    public  int X = R.drawable.x_black;
    public  int O = R.drawable.o_black;

    public void goTo(Context from, Class<?> to) {
        startActivity(new Intent(from, to));
    }

    public void goToWithDelay(Context from, Class<?> to) {
        new Handler().postDelayed(() -> {
            goTo(from, to);
            finish();
        }, 2500);
    }

    public void message(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    ///Save Data with shared preferences
    public void saveDataString(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void saveDataInt(String key, Integer value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void saveDataFloat(String key, Float value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void saveDataBoolean(String key, Boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    ///Load Data with shared preferences
    public String loadDataString(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getString(key, "");
    }

    public Integer loadDataInt(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getInt(key, 0);
    }

    public Float loadDataFloat(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getFloat(key, 0);
    }

    public Boolean loadDataBoolean(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getBoolean(key, false);
    }

    ///MODE
    public void toggleNightMode() {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        saveNightModePreference(currentNightMode != Configuration.UI_MODE_NIGHT_YES);
        recreate(); // Recreate the activity for the theme change to take effect
    }

    public void saveNightModePreference(boolean isNightMode) {
        SharedPreferences preferences = getSharedPreferences(FILE_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_NIGHT_MODE, isNightMode);
        editor.apply();
    }

    public boolean isNightMode() {
        SharedPreferences preferences = getSharedPreferences(FILE_SETTINGS, MODE_PRIVATE);
        return preferences.getBoolean(KEY_NIGHT_MODE, true);
    }

    public void loadMode() {
        boolean mode = isNightMode();
        if (mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            X = R.drawable.x_white;
            O = R.drawable.o_white;
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            X = R.drawable.x_black;
            O = R.drawable.o_black;
        }
    }

    ///LANGUAGE
    public void toggleLanguage() {
        String currentLang = getResources().getConfiguration().getLocales().get(0).toString();
        String newLang = currentLang.equals("en") ? "ar" : "en";
        setLocale(newLang);
        recreate(); // Recreate the activity to apply the new language
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        saveLocale(lang); // Save language preference
    }

    public String getLocale() {
        return Locale.getDefault().toString().toLowerCase();
    }

    public void saveLocale(String lang) {
        SharedPreferences preferences = getSharedPreferences(FILE_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_LANGUAGE, lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences preferences = getSharedPreferences(FILE_SETTINGS, MODE_PRIVATE);
        String lang = preferences.getString(KEY_LANGUAGE, "en");
        setLocale(lang);
    }

    // control on sound
    public void loadSound() {
        if (loadDataBoolean(KEY_SOUND)) {
            if(SoundService.isPlaying()){
                SoundService.resumeSound(getApplicationContext());
            }else {
                SoundService.playSound(getApplicationContext(), GAME_SOUND);
            }
            SoundService.setPlaybackSpeed(getApplicationContext(),1.0f);
//            float volume = loadDataFloat(KEY_VOLUME);
            SoundService.setVolume(getApplicationContext(), loadDataFloat(KEY_VOLUME));
        } else {
            SoundService.stopSound(getApplicationContext());
        }
    }
    public void toggleSound() {
        saveDataBoolean(KEY_SOUND,!getSound());
    }
    public boolean getSound(){
        return loadDataBoolean(KEY_SOUND);
    }

    public float getVolume(){
        return loadDataFloat(KEY_VOLUME);
    }
    public void setVolume(float volume){
        saveDataFloat(KEY_VOLUME,volume);
    }
}
package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SettingActivity extends BaseActivity {
    ImageButton soundBtn, modeBtn;
    Spinner spinner;
    Button saveBtn;
    SeekBar volumeSeekBar;
    String selectedLanguage;
    boolean selectedSound;
    float volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadMode();
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        soundBtn = findViewById(R.id.setting_sound_btn);
        modeBtn = findViewById(R.id.setting_mode_btn);
        spinner = findViewById(R.id.setting_spinner_dropdown_item);
        saveBtn = findViewById(R.id.setting_save_btn);
        volumeSeekBar = findViewById(R.id.setting_volume_seekbar);

        volume = getVolume();
        int v = (int) (volume * 4);

        if (getSound()) {
            soundBtn.setImageResource(R.drawable.volume);
            selectedSound = true;
            System.out.println(v);
            volumeSeekBar.setProgress(v);
            SoundService.setVolume(getApplicationContext(), (float) (v / 4.0));
        } else {
            volumeSeekBar.setProgress(0);
            soundBtn.setImageResource(R.drawable.mute);
            selectedSound = false;
            SoundService.setVolume(getApplicationContext(), 0);
        }

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volume = progress / 4.0f;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Set up the spinner with the data
        List<String> data = Arrays.asList("EN", "AR");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplication(),                // Context
                R.layout.spinner_dropdown_item, // Custom layout for drop-down items
                R.id.spinner_dropdown_item,    // ID of the TextView in the custom layout
                data                          // Data to display
        );
        spinner.setAdapter(adapter);
        if (getLocale().equals("en")) {
            spinner.setSelection(0);
        } else {
            spinner.setSelection(1);
        }

        // Set the onItemSelectedListener for the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected language
                selectedLanguage = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        soundBtn.setOnClickListener(View -> {
            if (Objects.equals(soundBtn.getDrawable().getConstantState(), getResources().getDrawable(R.drawable.volume).getConstantState())) {
                soundBtn.setImageResource(R.drawable.mute);
                SoundService.setVolume(getApplicationContext(), 0.0f);
                volumeSeekBar.setProgress(0);
                selectedSound = false;
            } else {
                soundBtn.setImageResource(R.drawable.volume);
                SoundService.setVolume(getApplicationContext(), (float) (v / 4.0));
                volume = getVolume();
                volumeSeekBar.setProgress(v);
                selectedSound = true;
            }
            toggleSound();
        });
        modeBtn.setOnClickListener(View -> toggleNightMode());
        saveBtn.setOnClickListener(View -> {
            // Set the language based on the selected item
            if (selectedLanguage != null) {
                setLocale(selectedLanguage.toLowerCase());
            }
            System.out.println("selected sound : " + selectedSound);
            System.out.println("volume : " + volume);
            if (selectedSound) {
                saveDataFloat(KEY_VOLUME, volume);
                SoundService.setVolume(getApplicationContext(), volume);
            }
            finish();
        });
    }

}
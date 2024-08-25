package com.example.tictactoe;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Build;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SoundService extends Service {
    private MediaPlayer mediaPlayer;
    private int currentSoundResource = -1;
    private int pausePosition = 0;
    private static boolean isPlaying = false;
    private float playbackSpeed = 1.0f; // Default speed (1x)

    private static final String ACTION_PLAY = "ACTION_PLAY";
    private static final String ACTION_PAUSE = "ACTION_PAUSE";
    private static final String ACTION_RESUME = "ACTION_RESUME";
    private static final String ACTION_STOP = "ACTION_STOP";
    private static final String ACTION_SET_SPEED = "ACTION_SET_SPEED";
    private static final String EXTRA_SOUND = "EXTRA_SOUND";
    private static final String EXTRA_VOLUME = "EXTRA_VOLUME";
    private static final String EXTRA_SPEED = "EXTRA_SPEED";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_PLAY.equals(action)) {
                int soundResource = intent.getIntExtra(EXTRA_SOUND, -1);
                playSound(soundResource);
            } else if (ACTION_PAUSE.equals(action)) {
                pauseSound();
            } else if (ACTION_RESUME.equals(action)) {
                resumeSound();
            } else if (ACTION_STOP.equals(action)) {
                stopSound();
            } else if (ACTION_SET_SPEED.equals(action) && intent.hasExtra(EXTRA_SPEED)) {
                float speed = intent.getFloatExtra(EXTRA_SPEED, 1.0f);
                setPlaybackSpeed(speed);
            } else if (intent.hasExtra(EXTRA_VOLUME)) {
                float volume = intent.getFloatExtra(EXTRA_VOLUME, 1.0f);
                setVolume(volume);
            }
        }
        return START_STICKY;
    }

    private void playSound(int soundResource) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, soundResource);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        currentSoundResource = soundResource;
        isPlaying = true;
        setPlaybackSpeed(playbackSpeed); // Apply the current playback speed
    }

    private void pauseSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            pausePosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    private void resumeSound() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(pausePosition);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
            isPlaying = true;
            setPlaybackSpeed(playbackSpeed); // Reapply playback speed
        }
    }

    private void setVolume(float volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume, volume);
        }
    }

    private void setPlaybackSpeed(float speed) {
        if (mediaPlayer != null) {
            PlaybackParams params = new PlaybackParams();
            params.setSpeed(speed);
            mediaPlayer.setPlaybackParams(params);
            playbackSpeed = speed;
        }
    }

    private void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSound();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void playSound(Context context, int soundResource) {
        Intent intent = new Intent(context, SoundService.class);
        intent.setAction(ACTION_PLAY);
        intent.putExtra(EXTRA_SOUND, soundResource);
        context.startService(intent);
    }

    public static void pauseSound(Context context) {
        Intent intent = new Intent(context, SoundService.class);
        intent.setAction(ACTION_PAUSE);
        context.startService(intent);
    }

    public static void resumeSound(Context context) {
        Intent intent = new Intent(context, SoundService.class);
        intent.setAction(ACTION_RESUME);
        context.startService(intent);
    }

    public static void stopSound(Context context) {
        Intent intent = new Intent(context, SoundService.class);
        intent.setAction(ACTION_STOP);
        context.startService(intent);
    }

    public static void setVolume(Context context, float volume) {
        Intent intent = new Intent(context, SoundService.class);
        intent.putExtra(EXTRA_VOLUME, volume);
        context.startService(intent);
    }

    public static void setPlaybackSpeed(Context context, float speed) {
        Intent intent = new Intent(context, SoundService.class);
        intent.setAction(ACTION_SET_SPEED);
        intent.putExtra(EXTRA_SPEED, speed);
        context.startService(intent);
    }
}


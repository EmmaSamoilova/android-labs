package com.example.lab4;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private int pausePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.playButton);
        Button pauseButton = findViewById(R.id.pauseButton);
        Button stopButton = findViewById(R.id.stopButton);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);

        playButton.setOnClickListener(v -> playAudio());
        pauseButton.setOnClickListener(v -> pauseAudio());
        stopButton.setOnClickListener(v -> stopAudio());
    }

    private void playAudio() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
        }

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(pausePosition);
            mediaPlayer.start();
            Toast.makeText(this, "Відтворення аудіо", Toast.LENGTH_SHORT).show();
        }
    }

    private void pauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            pausePosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            Toast.makeText(this, "Аудіо призупинено", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
            pausePosition = 0;
            Toast.makeText(this, "Аудіо зупинено", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
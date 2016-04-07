package com.romanprisyazhnuk.thinkaheadapp;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private Button buttonPlayStop;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;

    private final Handler handler = new Handler();

    public void screenSwitcherToMain(View view) {
        setContentView(R.layout.mainview);
    }
    public void screenSwitcherToSongList(View view) {
        setContentView(R.layout.songlist);
    }
    public void screenSwitcherToAboutUs(View view) {
        setContentView(R.layout.aboutusview);
    }
    public void screenSwitcherToAboutThinkAhead(View view) {setContentView(R.layout.aboutthinkaheadview);}
    public void screenSwitcherToAboutOlya(View view) {
        setContentView(R.layout.aboutolyaview);
    }
    public void screenSwitcherToAboutKate(View view) {
        setContentView(R.layout.aboutkatyaview);
    }
    public void screenSwitcherToFreakTheFreakview(View view) {

        setContentView(R.layout.freakthefreakview);
        mediaPlayer = MediaPlayer.create(this, R.raw.freakthefreak);
        initViews();
    }
    public void screenSwitcherToGiveItUp(View view) {

        setContentView(R.layout.giveitup);
        mediaPlayer = MediaPlayer.create(this, R.raw.giveitup);
        initViews();
    }
    public void screenSwitcherToSway(View view) {

        setContentView(R.layout.swayview);
        mediaPlayer = MediaPlayer.create(this, R.raw.sway);
        initViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startview);
    }
    private void initViews() {
        buttonPlayStop = (Button) findViewById(R.id.ButtonPlayStop);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seekChange(v);
                return false;
            }
        });
    }
    private void seekChange(View v){
        if(mediaPlayer.isPlaying()){
            SeekBar sb = (SeekBar)v;
            mediaPlayer.seekTo(sb.getProgress());
        }
    }
    public void playAndStop(View v){
        if (buttonPlayStop.getText() == getString(R.string.play_str)) {
            buttonPlayStop.setText(getString(R.string.pause_str));
            try{
                mediaPlayer.start();
                startPlayProgressUpdater();
            }catch (IllegalStateException e) {
                mediaPlayer.pause();
            }
        }else {
            buttonPlayStop.setText(getString(R.string.play_str));
            mediaPlayer.pause();
        }
    }
    public void startPlayProgressUpdater() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    startPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }else{
            mediaPlayer.pause();
            buttonPlayStop.setText(getString(R.string.play_str));
            seekBar.setProgress(0);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.d(this.getClass().getName(), "back button pressed");
        }
        return super.onKeyDown(keyCode, event);
    }
}

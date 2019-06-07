package com.example.timedialator.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.example.timedialator.R;
import com.example.timedialator.utils.ToolbarMgr;

public class Settings extends AppCompatActivity
{
    public ToolbarMgr toolbarMgr = new ToolbarMgr();

    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    private AudioManager audioMgr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Add toolbar and remove the title
        toolbarMgr.makeToolBar(this, R.id.toolbar02_back, true, this::finish);

        this.sharedPref = this.getSharedPreferences(getString(R.string.sharedpref_name), Context.MODE_PRIVATE);
        this.editor = sharedPref.edit();

        this.audioMgr = ( AudioManager ) getSystemService(Context.AUDIO_SERVICE);

        SeekBar soundBar = findViewById(R.id.settings_seekbar_soundlevel);
        soundBar.setMax(15);
        if (audioMgr != null)
            soundBar.setProgress(audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC));
        else
            soundBar.setProgress(sharedPref.getInt(getString(R.string.sharedpref_sound_key), 0));
        soundBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                editor.putInt(getString(R.string.sharedpref_sound_key), i);
                editor.apply();
                if (audioMgr != null)
                    audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        /*SeekBar soundBar = findViewById(R.id.settings_seekbar_musiclevel);
        soundBar.setProgress(sharedPref.getInt(getString(R.string.sharedpref_music_key), 0));
        soundBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                editor.putInt(getString(R.string.sharedpref_music_key), i);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });*/
    }
}

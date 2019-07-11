package com.example.timedodge.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.example.timedodge.R;
import com.example.timedodge.utils.ToolbarMgr;

public class Settings extends AppCompatActivity
{
    public ToolbarMgr toolbarMgr = new ToolbarMgr();

    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    private AudioManager audioMgr = null;

    private int tempLevel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Add toolbar and remove the title
        toolbarMgr.makeToolBar(this, R.id.toolbar02_back, true, this::finish);

        // Get shared preferences
        this.sharedPref = this.getSharedPreferences(getString(R.string.sharedpref_name), Context.MODE_PRIVATE);
        this.editor = sharedPref.edit();

        // Find the audiomanager
        this.audioMgr = ( AudioManager ) getSystemService(Context.AUDIO_SERVICE);

        //Find soundbar
        SeekBar soundBar = findViewById(R.id.settings_seekbar_soundlevel);
        soundBar.setMax(15);

        if (audioMgr != null)
            this.tempLevel = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        else
            this.tempLevel = sharedPref.getInt(getString(R.string.sharedpref_sound_key), 0);

        soundBar.setProgress(this.tempLevel);

        soundBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                tempLevel = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                editor.putInt(getString(R.string.sharedpref_sound_key), tempLevel);
                editor.apply();
                if (audioMgr != null)
                    audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, tempLevel, 0);
            }
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

        /*SeekBar tiltSensBar = findViewById(R.id.settings_seekbar_sensorsensitivity);
        soundBar.setMax(100);
        tiltSensBar.setProgress(sharedPref.getInt(getString(R.string.settings_text_sensorsensitivity), 0));
        tiltSensBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                editor.putInt(getString(R.string.sharedpref_sensitivity_key), i);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });*/
    }
}

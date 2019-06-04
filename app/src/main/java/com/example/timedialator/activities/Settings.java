package com.example.timedialator.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.example.timedialator.R;

public class Settings extends AppCompatActivity
{
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    // TODO: Connect to audio manager!

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.sharedPref = this.getSharedPreferences(getString(R.string.sharedpref_name), Context.MODE_PRIVATE);//this.getPreferences(Context.MODE_PRIVATE);
        this.editor = sharedPref.edit();

        SeekBar soundBar = findViewById(R.id.settings_seekbar_soundlevel);
        soundBar.setProgress(sharedPref.getInt(getString(R.string.sharedpref_sound_key), 0));
        soundBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                editor.putInt(getString(R.string.sharedpref_sound_key), i);
                editor.apply();
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

package com.example.timedialator.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.timedialator.R;
import com.example.timedialator.utils.ToolbarMgr;

public class MainMenu extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        Button btn_play = findViewById(R.id.mainmenu_btn_play);
        btn_play.setOnClickListener((view) -> {
            Intent settingsIntent = new Intent(getApplicationContext(), GameActivity.class);
            startActivity(settingsIntent);
        });
        Button btn_settings = findViewById(R.id.mainmenu_btn_settings);
        btn_settings.setOnClickListener((view) -> {
            Intent settingsIntent = new Intent(getApplicationContext(), Settings.class);
            startActivity(settingsIntent);
        });
        Button btn_quit = findViewById(R.id.mainmenu_btn_quit);
        btn_quit.setOnClickListener((view) -> {
            finish();
        });
    }
}

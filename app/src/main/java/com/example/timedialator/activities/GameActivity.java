package com.example.timedialator.activities;

import android.app.Service;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.timedialator.R;
import com.example.timedialator.game.GameCanvas;
import com.example.timedialator.game.GameView;

import static com.example.timedialator.utils.Logging.LOG_INFO_TAG;
import static com.example.timedialator.utils.Logging.LOG_WARN_TAG;

public class GameActivity extends AppCompatActivity
{
    //private GameCanvas canvas = null;
    private GameView gameView = null;

    private boolean gameOver = false;

    // SensorManagers
    private SensorManager sensorManager;
    private Sensor sensor;

    // Vibrator
    private Vibrator vibrator;

    // Media player variables
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set view
        Log.i(LOG_INFO_TAG, "Setting view!");
        setContentView(R.layout.activity_game);         // TODO: exception thrown on screen blackout. FIX THIS!.
        /*canvas = findViewById(R.id.game_gamecanvas);
        Log.i(LOG_INFO_TAG, "" + canvas);*/

        // Set screen orientation
        Log.i(LOG_INFO_TAG, "Setting screen orientation!");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Get Vibrator
        Log.i(LOG_INFO_TAG, "Tying to find vibrator!");
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator == null)
            Log.i(LOG_WARN_TAG, "Vibrator is null!");

        // Make a media play to play bloop sound
        Log.i(LOG_INFO_TAG, "Trying to get media player!");
        mediaPlayer = MediaPlayer.create(this, R.raw.boop);

        // Get accelerometer
        Log.i(LOG_INFO_TAG, "Finding acceleration sensor (accelerometer)!");
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        // If sensor manager exists, find sensor
        if (sensorManager != null)
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register the sensor listener
        Log.i(LOG_INFO_TAG, "Tying to register sensor!");
        //sensorManager.registerListener(canvas, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(gameView, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // Un-register sensor listener
        Log.i(LOG_INFO_TAG, "App paused, un-registering sensor listener");
        //sensorManager.unregisterListener(canvas);
        sensorManager.unregisterListener(gameView);
     //   gameCanvas.stopPointGiving();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Re-register sensor listener
        Log.i(LOG_INFO_TAG, "App un-paused, registering sensor listener");
        //sensorManager.registerListener(canvas, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(gameView, sensor, SensorManager.SENSOR_DELAY_FASTEST);

        // Log first drawing even after resume
        //if (canvas.isLoggingFirstDrawEvent()) canvas.setLoggingFirstDrawEvent(true);

        //canvas.setPrevTime(System.currentTimeMillis());
        //canvas.startPointGiving();
    }
}

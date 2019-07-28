package com.example.timedodge.activities;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.timedodge.R;
import com.example.timedodge.game.view.GameCanvas;
import com.example.timedodge.game.Public;

import static com.example.timedodge.utils.Logging.LOG_INFO_TAG;

public class GameActivity extends AppCompatActivity
{
    GameCanvas gameCanvas;
    // OpenGl Version --> private GameView gameView;
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
        // Set screen orientation
        Log.i(LOG_INFO_TAG, "Setting screen orientation!");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Public.screenSize.set(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        //Public.gameManager = new GameManager(this);

        super.onCreate(savedInstanceState);

        // Set view (canvas)
        Log.i(LOG_INFO_TAG, "Setting view!");
        setContentView(R.layout.activity_game);         // TODO: exception thrown on screen blackout. FIX THIS!.
        this.gameCanvas = findViewById(R.id.game_gamecanvas);

        // OpenGL Version
        /*this.gameView = new GameView(this);
        setContentView(this.gameView);*/

        // Get Vibrator
        /*Log.i(LOG_INFO_TAG, "Tying to find vibrator!");
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator == null)
            Log.i(LOG_WARN_TAG, "Vibrator is null!");

        // Make the media player play bloop sound
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
        sensorManager.registerListener(Public.gameManager, sensor, SensorManager.SENSOR_DELAY_FASTEST);

        findViewById(R.id.game_debuginfo_panel).setOnClickListener(v -> v.setActivated(false));*/
    }

    @Override
    protected void onPause()
    {
        super.onPause();


        Public.gameManager.shutdown();
        Public.spawnManager.destroy();

        // Un-register sensor listener
        /*Log.i(LOG_INFO_TAG, "App paused, un-registering sensor listener");
        sensorManager.unregisterListener(Public.gameManager);

        // Un-register sensor listener
        Log.i(LOG_INFO_TAG, "App paused, releasing media listener");
        mediaPlayer.release();*/

        //gameCanvas.stopPointGiving();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // Re-register sensor listener
        /*Log.i(LOG_INFO_TAG, "App un-paused, registering sensor listener");
        sensorManager.registerListener(Public.gameManager, sensor, SensorManager.SENSOR_DELAY_FASTEST);

        // Make a media play to play bloop sound
        Log.i(LOG_INFO_TAG, "App un-paused, trying to get media player!");
        mediaPlayer = MediaPlayer.create(this, R.raw.boop);*/

        //canvas.setPrevTime(System.currentTimeMillis());
        //canvas.startPointGiving();
    }

    @Override
    protected void onDestroy()
    {
        Public.gameManager.shutdown();
        Public.spawnManager.destroy();

        super.onDestroy();
    }
}

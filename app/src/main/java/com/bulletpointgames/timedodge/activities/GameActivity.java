package com.bulletpointgames.timedodge.activities;

import android.app.Service;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bulletpointgames.timedodge.R;
import com.bulletpointgames.timedodge.activities.fragments.GameOverFragment;
import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.systems.event.GameEvent;
import com.bulletpointgames.timedodge.game.systems.event.GameEventListener;
import com.bulletpointgames.timedodge.game.systems.event.events.PlayerDeathEvent;
import com.bulletpointgames.timedodge.game.systems.event.events.ui.GameOverUIEvent;
import com.bulletpointgames.timedodge.game.view.GameCanvas;
import com.bulletpointgames.timedodge.utils.Logging;
import com.bulletpointgames.timedodge.utils.Tools;

import static com.bulletpointgames.timedodge.utils.Logging.LOG_INFO_TAG;

public class GameActivity extends AppCompatActivity implements GameOverFragment.GameOverFragmentInteractionListener, GameEventListener
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
        Public.gameActivity = this;
        Public.screenSize.set(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        Public.screenRect.set(0, 0, (int) Public.screenSize.x, (int) Public.screenSize.y);
        Public.screenPixelDensity = getResources().getDisplayMetrics().density;
        Public.MARGIN_PIXEL = Tools.fromDPtoDevicePixels(Public.MARGIN_DP);
        Public.gameBoard = Public.screenSize.sub((float) Public.MARGIN_PIXEL);
        Public.gameBoardRect = new Rect(Public.MARGIN_PIXEL, Public.MARGIN_PIXEL, (int) Public.screenRect.right - Public.MARGIN_PIXEL, (int) Public.screenRect.bottom - Public.MARGIN_PIXEL);
        Public.gameEventHandler.registerListener(this);

        super.onCreate(savedInstanceState);

        // Set view (canvas)
        Log.i(LOG_INFO_TAG, "Setting view!");
        setContentView(R.layout.activity_game);         // TODO: exception thrown on screen blackout. FIX THIS!.
        this.gameCanvas = findViewById(R.id.game_gamecanvas);

        findViewById(R.id.game_gamecanvas_enableDebugInfo).setOnClickListener(v -> Public.DEBUG_MODE = !Public.DEBUG_MODE);
        ImageView debugScreenshot = findViewById(R.id.game_gamecanvas_debugScreenShot_view);
        findViewById(R.id.game_gamecanvas_debugScreenShot).setOnClickListener(v -> this.gameCanvas.dumpOnDebugImageView(debugScreenshot));


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
        mediaPlayer = MediaPlayer.create(this, R.raw.boop);*/

        // Get accelerometer
        Log.i(LOG_INFO_TAG, "Finding acceleration sensor (accelerometer)!");
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        // If sensor manager exists, find sensor
        if (sensorManager != null)
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register the sensor listener
        Log.i(LOG_INFO_TAG, "Tying to register sensor!");
        sensorManager.registerListener(Public.input, sensor, SensorManager.SENSOR_DELAY_FASTEST);

        //findViewById(R.id.game_debuginfo_panel).setOnClickListener(v -> v.setActivated(false));
    }

    @Override
    public void onBackPressed()
    {
        Public.gameActivity = null;
        Public.spawnManager.destroy();
        Public.gameManager.shutdown();
        Public.uiManager.destroy();

        // Un-register sensor listener
        Log.i(LOG_INFO_TAG, "App paused, un-registering sensor listener");
        sensorManager.unregisterListener(Public.input);

        // Un-register sensor listener
        /*Log.i(LOG_INFO_TAG, "App paused, releasing media listener");
        mediaPlayer.release();*/

        super.onBackPressed();
    }

    @Override
    protected void onPause()
    {
        Log.i(LOG_INFO_TAG, "APP PAUSED");

        Public.gameActivity = null;
        Public.spawnManager.pause(true);
        Public.gameManager.pause(true);

        // Un-register sensor listener
        Log.i(LOG_INFO_TAG, "App paused, un-registering sensor listener");
        sensorManager.unregisterListener(Public.input);

        // Un-register sensor listener
        /*Log.i(LOG_INFO_TAG, "App paused, releasing media listener");
        mediaPlayer.release();*/

        //gameCanvas.stopPointGiving();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        Log.i(LOG_INFO_TAG, "APP RESUMED");

        // Re-register sensor listener
        Log.i(LOG_INFO_TAG, "App un-paused, registering sensor listener");
        sensorManager.registerListener(Public.input, sensor, SensorManager.SENSOR_DELAY_FASTEST);

        // Make a media play to play bloop sound
        /*Log.i(LOG_INFO_TAG, "App un-paused, trying to get media player!");
        mediaPlayer = MediaPlayer.create(this, R.raw.boop);*/

        Public.spawnManager.pause(false);
        Public.gameManager.pause(false);
        Public.gameActivity = this;

        //canvas.setPrevTime(System.currentTimeMillis());
        //canvas.startPointGiving();
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        Public.gameActivity = null;
        Public.gameManager.shutdown();
        Public.spawnManager.destroy();
        Public.uiManager.destroy();

        super.onDestroy();
    }

    @Override
    public void onGameOverMenuButtonPressed()
    {
        Log.d(Logging.LOG_DEBUG_TAG, "MENU BUTTON PRESSED!");
        Public.gameActivity = this;
        finish();
    }

    @Override
    public void onGameOverReplayButtonPressed()
    {
        Log.d(Logging.LOG_DEBUG_TAG, "REPLAY BUTTON PRESSED!");
        super.recreate();
    }

    @Override
    public boolean isListeningFor(GameEvent event)
    {
        return (event instanceof PlayerDeathEvent);
    }

    @Override
    public void onEvent(GameEvent event)
    {
        if (event instanceof PlayerDeathEvent)
        {
            runOnUiThread(()->{
                ConstraintLayout gameoverFragment = findViewById(R.id.frag_gameover);
                gameoverFragment.setVisibility(View.VISIBLE);
            });
        }
    }
}

/*
TODO: List below!
X- Fix broken player.
X- Fix broken debris to debris collision detection or handling.
X- Remove debris wall collision.
X- Fix game thread shutdown on app exit.
X- Fix broken spawn timer for debris.
X- Add debris re-spawn on screen exit.
x- Fix no velocity on first entity spawn.
- Add points giving, pick-up-able points and close-encounter bonuses!
x- Implement RequiresComponent annotation for better control over component dependencies
- Implement ExecuteAfter annotation for better control over execution flow.
X- Implement game layer and entity tags on entities to allow for customizable sorting/filtering (XML configuration for layer name????).
- Add spawn behaviour configuration xmls to resources and implement loading of these.
- Re-enable vibration and sound for player-wall collision.

New Features:
- Free version:
    - Saving of game state on exit and pause.
        - Database?? SharePreferences?? XML?? YAML?? JSON??
        - What to save:
            - GameManager entity list.
            - Entities components.
            - Points, bonuses and multipliers so far.
            - Whether a game is on-going.
            - SpawnManager state:
                - Time until spawn.
                - Loaded spawn behaviour.
                - More??
            - Game events in GameEventHandler in order.
            - More??
    - Add Game Over fragment.
    - Tutorial on first launch.
        - Show:
            - Player controls.
            - Death conditions.
            - Player to debris interaction.
            - Debris to debris interaction
            - Effects (good vs. bad).
- Paid version:
    - "Certain amount of close-encounters" multiplier!
    - Add player ball explosion/fragmentation on death!
        - Add animation for player exploding into multiple directions and smalled balls bouncing around.
    - Add challenges:
        - Amount of close-call bonuses under certain time.
        - X nr effects at the same time.
        - More..
    - Multiple game modes:
        - Add and implement loading game mode configuration xmls.
    - Multiple maps:
        - Add and implement loading map configuration xmls.
- Effects:
    - Limit to x amount at a time????
    - non-distinguishable vs. distinguishable??
    - Types:
        - Good:
            - Free:
                T- Shield (blocks debris but not walls).
                - Smaller debris.
                - Bouncy-ness decrease for debris.
            - Paid:
                - SLOW-MO!!
                - Radar (spawn prediction, RARE).
                - Safe walls (timed effect where walls are safe to bounce off).
                - Additional helper ball:
                    - Picks up:
                        - Points
                        - Pick-up-points
                        - Bonuses
                    - Ignores:
                        - Walls (death)
                        - Effects
                    - Controlled by the player
        - Bad:
            - Free:
                - Bigger debris.
                - Bouncy-ness increase for debris
                - Increased spawn rate of debris!
                - Debris magnet (attracts nearby debris).
            - Paid:
                - FAST-MO (time speed-up)!
                - Inverse controls (RARE).
                - Fog of war (view blockade (circular) blocking for view of effects and debris).

Optimizations:
- Have collision detections use compute shaders (https://arm-software.github.io/opengl-es-sdk-for-android/compute_intro.html).

Possible features:
- Custom map editor?? Possibility to share these???
- Customizable game skin???
- Multiplayer (co-op?? challenge mode?? free for all(with or without debris)??)

 */



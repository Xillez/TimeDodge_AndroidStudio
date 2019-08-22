package com.bulletpointgames.timedodge.game;

import android.graphics.Rect;

import com.bulletpointgames.timedodge.game.systems.event.GameEventHandler;
import com.bulletpointgames.timedodge.game.systems.spawn.SpawnManager;
import com.bulletpointgames.timedodge.game.systems.thread.GameManager;
import com.bulletpointgames.timedodge.utils.Input;
import com.bulletpointgames.timedodge.utils.TimerManager;
import com.bulletpointgames.timedodge.utils.Vector;

public class Public
{
    public static boolean DEBUG_MODE = false;

    public static Vector screenSize = new Vector(0, 0);
    public static Rect screenRect = new Rect(0, 0, 0, 0);
    public static float screenPixelDensity = 0.0f;

    public static Vector gameBoard = new Vector(0, 0);
    public static Rect gameBoardRect = new Rect(0, 0, 0, 0);

    public static boolean dumpGameCanvas = false;

    public final static int MARGIN_DP = 5;
    public static int MARGIN_PIXEL = 0;

    public static GameManager gameManager = null;
    public static GameEventHandler gameEventHandler = new GameEventHandler();
    public static SpawnManager spawnManager = new SpawnManager();
    public static TimerManager timerManager = new TimerManager();
    public static Input input = new Input();

    public static final int TARGET_FPS = 60;
    public static final float MAX_FRAME_TIME_SEC = 1.0f / TARGET_FPS;
    public static final int MAX_FRAME_TIME_NANO = (int) Math.floor(1000000000.0f * MAX_FRAME_TIME_SEC);


}

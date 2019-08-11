package com.example.timedodge.game;

import com.example.timedodge.game.systems.event.GameEventHandler;
import com.example.timedodge.game.systems.spawn.SpawnManager;
import com.example.timedodge.game.systems.thread.GameManager;
import com.example.timedodge.utils.Vector;

public class Public
{
    public static final boolean DEBUG_MODE = true;

    public static Vector screenSize = new Vector(0, 0);
    public final static int MARGIN = 5;
    public static GameManager gameManager = null;
    public static GameEventHandler gameEventHandler = new GameEventHandler();
    public static SpawnManager spawnManager = new SpawnManager();

    public static final int TARGET_FPS = 60;
    public static final float MAX_FRAME_TIME_SEC = 1.0f / TARGET_FPS;
    public static final int MAX_FRAME_TIME_NANO = (int) Math.floor(1000000000.0f * MAX_FRAME_TIME_SEC);
}

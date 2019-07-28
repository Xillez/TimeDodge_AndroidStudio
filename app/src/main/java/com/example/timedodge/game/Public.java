package com.example.timedodge.game;

import com.example.timedodge.game.event.GameEventHandler;
import com.example.timedodge.game.spawn.SpawnManager;
import com.example.timedodge.utils.Vector;

import java.util.concurrent.Semaphore;

public class Public
{
    public static Vector screenSize = new Vector(0, 0);
    public final static int MARGIN = 5;
    public static GameManager gameManager = null;
    public static GameEventHandler gameEventHandler = new GameEventHandler();
    public static SpawnManager spawnManager = new SpawnManager();
}

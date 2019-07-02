package com.example.timedodge.game;

import android.graphics.PointF;

import com.example.timedodge.game.event.GameEventHandler;

public class Public
{
    public static PointF screenSize = new PointF(0, 0);
    public static GameEventHandler gameEventHandler = new GameEventHandler();
    public static GameCanvas canvas = null;
}

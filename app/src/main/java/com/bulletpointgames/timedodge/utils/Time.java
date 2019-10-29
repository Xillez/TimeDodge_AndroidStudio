package com.bulletpointgames.timedodge.utils;

import com.bulletpointgames.timedodge.game.systems.ecs.components.Physics;

import org.jetbrains.annotations.Contract;

public class Time
{
    private static long lastSysTime = 0L;
    private static long currentSysTime = 0L;
    private static float elapsed = 0.0f;

    public static Physics playerPhysics = null;

    public static void updateDeltaTime()
    {
        // DeltaTime calc
        Time.currentSysTime = System.nanoTime();
        Time.elapsed = (Time.currentSysTime - Time.lastSysTime) / 1000000000.0f;
        Time.lastSysTime = currentSysTime;
    }

    @Contract(pure = true)
    public static float getDeltaTime()
    {
        return Time.elapsed;
    }

    public static float getPlayerAffectedDeltaTime()
    {
        // If player is moving too slow, ensure time moves at 15%.
        return Math.max(Time.getDeltaTime() * playerPhysics.getVelocity().length() * 0.0025f, Time.getDeltaTime() * 0.15f);
    }
}

package com.example.timedodge.game.event.events;

import com.example.timedodge.game.event.GameEvent;

public class GameWallCollisionEvent extends GameEvent
{
    public enum WallSide{WALL_NONE, WALL_LEFT, WALL_TOP, WALL_RIGHT, WALL_BOTTOM}
    public WallSide collisionWithSide = WallSide.WALL_NONE;
}

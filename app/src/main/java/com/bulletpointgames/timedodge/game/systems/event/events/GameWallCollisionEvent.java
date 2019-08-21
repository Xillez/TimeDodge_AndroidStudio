package com.bulletpointgames.timedodge.game.systems.event.events;

import com.bulletpointgames.timedodge.game.systems.event.GameEvent;
import com.bulletpointgames.timedodge.utils.Vector;

public class GameWallCollisionEvent extends GameEvent
{
    public enum WallSide{WALL_NONE, WALL_LEFT, WALL_TOP, WALL_RIGHT, WALL_BOTTOM}
    public WallSide collisionWithSide = WallSide.WALL_NONE;
    public Vector unstuckPosition = null;
}

package com.bulletpointgames.timedodge.game.systems.event.events;

import com.bulletpointgames.timedodge.game.systems.event.GameEvent;
import com.bulletpointgames.timedodge.utils.Vector;

public class GameEntityCollisionEvent extends GameEvent
{
    public boolean doPhysics = true;
    public Vector deflectionForce = null;
    public Vector unstuckPosition = null;
}

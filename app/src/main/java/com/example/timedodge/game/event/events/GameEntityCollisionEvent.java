package com.example.timedodge.game.event.events;

import com.example.timedodge.game.ecs.Entity;
import com.example.timedodge.game.event.GameEvent;
import com.example.timedodge.utils.Vector;

public class GameEntityCollisionEvent extends GameEvent
{
    public Vector intersection = null;
    public Vector unstuckPosition = null;
}

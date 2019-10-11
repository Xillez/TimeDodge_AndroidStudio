package com.bulletpointgames.timedodge.game.systems.event.events;

import com.bulletpointgames.timedodge.game.systems.event.GameEvent;

public class PlayerDeathEvent extends GameEvent
{
    public long points = 0L;
    public long bonuses = 0L;
}

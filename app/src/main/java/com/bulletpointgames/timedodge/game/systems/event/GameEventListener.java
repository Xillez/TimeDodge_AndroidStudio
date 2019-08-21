package com.bulletpointgames.timedodge.game.systems.event;

public interface GameEventListener
{
    boolean isListeningFor(GameEvent event);
    void onEvent(GameEvent event);
}

package com.example.timedodge.game.event;

public interface GameEventListener
{
    boolean isListeningFor(GameEvent event);
    void onEvent(GameEvent event);
}

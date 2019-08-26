package com.bulletpointgames.timedodge.game.systems.event;

import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.systems.event.events.GameWallCollisionEvent;
import com.bulletpointgames.timedodge.game.systems.event.events.ui.GameOverUIEvent;
import com.bulletpointgames.timedodge.game.systems.score.ScoreManager;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GameEventHandler
{
    private ArrayList<GameEventListener> listeners = new ArrayList<>();
    private Queue<GameEvent> eventQueue = new PriorityQueue<>();

    public void registerListener(GameEventListener listener)
    {
        if(listener != null)
            this.listeners.add(listener);
    }

    public void deregisterListener(GameEventListener listener)
    {
        if(listener != null)
            this.listeners.remove(listener);
    }

    public void registerEvent(GameEvent event)
    {
        if (event != null)
            this.eventQueue.add(event);
    }

    public void handleEvents()
    {
        this.eventQueue.forEach((object)->{ if (object instanceof GameWallCollisionEvent) triggerGameOverEvent(); });

        for (GameEvent event : this.eventQueue)
        {
            // Event is not for everyone, handle and continue.
            if (event.target != null)
            {
                if (event.target.isListeningFor(event))
                    event.target.onEvent(event);
                continue;
            }

            for (GameEventListener listener : this.listeners)
            {
                if (listener.isListeningFor(event))
                    listener.onEvent(event);
            }
        }
        this.eventQueue.clear();
    }

    private void triggerGameOverEvent() {
        GameOverUIEvent goEvent = new GameOverUIEvent();
        goEvent.target = null;
        goEvent.referrer = null;
        goEvent.points = ScoreManager.GetPoints();
        goEvent.bonuses = ScoreManager.GetBonuses();
        Public.gameEventHandler.registerEvent(goEvent);
    }
}

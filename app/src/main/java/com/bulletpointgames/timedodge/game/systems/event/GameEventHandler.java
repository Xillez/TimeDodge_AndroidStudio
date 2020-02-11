package com.bulletpointgames.timedodge.game.systems.event;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class GameEventHandler
{
    private ArrayList<GameEventListener> listeners = new ArrayList<>();
    private Queue<GameEvent> eventQueue = new PriorityQueue<>();
    private Queue<GameEvent> busyQueue = new PriorityQueue<>();

    private Semaphore handlingEvents = new Semaphore(1, true);

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
            if (this.handlingEvents.availablePermits() == 1)
                this.eventQueue.add(event);
            else
                this.busyQueue.add(event);
    }

    public void handleEvents()
    {
        try {
            this.handlingEvents.acquire();

            this.handleEvents(this.eventQueue);

            // Handle events registered from other events.
            this.handleEvents(this.busyQueue);

            // Removed handled events
            this.eventQueue.clear();
            this.busyQueue.clear();

            // Done handling events
            this.handlingEvents.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }

    private void handleEvents(Queue<GameEvent> queue)
    {
        for (GameEvent event : queue)
        {
            // Event is not for everyone, handle for specified targets and continue.
            if (event.targets.size() > 0)
            {
                for (GameEventListener target : event.targets)
                {
                    if (target.isListeningFor(event))
                        target.onEvent(event);
                }
                continue;
            }

            // Event doesn't have a target, apply for everyone
            for (GameEventListener listener : this.listeners)
            {
                if (listener.isListeningFor(event))
                    listener.onEvent(event);
            }
        }
    }
}

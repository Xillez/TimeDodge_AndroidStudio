package com.example.timedodge.game.event;

import android.support.annotation.NonNull;

import com.example.timedodge.game.ecs.Component;

public abstract class GameEvent implements Comparable
{
    public enum Priority {Important, High, Medium, Low}

    public Priority priority = Priority.Medium;

    public Component referrer = null;

    // If target is null, event is for everyone!
    public GameEventListener target = null;

    @Override
    public int compareTo(@NonNull Object o) {
        if (o instanceof GameEvent)
            return ((this.priority.ordinal() <= ((GameEvent) o).priority.ordinal()) ? 0 : 1);
        return 1;
    }
}

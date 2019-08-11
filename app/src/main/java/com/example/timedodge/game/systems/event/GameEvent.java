package com.example.timedodge.game.systems.event;

import android.support.annotation.NonNull;

import com.example.timedodge.game.systems.ecs.Component;
import com.example.timedodge.game.systems.ecs.Entity;

public abstract class GameEvent implements Comparable
{
    public enum Priority {Important, High, Medium, Low}

    public Priority priority = Priority.Medium;

    public Component referrer = null;
    public Entity otherParent = null;

    // If target is null, event is for everyone!
    public GameEventListener target = null;

    @Override
    public int compareTo(@NonNull Object o) {
        if (o instanceof GameEvent)
            return ((this.priority.ordinal() <= ((GameEvent) o).priority.ordinal()) ? 0 : 1);
        return 1;
    }
}

package com.bulletpointgames.timedodge.game.systems.event;

import android.support.annotation.NonNull;

import com.bulletpointgames.timedodge.game.systems.ecs.Component;
import com.bulletpointgames.timedodge.game.systems.ecs.Entity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class GameEvent implements Comparable
{
    public enum Priority {Important, High, Medium, Low}

    public Priority priority = Priority.Medium;

    public Component referrer = null;
    public Entity otherParent = null;

    // If target is null, event is for everyone!
    public ArrayList<GameEventListener> targets = new ArrayList<>();

    @Override
    public int compareTo(@NonNull Object o) {
        if (o instanceof GameEvent)
            return ((this.priority.ordinal() <= ((GameEvent) o).priority.ordinal()) ? 0 : 1);
        return 1;
    }
}

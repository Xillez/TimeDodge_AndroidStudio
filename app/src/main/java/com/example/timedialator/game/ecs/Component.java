package com.example.timedialator.game.ecs;

import android.util.Log;

import static com.example.timedialator.utils.Logging.LOG_INFO_TAG;

public class Component
{
    protected int id = 0;
    protected Entity parent = null;

    public Component(Entity parent)
    {
        this.parent = parent;
    }

    public void create()
    {
        Log.i(LOG_INFO_TAG, "Component create");
    }

    public void update(float dt)
    {
        Log.i(LOG_INFO_TAG, "Component update");
    }

    public void destroy()
    {
        Log.i(LOG_INFO_TAG, "Component destroy");
    }

    public int getId()
    {
        return id;
    }

    public Entity getParent()
    {
        return parent;
    }
}

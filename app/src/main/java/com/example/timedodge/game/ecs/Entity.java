package com.example.timedodge.game.ecs;

import android.graphics.Canvas;
import android.hardware.SensorEvent;

import com.example.timedodge.game.ecs.components.Transform;
import com.example.timedodge.utils.Vector;

import java.util.ArrayList;

public class Entity
{
    private int nextCompId = 0;
    private ArrayList<Component> components = new ArrayList<>();

    public Entity()
    {
        //
    }

    public void create()
    {
        this.addComponent(new Transform());

        for (Component component : this.components)
        {
            component.create();
        }
    }

    public void update(float dt, SensorEvent event)
    {
        for (Component component : this.components)
        {
            component.update(dt, event);
        }
    }

    public void draw(Canvas canvas)
    {
        for (Component component : this.components)
        {
            component.draw(canvas);
        }
    }

    public void destroy()
    {
        for (Component component : this.components)
        {
            component.destroy();
        }
    }

    public ArrayList<Component> getComponents()
    {
        return this.components;
    }

    public Component getComponentById(int id)
    {
        return this.components.get(id);
    }

    public Component getComponentByType(Class clazz)
    {
        for (Component component : this.components)
            if (component.getClass().equals(clazz))
                return component;

        return null;
    }

    public void addComponent(Component component)
    {
        if (component != null) {
            component.setId(this.nextCompId++);
            component.setParent(this);
            if (!component.isCreated())
                component.create();
            this.components.add(component);
        }
    }

    public void setPosition(Vector pos)
    {
        Transform transform = (Transform) this.getComponentByType(Transform.class);
        if (transform != null)
            transform.setPosition(pos);
    }

    /**
     * Interface of method to be trigger at collision.
     */
    /*interface EntityEventListener
    {
        void triggerGameOver();
        void triggerVibration();
        void triggerSound();
    }*/
}

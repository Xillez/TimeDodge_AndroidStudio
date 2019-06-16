package com.example.timedodge.game.ecs;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.Log;

import java.util.ArrayList;

import static com.example.timedodge.utils.Logging.LOG_INFO_TAG;

public class Entity extends ShapeDrawable
{
    private int id = 0;
    private ArrayList<Component> components = new ArrayList<>();
    private PointF position = new PointF(0.0F, 0.0F);
    private PointF velocity = new PointF(0.0F, 0.0F);
    //private ArrayList<EntityEventListener> listeners = new ArrayList<>();

    public Entity()
    {

    }

    public void create(Shape shape)
    {
        Log.i(LOG_INFO_TAG, "Entity create");

        if (shape != null)
            this.setShape(shape);

        for (Component component : this.components)
        {
            component.create();
        }
    }

    public void update(float dt)
    {
        Log.i(LOG_INFO_TAG, "Entity update");

        for (Component component : this.components)
        {
            component.update(dt);
        }
    }

    public void draw(Canvas canvas)
    {
        Log.i(LOG_INFO_TAG, "Entity draw");

        for (Component component : this.components)
        {
            component.destroy();
        }
    }

    /*public void destroy()
    {
        Log.i(LOG_INFO_TAG, "Entity destroy");

        for (Component component : this.components)
        {
            component.destroy();
        }
    }*/

    public ArrayList<Component> getComponents()
    {
        return components;
    }

    public Component getComponent(int id)
    {
        return this.components.get(id);
    }

    public void addComponent(Component component)
    {
        if (component != null)
            this.components.add(component);
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

package com.bulletpointgames.timedodge.game.systems.ecs;

import android.graphics.Canvas;
import android.util.Log;

import com.bulletpointgames.timedodge.game.systems.thread.GameManager;
import com.bulletpointgames.timedodge.game.systems.ecs.components.Transform;
import com.bulletpointgames.timedodge.utils.Logging;
import com.bulletpointgames.timedodge.utils.Tools;
import com.bulletpointgames.timedodge.utils.Vector;

import java.util.ArrayList;

public final class Entity implements GameManager.GameLifecycle
{
    private int entityID = 0;
    private volatile ArrayList<Component> components = new ArrayList<>();
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<Integer> layers = new ArrayList<>();

    public Entity()
    {
        this.entityID = Tools.getNewUID();
        this.addComponent(new Transform());
    }

    @Override
    public void create()
    {
        for (Component component : this.components)
        {
            component.create();
        }
    }

    @Override
    public void update()
    {
        for (Component component : this.components)
        {
            if (!component.isCreated())
                component.create();
            component.update();
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        for (Component component : this.components)
        {
            component.draw(canvas);
        }
    }

    // OpenGL version
    /*@Override
    public void draw(int vertexBufferPosition, int colorPosition)
    {
        for (Component component : this.components)
        {
            component.draw(vertexBufferPosition, colorPosition);
        }
    }*/

    @Override
    public void destroy()
    {
        for (Component component : this.components)
        {
            component.destroy();
        }
    }

    public int getID()
    {
        return entityID;
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

        Log.e(Logging.LOG_ERR_TAG, String.format("Could not find components type \"%s\" on entity! ", clazz.getName()));
        return null;
    }

    public void addComponent(Component component)
    {
        if (component != null) {
            component.setParent(this);
            this.components.add(component);
        }
    }

    public void setPosition(Vector pos)
    {
        Transform transform = (Transform) this.getComponentByType(Transform.class);
        if (transform != null)
            transform.setPosition(pos);
    }

    public boolean hasTag(String tag)
    {
        return tags.contains(tag);
    }

    public ArrayList<String> getTags()
    {
        return this.tags;
    }

    public void addTag(String tag)
    {
        if (!this.tags.contains(tag))
            this.tags.add(tag);
    }

    public void removeTag(String tag)
    {
        this.tags.remove(tag);
    }

    public boolean inLayer(int layer)
    {
        return layers.contains(layer);
    }

    public ArrayList<Integer> getLayers()
    {
        return this.layers;
    }

    public void addLayer(int layer)
    {
        if (!this.layers.contains(layer))
            this.layers.add(layer);
    }

    public void removeLayer(int layer)
    {
        this.layers.remove(layer);
    }
}

package com.bulletpointgames.timedodge.game.systems.UI;

import android.graphics.Canvas;

import com.bulletpointgames.timedodge.game.systems.ecs.Component;

public class UICompElement extends UIElement
{
    protected Component component = null;
    protected String fieldNameToWatch = "";

    @Override
    public void create()
    {
        super.create();
    }

    @Override
    public void update()
    {
        super.update();
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    public Component getComponent()
    {
        return this.component;
    }

    public void setComponent(Component component)
    {
        this.component = component;
    }

    public String getFieldNameToWatch()
    {
        return fieldNameToWatch;
    }

    public void setFieldNameToWatch(String fieldNameToWatch)
    {
        this.fieldNameToWatch = fieldNameToWatch;
    }
}

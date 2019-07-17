package com.example.timedodge.game.ecs.components;

import android.graphics.Canvas;
import android.hardware.SensorEvent;

import com.example.timedodge.game.ecs.Component;

public class Collision extends Component
{
    // TODO: Add ignored layers integer array here!
    // TODO: Make this use compute shaders! https://arm-software.github.io/opengl-es-sdk-for-android/compute_intro.html

    public Collision()
    {
        super();
    }

    public Collision(int id)
    {
        super(id);
    }

    @Override
    public void create()
    {
        super.create();
    }

    @Override
    public void update(float dt, SensorEvent event)
    {
        super.update(dt, event);
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
}

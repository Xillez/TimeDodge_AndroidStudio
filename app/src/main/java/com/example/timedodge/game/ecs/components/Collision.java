package com.example.timedodge.game.ecs.components;

import android.graphics.Canvas;
import android.hardware.SensorEvent;

import com.example.timedodge.game.ecs.Component;
import com.example.timedodge.utils.Vector;

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
    public void update(float dt, Vector tiltValues)
    {
        super.update(dt, tiltValues);
    }

    @Override
    public void draw()
    {
        super.draw();
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }
}

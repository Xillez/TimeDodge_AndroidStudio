package com.example.timedodge.game.ecs.components;

import android.graphics.Canvas;
import android.hardware.SensorEvent;

import com.example.timedodge.game.ecs.Component;

public class PlayerController extends Component
{
    public PlayerController()
    {
        this.id = Component.NO_ID;
    }

    public PlayerController(int id)
    {
        this.id = id;
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

        super.update(dt, event);

        // Find parent physics, fail if none
        Physics parentPhysics = (Physics) this.parent.getComponentByType(Physics.class);
        if (parentPhysics == null)
            return;

        // Set acceleration
        parentPhysics.setAcceleration(event.values[1] * 100.0f, event.values[0] * 100.0f);
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
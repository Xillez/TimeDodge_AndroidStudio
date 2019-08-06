package com.example.timedodge.game.ecs.components;

import android.graphics.Canvas;
import android.hardware.SensorEvent;

import com.example.timedodge.game.ecs.Component;
import com.example.timedodge.utils.Tools;
import com.example.timedodge.utils.Vector;

public class PlayerController extends Component
{
    public PlayerController()
    {
        super();
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

        // Find parent physics, fail if none
        Physics parentPhysics = (Physics) this.parent.getComponentByType(Physics.class);
        if (parentPhysics == null)
            return;

        // Set acceleration
        parentPhysics.setAcceleration(tiltValues);
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
    }

    // OpenGL Version
    /*@Override
    public void draw(int vertexBufferPosition, int colorPosition)
    {
        super.draw(vertexBufferPosition, colorPosition);
    }*/

    @Override
    public void destroy()
    {
        super.destroy();
    }
}

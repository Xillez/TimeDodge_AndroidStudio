package com.bulletpointgames.timedodge.game.systems.ecs.components;

import android.graphics.Canvas;

import com.bulletpointgames.timedodge.game.systems.ecs.Component;
import com.bulletpointgames.timedodge.game.systems.ecs.annotations.RequiresComponent;
import com.bulletpointgames.timedodge.game.systems.ecs.annotations.Singleton;
import com.bulletpointgames.timedodge.utils.Input;

@Singleton
@RequiresComponent(component = Physics.class)
public class PlayerController extends Component
{
    private Physics parentPhysics = null;

    public PlayerController()
    {
        super();
    }

    @Override
    public void create()
    {
        super.create();

        this.parentPhysics = (Physics) this.parent.getComponentByType(Physics.class);
    }

    @Override
    public void update()
    {
        super.update();

        // Find parent physics, fail if none
        if (parentPhysics == null)
            return;

        // Set acceleration
        parentPhysics.setAcceleration(Input.getTiltValues());
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

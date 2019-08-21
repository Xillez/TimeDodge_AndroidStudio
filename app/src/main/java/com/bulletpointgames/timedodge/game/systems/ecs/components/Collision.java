package com.bulletpointgames.timedodge.game.systems.ecs.components;

import android.graphics.Canvas;

import com.bulletpointgames.timedodge.game.systems.ecs.Component;

public class Collision extends Component
{
    // TODO: Make this use compute shaders! https://arm-software.github.io/opengl-es-sdk-for-android/compute_intro.html

    public Collision()
    {
        super();
    }

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

    // OpenGl Version
    /*@Override
    public void draw(int vertexBufferPosition, int colorPosition)
    {
        super.draw(vertexBufferPosition, colorPosition);
    }*/

    public void destroy()
    {
        super.destroy();
    }
}

package com.bulletpointgames.timedodge.game.systems.ecs.components;

import android.graphics.Canvas;

import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.systems.ecs.Component;
import com.bulletpointgames.timedodge.game.systems.ecs.annotations.Singleton;
import com.bulletpointgames.timedodge.utils.Vector;

@Singleton
public class Transform extends Component
{
    private Vector position = new Vector(Public.screenRect.centerX(), Public.screenRect.centerY());
    private float rotation = 0.0f;
    private Vector scale = new Vector(1, 1);

    public Transform()
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

    //OpenGL Version
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

    public Vector getPosition() {
        return this.position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position = new Vector(x, y);
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Vector getScale() {
        return this.scale;
    }

    public void setScale(Vector scale) {
        this.scale = scale;
    }

    public void setScale(float x, float y) {
        this.scale = new Vector(x, y);
    }
}

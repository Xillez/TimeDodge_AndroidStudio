package com.example.timedodge.game.systems.ecs.components;

import android.graphics.Canvas;

import com.example.timedodge.game.Public;
import com.example.timedodge.game.systems.ecs.Component;
import com.example.timedodge.utils.Vector;

public class Transform extends Component
{
    private Vector position = new Vector(Public.screenSize.x / 2.0f, Public.screenSize.y / 2.0f);
    private float rotation = 0.0f;
    private Vector scale = new Vector(3, 3);

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
    public void update(float dt, Vector tiltValues)
    {
        super.update(dt, tiltValues);
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

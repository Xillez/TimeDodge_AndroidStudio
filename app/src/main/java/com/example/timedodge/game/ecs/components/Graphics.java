package com.example.timedodge.game.ecs.components;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.SensorEvent;
import android.util.Log;

import com.example.timedodge.game.ecs.Component;
import com.example.timedodge.game.ecs.Entity;
import com.example.timedodge.utils.Logging;
import com.example.timedodge.utils.Vector;

public class Graphics extends Component
{
    private ShapeDrawable shape = new ShapeDrawable();
    private Vector size = new Vector(10, 10);

    public Graphics()
    {
        this.id = Component.NO_ID;
        this.shape.setShape(new OvalShape());
        this.shape.getPaint().setColor(0xffffffff);
    }

    public Graphics(int id)
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
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        Transform parentTransform = (Transform) parent.getComponentByType(Transform.class);
        if (parentTransform != null)
        {
            Vector parentPos = parentTransform.getPosition();
            Vector parentScale = parentTransform.getScale();
            this.shape.setBounds(new Rect((int) (parentPos.x - (size.x * parentScale.x * 0.5f)), (int) (parentPos.y - (size.y * parentScale.y * 0.5f)), (int) (parentPos.x + (size.x * parentScale.x * 0.5f)), (int) (parentPos.y + (size.y * parentScale.y * 0.5f))));
        }

        this.shape.draw(canvas);
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    public Vector getSize() {
        return size;
    }

    public Vector getActualSize() {
        Transform parentTransform = (Transform) parent.getComponentByType(Transform.class);
        if (parentTransform != null) {
            Vector parentScale = parentTransform.getScale();
            return new Vector(size.x * parentScale.x, size.y * parentScale.y);
        }
        return size;
    }

    public Rect getBounds()
    {
        return this.shape.getBounds();
    }
}

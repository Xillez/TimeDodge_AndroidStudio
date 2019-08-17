package com.example.timedodge.game.systems.ecs.components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import com.example.timedodge.game.systems.ecs.Component;
import com.example.timedodge.utils.Tools;
import com.example.timedodge.utils.Vector;

public class Graphics extends Component
{
    // Disable for opengl drawing
    private ShapeDrawable shape = new ShapeDrawable();
    private Vector size = new Vector(Tools.fromDPtoDevicePixels(15), Tools.fromDPtoDevicePixels(15));
    public boolean isPlayer = false;

    Transform parentTransform = null;

    public Graphics()
    {
        super();
        this.shape.setShape(new OvalShape());
        this.shape.getPaint().setColor(0xffffffff);
    }

    // OpenGL Version
    /*public Graphics()
    {
        super();
    }*/
    @Override
    public void create()
    {
        super.create();

        this.parentTransform = (Transform) this.parent.getComponentByType(Transform.class);
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

        // No parent transform found, abort
        if (parentTransform != null)
        {
            Vector parentPos = parentTransform.getPosition();
            Vector parentScale = parentTransform.getScale();
            this.shape.setBounds(new Rect((int) (parentPos.x - (size.x * parentScale.x * 0.5f)), (int) (parentPos.y - (size.y * parentScale.y * 0.5f)), (int) (parentPos.x + (size.x * parentScale.x * 0.5f)), (int) (parentPos.y + (size.y * parentScale.y * 0.5f))));
        }

        if (isPlayer)
            this.shape.getPaint().setColor(0xffff0000);

        this.shape.draw(canvas);
    }

    // OpenGL Version
    /*@Override
    public void draw(int vertexBufferPosition, int colorPosition)
    {
        super.draw(vertexBufferPosition, colorPosition);

        Transform parentTransform = (Transform) parent.getComponentByType(Transform.class);
        if (parentTransform != null)
        {
            Vector parentPos = parentTransform.getPosition();
            Vector parentScale = parentTransform.getScale();
            //this.shape.setBounds(new Rect((int) (parentPos.x - (size.x * parentScale.x * 0.5f)), (int) (parentPos.y - (size.y * parentScale.y * 0.5f)), (int) (parentPos.x + (size.x * parentScale.x * 0.5f)), (int) (parentPos.y + (size.y * parentScale.y * 0.5f))));
        }

        //this.shape.draw(canvas);
    }*/

    @Override
    public void destroy()
    {
        super.destroy();
    }

    public Vector getSize() {
        return size;
    }

    public Vector getActualSize() {
        if (this.parentTransform != null) {
            Vector parentScale = parentTransform.getScale();
            return new Vector(size.x * parentScale.x, size.y * parentScale.y);
        }
        return size;
    }

    public Rect getBounds()
    {
        return new Rect();//this.shape.getBounds();
    }
}

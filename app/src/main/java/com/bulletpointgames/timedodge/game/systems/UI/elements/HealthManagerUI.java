package com.bulletpointgames.timedodge.game.systems.UI.elements;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import com.bulletpointgames.timedodge.R;
import com.bulletpointgames.timedodge.game.systems.UI.UICompElement;
import com.bulletpointgames.timedodge.game.systems.UI.UIElement;
import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.systems.ecs.components.HealthManager;
import com.bulletpointgames.timedodge.game.systems.ecs.components.Transform;
import com.bulletpointgames.timedodge.utils.Logging;
import com.bulletpointgames.timedodge.utils.Vector;
import com.bulletpointgames.timedodge.utils.XMath;

public class HealthManagerUI extends UICompElement
{
    private LayerDrawable drawable;
    private Drawable progressDrawable;

    private float progress = 0.0f;
    private float MIN = 0.0f;
    private float MAX = 100.0f;

    private Transform transform = null;

    private Vector pos = new Vector(0, 0);
    private Vector size = new Vector(110,20);

    public HealthManagerUI()
    {
        Resources res = Public.gameActivity.getResources();
        this.drawable = (LayerDrawable) ResourcesCompat.getDrawable(res, R.drawable.game_health_ui, null);
        if (this.drawable == null)
        {
            Log.d(Logging.LOG_DEBUG_TAG, "LAYER DRAWABLE NULL!");
            return;
        }

        this.progressDrawable = this.drawable.findDrawableByLayerId(R.id.game_health_ui_progress);
        if (this.progressDrawable == null)
        {
            Log.d(Logging.LOG_DEBUG_TAG, "PROGRESS DRAWABLE NULL!");
            return;
        }
    }

    @Override
    public void create()
    {
        super.create();

        this.transform = this.component.getParent().transform;
    }

    @Override
    public void update()
    {
        super.update();

        this.progress = ((HealthManager) this.component).getHealth();
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        Vector pos = this.transform.getPosition().add(new Vector(40, -40));

        // Compress health range to -1 - 0 for moving healthbar backwards
        float displacement = ((this.progress - this.MIN)/(this.MAX - this.MIN)) - 1;

        Rect lpb_bounds = new Rect((int) pos.x, (int) pos.y, (int) (pos.x + size.x), (int) (pos.y + size.y));
        Rect progressDrawable_bounds = new Rect(
                lpb_bounds.left + (int)(size.x * displacement),
                lpb_bounds.top,
                lpb_bounds.right + (int)(size.x * displacement),
                lpb_bounds.bottom
        );

        // Draw the example drawable on top of the text.
        if (this.drawable != null)
        {
            //Log.d(Logging.LOG_DEBUG_TAG, this.drawable.getBounds().toString());
            // Position the whole layered drawable
            this.drawable.setBounds(lpb_bounds);
            this.progressDrawable.setBounds(progressDrawable_bounds);
            this.drawable.draw(canvas);
        }
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    public LayerDrawable getDrawable()
    {
        return this.drawable;
    }

    public void setDrawable(LayerDrawable drawable)
    {
        this.drawable = drawable;
    }

    public float getProgress()
    {
        return progress;
    }

    public void setProgress(float progress)
    {
        this.progress = XMath.clamp(progress, this.MAX, this.MIN);
    }

    public float getMIN()
    {
        return MIN;
    }

    public void setMIN(float MIN)
    {
        this.MIN = MIN;
    }

    public float getMAX()
    {
        return MAX;
    }

    public void setMAX(float MAX)
    {
        this.MAX = MAX;
    }

    public Vector getPos()
    {
        return pos;
    }

    public void setPos(float x, float y)
    {
        this.pos.set(x, y);
    }

    public void setPos(Vector pos)
    {
        this.pos = pos;
    }

    public Vector getSize()
    {
        return size;
    }

    public void setSize(Vector size)
    {
        this.size = size;
    }

    public void setSize(float x, float y)
    {
        this.size.set(x, y);
    }
}

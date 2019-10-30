package com.bulletpointgames.timedodge.UI.elements;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;

import com.bulletpointgames.timedodge.R;
import com.bulletpointgames.timedodge.UI.UIElement;
import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.utils.Logging;
import com.bulletpointgames.timedodge.utils.Vector;
import com.bulletpointgames.timedodge.utils.XMath;

public class LayeredProgressbar extends UIElement
{
    private LayerDrawable drawable;
    private Drawable progressDrawable;

    private float progress = 0.0f;
    private float MIN = 0.0f;
    private float MAX = 100.0f;

    private Vector pos = new Vector(0,0);
    private Vector size = new Vector(110,20);

    public LayeredProgressbar()
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
    public void draw(Canvas canvas)
    {
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

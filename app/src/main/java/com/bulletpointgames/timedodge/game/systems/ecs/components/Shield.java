package com.bulletpointgames.timedodge.game.systems.ecs.components;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;

import com.bulletpointgames.timedodge.R;
import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.systems.collision.CollisionManager;
import com.bulletpointgames.timedodge.game.systems.ecs.Component;
import com.bulletpointgames.timedodge.game.systems.ecs.Entity;
import com.bulletpointgames.timedodge.game.systems.ecs.annotations.RequiresComponent;
import com.bulletpointgames.timedodge.game.systems.event.GameEvent;
import com.bulletpointgames.timedodge.game.systems.event.GameEventListener;
import com.bulletpointgames.timedodge.game.systems.event.events.GameEntityCollisionEvent;
import com.bulletpointgames.timedodge.game.systems.event.events.GameWallCollisionEvent;
import com.bulletpointgames.timedodge.utils.Logging;
import com.bulletpointgames.timedodge.utils.Vector;
import com.bulletpointgames.timedodge.utils.XMath;

import java.util.ArrayList;

@RequiresComponent(component = Transform.class)
@RequiresComponent(component = Graphics.class)
@RequiresComponent(component = Physics.class)
public class Shield extends Collider implements GameEventListener
{
    private Transform parentTransform = null;
    private Graphics parentGraphics = null;

    private Drawable shieldDrawable = null;

    public final float MAX_STRENGTH = 2.0f;
    public final float MIN_STRENGTH = 1.0f;
    private float strenght = 2.0f;

    ShapeDrawable debugDrawable = null;

    public Shield()
    {
        super();
    }

    @Override
    public void create()
    {
        super.create();

        this.parentTransform = (Transform) this.parent.getComponentByType(Transform.class);
        this.parentGraphics = (Graphics) this.parent.getComponentByType(Graphics.class);

        Resources res = Public.gameActivity.getResources();
        this.shieldDrawable = res.getDrawable(R.drawable.shield, null);

        this.debugDrawable = new ShapeDrawable(new OvalShape());
        this.debugDrawable.getPaint().setColor(0x99FF0000);

        this.backgroundCollision = false;
        this.triggerPhysics = false;

        Public.gameEventHandler.registerListener(this);
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

        Vector parentPos = this.parentTransform.getPosition();
        this.shieldDrawable.setBounds(
            new Rect(
                (int) (parentPos.x - this.getRadius()),
                (int) (parentPos.y - this.getRadius()),
                (int) (parentPos.x + this.getRadius()),
                (int) (parentPos.y + this.getRadius())
            )
        );
        this.shieldDrawable.draw(canvas);

        if (Public.DEBUG_MODE)
        {
            float detectRange = CollisionManager.DETECTION_RANGE;

            this.debugDrawable.setBounds(
                new Rect(
                    (int) (parentPos.x - (this.getRadius() + detectRange)),
                    (int) (parentPos.y - (this.getRadius() + detectRange)),
                    (int) (parentPos.x + (this.getRadius() + detectRange)),
                    (int) (parentPos.y + (this.getRadius() + detectRange))
                )
            );
            this.debugDrawable.draw(canvas);
        }
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

    @Override
    public Vector getPosition()
    {
        return this.parentTransform.getPosition();
    }

    @Override
    public float getRadius()
    {
        return this.parentGraphics.getActualSize().x * strenght;
    }

    public void takeDamage(float value)
    {
        this.strenght = XMath.clamp(this.strenght + value, this.MAX_STRENGTH, this.MIN_STRENGTH);
    }

    @Override
    public boolean isListeningFor(GameEvent event)
    {
        return (event instanceof GameEntityCollisionEvent);
    }

    @Override
    public void onEvent(GameEvent event)
    {
        Log.d(Logging.LOG_DEBUG_TAG, "SHIELD GOT EVENT");
        if (event instanceof GameEntityCollisionEvent)
        {
            this.takeDamage(-0.1f);

            if (XMath.floatEqual(this.strenght, this.MIN_STRENGTH, 0.005f))
                this.getParent().removeComponent(this);
        }
    }
}

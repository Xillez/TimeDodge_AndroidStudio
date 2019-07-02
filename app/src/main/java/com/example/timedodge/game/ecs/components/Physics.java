package com.example.timedodge.game.ecs.components;

import android.graphics.Canvas;
import android.hardware.SensorEvent;
import android.util.Log;

import com.example.timedodge.game.Public;
import com.example.timedodge.game.ecs.Component;
import com.example.timedodge.game.ecs.Entity;
import com.example.timedodge.game.event.GameEvent;
import com.example.timedodge.game.event.GameEventListener;
import com.example.timedodge.game.event.events.GameEntityCollisionEvent;
import com.example.timedodge.game.event.events.GameWallCollisionEvent;
import com.example.timedodge.utils.Logging;
import com.example.timedodge.utils.Vector;

public class Physics extends Component implements GameEventListener
{
    private Vector velocity = new Vector(0,0);
    private Vector acceleration = new Vector(0,0);

    public Physics()
    {
        //
    }

    @Override
    public void create(int id, Entity parent)
    {
        super.create(id, parent);

        Public.gameEventHandler.registerListener(this);
    }

    @Override
    public void update(float dt, SensorEvent event)
    {
        super.update(dt, event);

        // Find parent transform, fail if none
        Transform parentTransform = (Transform) this.parent.getComponentByType(Transform.class);
        if (parentTransform == null)
            return;

        // Set acceleration
        acceleration.set(event.values[2] * 100000.0f, event.values[0] * 100000.0f);

        // Update velocity
        this.velocity.addTo(this.acceleration.multi(dt));

        // Update position
        parentTransform.setPosition(parentTransform.getPosition().x + this.velocity.x * dt, parentTransform.getPosition().y + this.velocity.y * dt);
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    @Override
    public boolean isListeningFor(GameEvent event)
    {
        return (event instanceof GameEntityCollisionEvent || event instanceof GameWallCollisionEvent);
    }

    @Override
    public void onEvent(GameEvent event)
    {
        // Find parent transform, fail if none
        Transform parentTransform = (Transform) this.parent.getComponentByType(Transform.class);
        if (parentTransform == null)
            return;

        if (event instanceof GameEntityCollisionEvent)
        {
            Transform otherTransform = (Transform) event.referrer.getParent().getComponentByType(Transform.class);
            if (otherTransform != null)
                this.velocity.addTo(parentTransform.getPosition().sub(otherTransform.getPosition()).multi(0.90f));
        }
        else if (event instanceof GameWallCollisionEvent)
        {

            if (((GameWallCollisionEvent) event).collisionWithSide == GameWallCollisionEvent.WallSide.WALL_LEFT &&
                ((GameWallCollisionEvent) event).collisionWithSide == GameWallCollisionEvent.WallSide.WALL_RIGHT)
                this.velocity.multiToAxis(Vector.Axis.x, -1.0f);
            if (((GameWallCollisionEvent) event).collisionWithSide == GameWallCollisionEvent.WallSide.WALL_TOP &&
                ((GameWallCollisionEvent) event).collisionWithSide == GameWallCollisionEvent.WallSide.WALL_BOTTOM)
                this.velocity.multiToAxis(Vector.Axis.y, -1.0f);
            Log.d(Logging.LOG_DEBUG_TAG, "WallCollisionEvent! " + this.velocity.toString());
        }
    }
}

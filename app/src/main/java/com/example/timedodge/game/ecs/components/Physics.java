package com.example.timedodge.game.ecs.components;

import android.graphics.Canvas;

import com.example.timedodge.game.Public;
import com.example.timedodge.game.ecs.Component;
import com.example.timedodge.game.event.GameEvent;
import com.example.timedodge.game.event.GameEventListener;
import com.example.timedodge.game.event.events.GameEntityCollisionEvent;
import com.example.timedodge.game.event.events.GameWallCollisionEvent;
import com.example.timedodge.utils.Tools;
import com.example.timedodge.utils.Vector;

public class Physics extends Component implements GameEventListener
{
    private Vector velocity = new Vector(0,0);
    private Vector acceleration = new Vector(0,0);

    public Physics()
    {
        super();
    }

    @Override
    public void create()
    {
        super.create();

        Public.gameEventHandler.registerListener(this);
    }

    @Override
    public void update(float dt, Vector tiltValues)
    {
        super.update(dt, tiltValues);

        // Find parent transform, fail if none
        Transform parentTransform = (Transform) this.parent.getComponentByType(Transform.class);
        if (parentTransform == null)
            return;

        // Update velocity
        this.velocity.addTo(this.acceleration);

        // Update position
        parentTransform.setPosition(parentTransform.getPosition().x + this.velocity.x * dt, parentTransform.getPosition().y + this.velocity.y * dt);
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

        // Entity to entity collision
        if (event instanceof GameEntityCollisionEvent)
        {
            // Unstuck my self
            parentTransform.getPosition().set(((GameEntityCollisionEvent) event).unstuckPosition);

            // Add bounce of other entity + removal of energy (hence 0.75 instead of 1)
            Transform otherTransform = (Transform) event.otherParent.getComponentByType(Transform.class);
            if (otherTransform != null)
            {
                this.velocity.addTo(parentTransform.getPosition().sub(otherTransform.getPosition()));
                this.velocity.multiTo(0.75f);
            }

        }
        // Entity to wall collision
        else if (event instanceof GameWallCollisionEvent)
        {
            GameWallCollisionEvent wallCollisionEvent = (GameWallCollisionEvent) event;

            // Flip velocity for bounce effect + removal of energy (hence 0.75 instead of 1)
            if (wallCollisionEvent.collisionWithSide.ordinal() == GameWallCollisionEvent.WallSide.WALL_LEFT.ordinal() ||
                    wallCollisionEvent.collisionWithSide.ordinal() == GameWallCollisionEvent.WallSide.WALL_RIGHT.ordinal())
                this.velocity.multiToAxis(Vector.Axis.x, -0.75f);
            if (wallCollisionEvent.collisionWithSide.ordinal() == GameWallCollisionEvent.WallSide.WALL_TOP.ordinal() ||
                    wallCollisionEvent.collisionWithSide.ordinal() == GameWallCollisionEvent.WallSide.WALL_BOTTOM.ordinal())
                this.velocity.multiToAxis(Vector.Axis.y, -0.75f);

            // Unstuck ball
            if (wallCollisionEvent.unstuckPosition != null)
                parentTransform.getPosition().set(wallCollisionEvent.unstuckPosition);
        }
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }
    public void setAcceleration(float x, float y) {
        this.acceleration.set(x, y);
    }
}

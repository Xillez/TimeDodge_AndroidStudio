package com.example.timedodge.game.systems.ecs.components;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.timedodge.game.Public;
import com.example.timedodge.game.systems.ecs.Component;
import com.example.timedodge.game.systems.event.GameEvent;
import com.example.timedodge.game.systems.event.GameEventListener;
import com.example.timedodge.game.systems.event.events.GameEntityCollisionEvent;
import com.example.timedodge.game.systems.event.events.GameWallCollisionEvent;
import com.example.timedodge.utils.Time;
import com.example.timedodge.utils.Vector;

public class Physics extends Component implements GameEventListener
{
    private Vector velocity = new Vector(0,0);
    private Vector acceleration = new Vector(0,0);

    private Transform parentTransform = null;

    public Physics()
    {
        super();
    }

    @Override
    public void create()
    {
        super.create();

        this.parentTransform = (Transform) this.parent.getComponentByType(Transform.class);

        Public.gameEventHandler.registerListener(this);
    }

    @Override
    public void update()
    {
        super.update();

        // No parent transform found, abort
        if (this.parentTransform == null)
            return;

        // Update velocity
        this.velocity.addTo(this.acceleration);

        // Update position
        this.parentTransform.getPosition().addTo(this.velocity.multi(Time.getDeltaTime()));
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        if (Public.DEBUG_MODE)
        {
            // No parent transform found, abort
            if (this.parentTransform == null)
                return;

            Vector pos = this.parentTransform.getPosition();
            Paint debugVelocityPaint = new Paint();
            debugVelocityPaint.setColor(0xFFF5D040);
            canvas.drawLine(pos.x, pos.y, pos.x + this.velocity.x, pos.y + this.velocity.y, debugVelocityPaint);

            Paint debugAccPaint = new Paint();
            debugAccPaint.setColor(0xFF0010BA);
            canvas.drawLine(pos.x, pos.y, pos.x + (this.acceleration.x * 100.0f), pos.y + (this.acceleration.y * 100.0f), debugAccPaint);
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
    public boolean isListeningFor(GameEvent event)
    {
        return (event instanceof GameEntityCollisionEvent || event instanceof GameWallCollisionEvent);
    }

    @Override
    public void onEvent(GameEvent event)
    {
        // No parent transform found, abort
        if (this.parentTransform == null)
            return;

        // Entity to entity collision
        if (event instanceof GameEntityCollisionEvent)
        {
            GameEntityCollisionEvent entityCollisionEvent = (GameEntityCollisionEvent) event;
            // Unstuck my self
           //parentTransform.getPosition().set(((GameEntityCollisionEvent) event).unstuckPosition);

            // Add bounce of other entity + removal of energy (hence 0.75 instead of 1)
            this.velocity.addTo(entityCollisionEvent.deflecionForce);
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

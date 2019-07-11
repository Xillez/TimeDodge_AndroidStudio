package com.example.timedodge.game.ecs.components;

import android.graphics.Canvas;
import android.hardware.SensorEvent;

import com.example.timedodge.game.Public;
import com.example.timedodge.game.ecs.Component;
import com.example.timedodge.game.ecs.Entity;
import com.example.timedodge.game.event.GameEventListener;
import com.example.timedodge.game.event.events.GameEntityCollisionEvent;
import com.example.timedodge.game.event.events.GameWallCollisionEvent;
import com.example.timedodge.utils.Vector;

public class CollisionCircle extends Collision
{
    private boolean backgroundCollision = true;

    public CollisionCircle()
    {
        //
    }

    @Override
    public void create(int id, Entity parent)
    {
        super.create(id, parent);
    }

    @Override
    public void update(float dt, SensorEvent event)
    {
        super.update(dt, event);

        // Fetch transform and graphics components from parent
        Transform parentTransform = ((Transform) this.parent.getComponentByType(Transform.class));
        Graphics parentGraphics = (Graphics) this.parent.getComponentByType(Graphics.class);

        // Not found, abort
        if (parentTransform == null || parentGraphics == null)
            return;

        Vector pos = parentTransform.getPosition();
        Vector size = parentGraphics.getActualSize();

        // Run through all collision components on canvas
        for (Component comp : Public.canvas.getAllComponentsOfType(CollisionCircle.class))
        {
            // If me, skip.
            if (comp == this)
                continue;

            // Get other's components
            Transform otherTransform = (Transform) comp.getParent().getComponentByType(Transform.class);
            Graphics otherGraphics = (Graphics) comp.getParent().getComponentByType(Graphics.class);
            Vector otherPos = parentTransform.getPosition();
            Vector otherSize = parentGraphics.getActualSize();

            // Distance is less than their combined radius', trigger collision if a Physics component exists.
            if (otherPos.sub(pos).length() < otherSize.x + size.x)
            {
                Physics otherPhysics = (Physics) comp.getParent().getComponentByType(Physics.class);
                if (otherPhysics != null)
                    this.triggerEntityCollisionEvent(otherPhysics, otherPos.sub(pos).div(2.0f));
            }
        }

        // Handle background wall collision
        if (backgroundCollision)
        {
            Physics parentPhysics = (Physics) this.parent.getComponentByType(Physics.class);
            if (parentPhysics == null)
                return;

            if ((pos.x - (size.x / 2.0f)) <= (0.0f + Public.MARGIN))
                this.triggerWallCollisionEvent(parentPhysics, GameWallCollisionEvent.WallSide.WALL_LEFT, new Vector((size.x / 2.0f) + Public.MARGIN + 0.01f, pos.y));
            if ((pos.y - (size.y / 2.0f)) <= (0.0f + Public.MARGIN))
                this.triggerWallCollisionEvent(parentPhysics, GameWallCollisionEvent.WallSide.WALL_TOP, new Vector(pos.x, (size.y / 2.0f) + Public.MARGIN + 0.01f));
            if ((pos.x + (size.x / 2.0f)) >= (Public.screenSize.x - Public.MARGIN))
                this.triggerWallCollisionEvent(parentPhysics, GameWallCollisionEvent.WallSide.WALL_RIGHT, new Vector(Public.screenSize.x - (size.x / 2.0f) - Public.MARGIN - 0.01f, pos.y));
            if ((pos.y + (size.y / 2.0f)) >= (Public.screenSize.y - Public.MARGIN))
                this.triggerWallCollisionEvent(parentPhysics, GameWallCollisionEvent.WallSide.WALL_BOTTOM, new Vector(pos.x, Public.screenSize.y - (size.y / 2.0f) - Public.MARGIN - 0.01f));
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        // Fetch transform and graphics components from parent
        /*Transform parentTransform = ((Transform) this.parent.getComponentByType(Transform.class));
        Graphics parentGraphics = (Graphics) this.parent.getComponentByType(Graphics.class);

        // Not found, abort
        if (parentTransform == null || parentGraphics == null)
            return;

        Vector pos = parentTransform.getPosition();
        Vector size = parentGraphics.getActualSize();

        ShapeDrawable circle = new ShapeDrawable(new RectShape());
        circle.getPaint().setColor(0xFF98FA8f);
        circle.setBounds(new Rect((int)(pos.x - (size.x / 2.0f)), (int)(pos.y - (size.y / 2.0f)), (int)(pos.x + (size.x / 2.0f)), (int)(pos.y + (size.y / 2.0f))));
        circle.draw(canvas);*/
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    public void triggerEntityCollisionEvent(GameEventListener other, Vector intersectionPoint)
    {
        GameEntityCollisionEvent collEvent = new GameEntityCollisionEvent();
        collEvent.target = other;
        collEvent.referrer = this;
        collEvent.intersection = intersectionPoint;
        Public.gameEventHandler.registerEvent(collEvent);
    }

    public void triggerWallCollisionEvent(GameEventListener other, GameWallCollisionEvent.WallSide wallSide, Vector unstuckPosition)
    {
        GameWallCollisionEvent collEvent = new GameWallCollisionEvent();
        collEvent.target = other;
        collEvent.referrer = this;
        collEvent.collisionWithSide = wallSide;
        collEvent.unstuckPosition = unstuckPosition;
        Public.gameEventHandler.registerEvent(collEvent);
    }
}

package com.bulletpointgames.timedodge.game.systems.ecs.components;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;

import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.systems.ecs.Component;
import com.bulletpointgames.timedodge.game.systems.ecs.Entity;
import com.bulletpointgames.timedodge.game.systems.event.GameEventListener;
import com.bulletpointgames.timedodge.game.systems.event.events.GameEntityCollisionEvent;
import com.bulletpointgames.timedodge.game.systems.event.events.GameWallCollisionEvent;
import com.bulletpointgames.timedodge.game.systems.event.events.ui.GameOverUIEvent;
import com.bulletpointgames.timedodge.game.systems.score.ScoreManager;
import com.bulletpointgames.timedodge.utils.Logging;
import com.bulletpointgames.timedodge.utils.Tools;
import com.bulletpointgames.timedodge.utils.Vector;

import java.util.ArrayList;

public class CollisionCircle extends Collision
{
    private boolean backgroundCollision = true;
    private float DETECTION_RANGE = 75.0f;

    private Transform parentTransform = null;
    private Graphics parentGraphics = null;
    private Physics parentPhysics = null;

    public CollisionCircle()
    {
        super();
    }

    @Override
    public void create()
    {
        super.create();

        // Fetch transform and graphics components from parent
        this.parentTransform = (Transform) this.parent.getComponentByType(Transform.class);
        this.parentGraphics = (Graphics) this.parent.getComponentByType(Graphics.class);
        this.parentPhysics = (Physics) this.parent.getComponentByType(Physics.class);
    }

    @Override
    public void update()
    {
        super.update();

        // No parent transform nor graphics found, abort
        if (this.parentTransform == null || this.parentGraphics == null)
            return;

        Vector pos = this.parentTransform.getPosition();
        Vector size = this.parentGraphics.getActualSize();

        ArrayList<Component> comps = Public.gameManager.getAllComponentsOfTypeNearEntity(CollisionCircle.class, this.parent, this.parentTransform.getPosition(), this.DETECTION_RANGE);
        if (comps == null) {
            Log.i(Logging.LOG_DEBUG_TAG, "Failed to fetch collision components!");
            return;
        }

        // Run through all collision components on canvas
        for (Component comp : comps)
        {
            // If me, skip.
            if (comp.getId() == this.getId())
            {
                continue;
            }

            Entity otherParent = comp.getParent();

            // Get other's components
            Transform otherTransform = (Transform) otherParent.getComponentByType(Transform.class);
            Graphics otherGraphics = (Graphics) otherParent.getComponentByType(Graphics.class);
            Vector otherPos = otherTransform.getPosition();
            Vector otherSize = otherGraphics.getActualSize();

            Vector diff = new Vector(otherPos.x - pos.x, otherPos.y - pos.y);

            // Distance is less than their combined radius', trigger collision if a Physics component exists.
            if (diff.length() < (otherSize.x + size.x) * 0.5f)
            {
                Physics otherPhysics = (Physics) otherParent.getComponentByType(Physics.class);
                if (otherPhysics != null)
                {
                    // Get direction of deflection
                    Vector deflectionForce = pos.sub(otherPos).normalize();
                    // Set the energy of the bounce collision - 25%
                    deflectionForce.multiTo(this.parentPhysics.getVelocity().add(otherPhysics.getVelocity().multi(-1.0f)).length() * 0.65f);
                    this.triggerEntityCollisionEvent(this.parentPhysics, deflectionForce);
                }
            }
        }

        // Handle background wall collision
        if (backgroundCollision)
        {
            if (this.parentPhysics == null)
                return;

            Vector unstuckPos = Tools.findClosestScreenUnstuckPosition(pos, size);

            if ((pos.x - (size.x / 2.0f)) <= Public.gameBoardRect.left)
            {
                this.triggerWallCollisionEvent(this.parentPhysics, GameWallCollisionEvent.WallSide.WALL_LEFT, unstuckPos);
                this.triggerGameOverEvent();
            }
            if ((pos.y - (size.y / 2.0f)) <= Public.gameBoardRect.top)
            {
                this.triggerWallCollisionEvent(this.parentPhysics, GameWallCollisionEvent.WallSide.WALL_TOP, unstuckPos);
                this.triggerGameOverEvent();
            }
            if ((pos.x + (size.x / 2.0f)) >= Public.gameBoardRect.right)
            {
                this.triggerWallCollisionEvent(this.parentPhysics, GameWallCollisionEvent.WallSide.WALL_RIGHT, unstuckPos);
                this.triggerGameOverEvent();
            }
            if ((pos.y + (size.y / 2.0f)) >= Public.gameBoardRect.bottom)
            {
                this.triggerWallCollisionEvent(this.parentPhysics, GameWallCollisionEvent.WallSide.WALL_BOTTOM, unstuckPos);
                this.triggerGameOverEvent();
            }
        }
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

            ShapeDrawable detectCircle = new ShapeDrawable(new OvalShape());
            detectCircle.getPaint().setColor(0x8800ff00);
            detectCircle.setBounds(new Rect((int) (pos.x - this.DETECTION_RANGE), (int)(pos.y - this.DETECTION_RANGE), (int)(pos.x + this.DETECTION_RANGE), (int)(pos.y + this.DETECTION_RANGE)));
            detectCircle.draw(canvas);
        }
    }

    // OpenGL Version
    /*@Override
    public void draw(int vertexBufferPosition, int colorPosition)
    {
        super.draw(vertexBufferPosition, colorPosition);

        // Fetch transform and graphics components from parent
        Transform parentTransform = ((Transform) this.parent.getComponentByType(Transform.class));
        Graphics parentGraphics = (Graphics) this.parent.getComponentByType(Graphics.class);

        // Not found, abort
        if (parentTransform == null || parentGraphics == null)
            return;

        Vector pos = parentTransform.getPosition();
        Vector size = parentGraphics.getActualSize();

        /*ShapeDrawable circle = new ShapeDrawable(new RectShape());
        circle.getPaint().setColor(0xFF98FA8f);
        circle.setBounds(new Rect((int) (pos.x - ( size.x / 2.0f)), (int)( pos.y - ( size.y / 2.0f)), (int)( pos.x + ( size.x / 2.0f)), (int)( pos.y + ( size.y / 2.0f))));
        circle.draw(canvas);*
    }*/

    @Override
    public void destroy()
    {
        super.destroy();
    }

    public void triggerEntityCollisionEvent(GameEventListener target, Vector deflectionForce)
    {
        GameEntityCollisionEvent collEvent = new GameEntityCollisionEvent();
        collEvent.target = target;
        collEvent.referrer = this;
        collEvent.deflecionForce = deflectionForce;
        //collEvent.intersection = intersectionPoint;
        //collEvent.unstuckPosition = unstuckPosition;
        Public.gameEventHandler.registerEvent(collEvent);
    }

    public void triggerWallCollisionEvent(GameEventListener target, GameWallCollisionEvent.WallSide wallSide, Vector unstuckPosition)
    {
        GameWallCollisionEvent collEvent = new GameWallCollisionEvent();
        collEvent.target = target;
        collEvent.referrer = this;
        collEvent.collisionWithSide = wallSide;
        collEvent.unstuckPosition = unstuckPosition;
        Public.gameEventHandler.registerEvent(collEvent);
    }

    private void triggerGameOverEvent()
    {
        GameOverUIEvent goEvent = new GameOverUIEvent();
        goEvent.target = null;
        goEvent.referrer = null;
        goEvent.points = ScoreManager.GetPoints();
        goEvent.bonuses = ScoreManager.GetBonuses();
        Public.gameEventHandler.registerEvent(goEvent);
    }

    public boolean isBackgroundCollision()
    {
        return backgroundCollision;
    }

    public void setBackgroundCollision(boolean backgroundCollision)
    {
        this.backgroundCollision = backgroundCollision;
    }
}

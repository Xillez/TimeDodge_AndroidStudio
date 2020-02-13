package com.bulletpointgames.timedodge.game.systems.ecs.components;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.systems.collision.CollisionManager;
import com.bulletpointgames.timedodge.game.systems.ecs.annotations.RequiresComponent;
import com.bulletpointgames.timedodge.utils.Vector;

//@Singleton
@RequiresComponent(component = Graphics.class)
@RequiresComponent(component = Physics.class)
public class CircleCollider extends Collider
{
    protected Graphics parentGraphics = null;
    protected Physics parentPhysics = null;

    public CircleCollider()
    {
        super();
    }

    @Override
    public void create()
    {
        super.create();

        // Fetch Graphics and Physics components from parent
        this.parentGraphics = (Graphics) this.parent.getComponentByType(Graphics.class);
        this.parentPhysics = (Physics) this.parent.getComponentByType(Physics.class);
    }

    @Override
    public void update()
    {
        super.update();

        // No parent transform nor graphics found, abort
        /*if (this.parentTransform == null || this.parentGraphics == null)
            return;

        Vector pos = this.parentTransform.getPosition();
        Vector size = this.parentGraphics.getActualSize();

        ArrayList<Component> comps = Public.gameManager.getAllComponentsOfTypeNearEntity(CircleCollider.class, this.parent, this.parentTransform.getPosition(), this.DETECTION_RANGE);
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
        }*/
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        if (Public.DEBUG_MODE)
        {
            Vector pos = this.parent.transform.getPosition();
            float detectRange = CollisionManager.DETECTION_RANGE;

            ShapeDrawable detectCircle = new ShapeDrawable(new OvalShape());
            detectCircle.getPaint().setColor(0x8800ff00);
            detectCircle.setBounds(
                    new Rect(
                            (int) (pos.x - (this.getRadius() + detectRange)),
                            (int) (pos.y - (this.getRadius() + detectRange)),
                            (int) (pos.x + (this.getRadius() + detectRange)),
                            (int) (pos.y + (this.getRadius() + detectRange))));
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

    @Override
    public Vector getPosition()
    {
        return this.parent.transform.getPosition();
    }

    @Override
    public float getRadius()
    {
        return this.parentGraphics.getActualSize().x / 2.0f;
    }
}

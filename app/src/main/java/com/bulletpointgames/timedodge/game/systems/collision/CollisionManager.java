package com.bulletpointgames.timedodge.game.systems.collision;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.systems.ecs.Component;
import com.bulletpointgames.timedodge.game.systems.ecs.components.Collider;
import com.bulletpointgames.timedodge.game.systems.ecs.components.HealthManager;
import com.bulletpointgames.timedodge.game.systems.ecs.components.Physics;
import com.bulletpointgames.timedodge.game.systems.ecs.components.Shield;
import com.bulletpointgames.timedodge.game.systems.event.GameEventListener;
import com.bulletpointgames.timedodge.game.systems.event.events.GameEntityCollisionEvent;
import com.bulletpointgames.timedodge.game.systems.event.events.GameWallCollisionEvent;
import com.bulletpointgames.timedodge.game.systems.event.events.ui.GameOverUIEvent;
import com.bulletpointgames.timedodge.game.systems.score.ScoreManager;
import com.bulletpointgames.timedodge.game.tags.Tags;
import com.bulletpointgames.timedodge.utils.Tools;
import com.bulletpointgames.timedodge.utils.Vector;

import java.util.ArrayList;

public class CollisionManager
{
    // TODO: Make this use compute shaders! https://arm-software.github.io/opengl-es-sdk-for-android/compute_intro.html

    ArrayList<Collider> colliders = new ArrayList<>();

    public static float DETECTION_RANGE = 75.0f;

    public void create()
    {

    }

    public void update()
    {
        // Run through all collision components on canvas
        for (int a = 0; a < this.colliders.size(); a++)
        {
            Vector pos = this.colliders.get(a).getPosition();
            float radius = this.colliders.get(a).getRadius();
            Physics parentPhysics = (Physics) this.colliders.get(a).getParent().getComponentByType(Physics.class);
            Shield parentShield = (Shield) this.colliders.get(a).getParent().getComponentByType(Shield.class);

            ArrayList<Component> collidersNearby = Public.gameManager.getAllComponentsOfTypeNearEntity(Collider.class, this.colliders.get(a).getParent(), pos, radius + DETECTION_RANGE);
            for (Component comp : collidersNearby)
            {
                Collider collider = (Collider) comp;

                // Get other's position and radius
                Vector otherPos = collider.getPosition();
                float otherRadius = collider.getRadius();

                Vector diff = new Vector(otherPos.x - pos.x, otherPos.y - pos.y);

                // Distance is less than their combined radius', trigger collision if a Physics component exists.
                if (diff.length() <= radius + otherRadius)
                {
                    Physics otherPhysics = (Physics) collider.getParent().getComponentByType(Physics.class);
                    if (otherPhysics != null && parentPhysics != null)
                    {
                        // Get direction of deflection
                        Vector deflectionForce = pos.sub(otherPos).normalize();
                        // Set the energy of the bounce collision to 65%
                        deflectionForce.multiTo(parentPhysics.getVelocity().add(otherPhysics.getVelocity().multi(-1.0f)).length() * 0.65f);

                        boolean colliderTriggersPhysics = this.colliders.get(a).shouldTriggerPhysics();
                        ArrayList<GameEventListener> targets = new ArrayList<>();
                        if (parentPhysics != null)
                            targets.add(parentPhysics);
                        if (parentShield != null)
                            targets.add(parentShield);
                        this.triggerEntityCollisionEvent(targets, colliderTriggersPhysics, deflectionForce, Tools.findClosestCircleColliderUnstuckPosition(pos, radius, otherPos, otherRadius));
                    }
                }
            }

            // Handle background wall collision
            if (this.colliders.get(a).isBackgroundCollisionOn())
            {
                if (parentPhysics == null)
                    return;

                Vector unstuckPos = Tools.findClosestScreenUnstuckPosition(pos, radius);

                if ((pos.x - radius) <= Public.gameBoardRect.left)
                {
                    this.triggerWallCollisionEvent(parentPhysics, GameWallCollisionEvent.WallSide.WALL_LEFT, unstuckPos);
                }
                if ((pos.y - radius) <= Public.gameBoardRect.top)
                {
                    this.triggerWallCollisionEvent(parentPhysics, GameWallCollisionEvent.WallSide.WALL_TOP, unstuckPos);
                }
                if ((pos.x + radius) >= Public.gameBoardRect.right)
                {
                    this.triggerWallCollisionEvent(parentPhysics, GameWallCollisionEvent.WallSide.WALL_RIGHT, unstuckPos);
                }
                if ((pos.y + radius) >= Public.gameBoardRect.bottom)
                {
                    this.triggerWallCollisionEvent(parentPhysics, GameWallCollisionEvent.WallSide.WALL_BOTTOM, unstuckPos);
                }
            }
        }
    }

    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(0xFFFF00FF);
        canvas.drawLine(200, 200, 300, 200, paint);
    }

    public void destroy()
    {

    }

    public void regristerCollider(Collider collider)
    {
        if (!this.colliders.contains(collider))
            this.colliders.add(collider);
    }

    public void deregristerCollider(Collider collider)
    {
        if (this.colliders.contains(collider))
            this.colliders.remove(collider);
    }

    public void triggerEntityCollisionEvent(ArrayList<GameEventListener> targets, boolean doPhysics, Vector deflectionForce, Vector unstuckPosition)
    {
        GameEntityCollisionEvent collEvent = new GameEntityCollisionEvent();
        collEvent.targets = targets;
        collEvent.referrer = null;
        collEvent.deflectionForce = deflectionForce;
        collEvent.doPhysics = doPhysics;
        //collEvent.intersection = intersectionPoint;
        collEvent.unstuckPosition = unstuckPosition;
        Public.gameEventHandler.registerEvent(collEvent);
    }

    public void triggerWallCollisionEvent(Physics target, GameWallCollisionEvent.WallSide wallSide, Vector unstuckPosition)
    {
        GameWallCollisionEvent collEvent = new GameWallCollisionEvent();
        collEvent.targets.add(target);

        if (target.getParent().hasTag(Tags.PLAYER_TAG))
        {
            collEvent.targets.add((HealthManager) target.getParent().getComponentByType(HealthManager.class));
        }

        collEvent.referrer = null;
        collEvent.collisionWithSide = wallSide;
        collEvent.unstuckPosition = unstuckPosition;
        Public.gameEventHandler.registerEvent(collEvent);
    }

    private void triggerGameOverEvent()
    {
        GameOverUIEvent goEvent = new GameOverUIEvent();
        goEvent.referrer = null;
        goEvent.points = ScoreManager.GetPoints();
        goEvent.bonuses = ScoreManager.GetBonuses();
        Public.gameEventHandler.registerEvent(goEvent);
    }
}

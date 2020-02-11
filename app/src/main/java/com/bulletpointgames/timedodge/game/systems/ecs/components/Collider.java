package com.bulletpointgames.timedodge.game.systems.ecs.components;

import android.graphics.Canvas;

import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.systems.ecs.Component;
import com.bulletpointgames.timedodge.game.systems.event.GameEventListener;
import com.bulletpointgames.timedodge.game.systems.event.events.GameEntityCollisionEvent;
import com.bulletpointgames.timedodge.game.systems.event.events.GameWallCollisionEvent;
import com.bulletpointgames.timedodge.game.systems.event.events.ui.GameOverUIEvent;
import com.bulletpointgames.timedodge.game.systems.score.ScoreManager;
import com.bulletpointgames.timedodge.game.tags.Tags;
import com.bulletpointgames.timedodge.utils.Vector;

public class Collider extends Component
{
    protected boolean backgroundCollision = true;

    protected boolean triggerPhysics = true;

    public Collider()
    {
        super();
    }

    @Override
    public void create()
    {
        super.create();

        Public.collisionManager.regristerCollider(this);
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
    }

    // OpenGl Version
    /*@Override
    public void draw(int vertexBufferPosition, int colorPosition)
    {
        super.draw(vertexBufferPosition, colorPosition);
    }*/

    public void destroy()
    {
        super.destroy();
    }

    public Vector getPosition()
    {
        return new Vector(0.0f, 0.0f);
    }

    public float getRadius()
    {
        return 0.0f;
    }

    public boolean isBackgroundCollisionOn()
    {
        return backgroundCollision;
    }

    public void setBackgroundCollision(boolean backgroundCollision)
    {
        this.backgroundCollision = backgroundCollision;
    }

    public boolean shouldTriggerPhysics()
    {
        return triggerPhysics;
    }

    public void triggerEntityCollisionEvent(GameEventListener target, Vector deflectionForce)
    {
        GameEntityCollisionEvent collEvent = new GameEntityCollisionEvent();
        collEvent.targets.add(target);
        collEvent.referrer = this;
        collEvent.deflectionForce = deflectionForce;
        //collEvent.intersection = intersectionPoint;
        //collEvent.unstuckPosition = unstuckPosition;
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

        collEvent.referrer = this;
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

package com.bulletpointgames.timedodge.game.systems.ecs.components;

import android.graphics.Canvas;

import com.bulletpointgames.timedodge.UI.elements.LayeredProgressbar;
import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.systems.ecs.Component;
import com.bulletpointgames.timedodge.game.systems.ecs.annotations.RequiresComponent;
import com.bulletpointgames.timedodge.game.systems.ecs.annotations.Singleton;
import com.bulletpointgames.timedodge.game.systems.event.GameEvent;
import com.bulletpointgames.timedodge.game.systems.event.GameEventListener;
import com.bulletpointgames.timedodge.game.systems.event.events.GameWallCollisionEvent;
import com.bulletpointgames.timedodge.game.systems.event.events.PlayerDeathEvent;
import com.bulletpointgames.timedodge.game.systems.score.ScoreManager;
import com.bulletpointgames.timedodge.utils.XMath;

@Singleton
@RequiresComponent(component = Transform.class)
@RequiresComponent(component = Physics.class)
public class HealthManager extends Component implements GameEventListener
{
    public final int MAX_HEALTH = 100;
    public final int MIN_HEALTH = 0;
    private int playerHealth = this.MAX_HEALTH;
    private Transform parentTransform = null;
    private Physics parentPhysics = null;
    private LayeredProgressbar healthBar = new LayeredProgressbar();

    public HealthManager()
    {
        super();
    }

    @Override
    public void create()
    {
        super.create();

        this.parentTransform = (Transform) this.parent.getComponentByType(Transform.class);
        this.parentPhysics = (Physics) this.parent.getComponentByType(Physics.class);

        this.healthBar.setMAX(this.MAX_HEALTH);
        this.healthBar.setMIN(this.MIN_HEALTH);
    }

    @Override
    public void update()
    {
        super.update();

        this.healthBar.setPos(this.parentTransform.getPosition().add(40.0f));
        this.healthBar.setProgress(this.playerHealth);
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        this.healthBar.draw(canvas);
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
        return (event instanceof GameWallCollisionEvent);
    }

    @Override
    public void onEvent(GameEvent event)
    {
        if (event instanceof GameWallCollisionEvent)
        {
            this.modifyHealth(-35);

            if (this.playerHealth <= 0)
            {
                PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent();
                playerDeathEvent.targets.add(Public.gameActivity);
                playerDeathEvent.points = ScoreManager.GetPoints();
                playerDeathEvent.bonuses = ScoreManager.GetBonuses();
                Public.gameEventHandler.registerEvent(playerDeathEvent);
            }
        }
    }

    public void modifyHealth(int health)
    {
        this.playerHealth = XMath.clamp(this.playerHealth + health, this.MAX_HEALTH, this.MIN_HEALTH);
    }
}

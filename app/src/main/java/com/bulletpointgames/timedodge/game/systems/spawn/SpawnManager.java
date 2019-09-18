package com.bulletpointgames.timedodge.game.systems.spawn;

import android.graphics.Canvas;

import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.layers.Layers;
import com.bulletpointgames.timedodge.game.systems.ecs.Entity;
import com.bulletpointgames.timedodge.game.systems.ecs.components.CollisionCircle;
import com.bulletpointgames.timedodge.game.systems.ecs.components.Graphics;
import com.bulletpointgames.timedodge.game.systems.ecs.components.Physics;
import com.bulletpointgames.timedodge.game.systems.ecs.components.RespawnTrigger;
import com.bulletpointgames.timedodge.game.systems.ecs.components.Transform;
import com.bulletpointgames.timedodge.game.tags.Tags;
import com.bulletpointgames.timedodge.utils.Tools;
import com.bulletpointgames.timedodge.utils.Vector;

import java.util.ArrayList;

public class SpawnManager {
    /*private class SpawnedEntityInfo {
        boolean enteredScreen = false;
        Entity entity = null;

        public SpawnedEntityInfo(Entity entity) {
            this.entity = entity;
        }
    }*/

    //private SpawnConfigLoader configLoader = null;
    private boolean shouldSpawn = true;
    public static final int MAX_ENTITY_COUNT = 11;
    ArrayList<RespawnTrigger> respawnTriggers = new ArrayList<>();
    private Transform playerTransform = null;

    public SpawnManager(/*Context context*/)
    {
        //this.configLoader = new SpawnConfigLoader(context);
    }

    public void create()
    {
        // TODO: Load spawn manager configuration xml !!NOT IMPLEMENTED, FUTURE UPDATE!!

        ArrayList<Entity> players = Public.gameManager.getAllEntitiesWithTag(Tags.PLAYER_TAG, null);
        this.playerTransform = (Transform) players.get(0).getComponentByType(Transform.class);

        Public.timerManager.registerTimer(1000, 750, () -> spawnEntity());
    }

    public void update()
    {
        Transform entityTransform = null;
        Physics entityPhysics = null;
        for (RespawnTrigger detector : this.respawnTriggers)
        {
            entityTransform = (Transform) detector.getParent().getComponentByType(Transform.class);
            entityPhysics = (Physics) detector.getParent().getComponentByType(Physics.class);

            if ((detector.hasEnteredScreen() || detector.hasTimeExpired()) && !detector.isVisible())
            {
                this.placeEntity(entityTransform, entityPhysics);
                detector.reset();
            }
        }
    }

    public void draw(Canvas canvas) {
        //
    }

    //OpenGL Version
    /*public void draw(int vertexBufferPosition, int colorPosition)
    {
        //
    }*/

    public void destroy() {
        //
    }

    public void pause(boolean pause)
    {
        this.shouldSpawn = pause;
    }

    public void spawnEntity() {
        if (!(Public.gameManager.getEntities().size() < SpawnManager.MAX_ENTITY_COUNT))
            return;

        Entity entity = new Entity();
        entity.addTag(Tags.DEBRIS_TAG);
        entity.addLayer(Layers.DEBRIS_LAYER);
        Transform entityTransform = (Transform) entity.getComponentByType(Transform.class);
        entity.addComponent(new Graphics());
        Physics entityPhysics = new Physics();
        entity.addComponent(entityPhysics);
        CollisionCircle collision = new CollisionCircle();
        collision.setBackgroundCollision(false);
        entity.addComponent(collision);
        RespawnTrigger respawnTrigger = new RespawnTrigger();
        entity.addComponent(respawnTrigger);

        if (Public.gameManager.getNrEntitiesWithTag(Tags.PLAYER_TAG, null) > 0)
        {
            this.placeEntity(entityTransform, entityPhysics);
            Public.gameManager.addEntity(entity);
            this.respawnTriggers.add(respawnTrigger);
        }
    }

    private void placeEntity(Transform transform, Physics physics)
    {
        Vector playerPos = this.playerTransform.getPosition();
        transform.setPosition(Tools.getRandomPointOnCircumference(new Vector(Public.screenRect.centerX(), Public.screenRect.centerY()), (Public.screenRect.width() * 0.65f)));
        physics.setVelocity(playerPos.sub(transform.getPosition()).multi(0.65f));
    }
}
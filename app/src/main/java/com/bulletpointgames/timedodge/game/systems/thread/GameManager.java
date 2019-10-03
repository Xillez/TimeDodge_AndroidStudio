package com.bulletpointgames.timedodge.game.systems.thread;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.TextView;

import com.bulletpointgames.timedodge.R;
import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.game.layers.Layers;
import com.bulletpointgames.timedodge.game.systems.ecs.Component;
import com.bulletpointgames.timedodge.game.systems.ecs.Entity;
import com.bulletpointgames.timedodge.game.systems.ecs.components.CollisionCircle;
import com.bulletpointgames.timedodge.game.systems.ecs.components.Graphics;
import com.bulletpointgames.timedodge.game.systems.ecs.components.Physics;
import com.bulletpointgames.timedodge.game.systems.ecs.components.PlayerController;
import com.bulletpointgames.timedodge.game.systems.ecs.components.Transform;
import com.bulletpointgames.timedodge.game.tags.Tags;
import com.bulletpointgames.timedodge.game.view.GameView;
import com.bulletpointgames.timedodge.utils.Logging;
import com.bulletpointgames.timedodge.utils.Time;
import com.bulletpointgames.timedodge.utils.Tools;
import com.bulletpointgames.timedodge.utils.Vector;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameManager extends Thread
{
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private volatile AtomicBoolean running = new AtomicBoolean(true);
    private volatile boolean paused = false;

    private Context context;

    private int framesSinceDebugUpdate = 0;
    private boolean firstFrame = true;

    private volatile ArrayList<Entity> entities = new ArrayList<>();
    public final Semaphore haltUpdating = new Semaphore(1, true);

    public GameManager(Context context)
    {
        this.context = context;
    }

    private void gameCreate()
    {
        Entity ball = new Entity();
        ball.addTag(Tags.PLAYER_TAG);
        ball.addLayer(Layers.PLAYER_LAYER);
        Graphics playerGraphics = new Graphics();
        playerGraphics.isPlayer = true;
        ball.addComponent(playerGraphics);
        ball.addComponent(new PlayerController());
        Physics physics = new Physics();
        ball.addComponent(physics);
        ball.addComponent(new CollisionCircle());
        this.addEntity(ball);
        Time.playerPhysics = physics;

        // Create entities
        try {
            this.haltUpdating();

            Log.i(Logging.LOG_INFO_TAG, "GameManager creating game entities");
            for (Entity entity : this.entities)
            {
                entity.create();
            }
            Log.i(Logging.LOG_INFO_TAG, "GameManager finished creating game entities");

            this.continueUpdating();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        Public.spawnManager.create();
    }

    private void gameUpdate()
    {
        // Update time since last frame.
        Time.updateDeltaTime();

        this.framesSinceDebugUpdate++;
        // Update debug screen every 5 frames.
        if (this.framesSinceDebugUpdate >= 25) {
            ((Activity) this.context).runOnUiThread(() -> { ((TextView) ((Activity) this.context).findViewById(R.id.game_debuginfo_ups)).setText(String.format("UPS: %f", (1.0f / Time.getDeltaTime()))); });
            this.framesSinceDebugUpdate = 0;
        }

        // Skip updating on the first frame due to massive time lag with setup.
        if(this.firstFrame) {
            this.firstFrame = false;
            return;
        }

        // Skip updating if game is paused
        if (paused) {
            return;
        }

        // Handle spawning of entities.
        Public.spawnManager.update();

        // Update TimerManager
        Public.timerManager.update();

        // Update entities
        try {
            this.haltUpdating();
            for (Entity entity : this.entities)
            {
                entity.update();
            }
            this.continueUpdating();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        // Handle events
        Public.gameEventHandler.handleEvents();

        // If running, give up cpu, if not continue for stopping
        if (running.get())
        {
            Tools.sleepRestOfFrame(Time.getDeltaTime(), "Game thread", (Activity) this.context, ((Activity) this.context).findViewById(R.id.game_debuginfo_gamethread_sleeptime));
        }
    }

    private void gameDraw(Canvas canvas)
    {
        // Draw for SpawnManager, Not really needed
        Public.spawnManager.draw(canvas);

        // Draw entities
        for (Entity entity : this.entities)
        {
            entity.draw(canvas);
        }
    }

    // OpenGL Version
    /*private void gameDraw(int vertexBufferPosition, int colorPosition)
    {
        // Draw for SpawnManager, Not really needed
        Public.spawnManager.draw(vertexBufferPosition, colorPosition);

        // Draw entities
        for (Entity entity : this.entities)
        {
            entity.draw(vertexBufferPosition, colorPosition);
        }
    }*/

    private void gameDestroy()
    {
        Log.i(Logging.LOG_INFO_TAG, "GameManager started destruction");
        // Destroy for SpawnManager
        Public.spawnManager.destroy();

        // Destroy entities
        for (Entity entity : this.entities)
        {
            entity.destroy();
        }
        Log.i(Logging.LOG_INFO_TAG, "GameManager finished destruction");
    }

    @Override
    public void run()
    {
        Log.i(Logging.LOG_INFO_TAG, "GameManager thread started construction");
        super.run();
        Log.i(Logging.LOG_INFO_TAG, "GameManager thread finished construction");

        Log.i(Logging.LOG_INFO_TAG, "GameManager started game creation/setup");
        this.gameCreate();
        Log.i(Logging.LOG_INFO_TAG, "GameManager finished game creation/setup");


        while (running.get())
        {
            this.gameUpdate();
            //this.gameDraw();
        }

        this.gameDestroy();
    }

    public void shutdown()
    {
        this.running.set(false);
    }

    public void pause(boolean pause)
    {
        this.paused = pause;
    }

    public ArrayList<Entity> getEntities()
    {
        return this.entities;
    }

    public Entity getEntity(int id)
    {
        return this.entities.get(id);
    }

    public void addEntity(Entity entity)
    {
        try {
            this.haltUpdating();
            if (entity != null)
            {
                entity.create();
                this.entities.add(entity);
            }
            this.continueUpdating();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }

    public ArrayList<Component> getAllComponentsOfType(Class<CollisionCircle> clazz, Entity exclude)
    {
        ArrayList<Component> components = new ArrayList<>();
        for (Entity entity : this.entities)
        {
            // Entity to exclude
            if (exclude != null)
                if (entity.getID() == exclude.getID())
                    continue;
            for (Component comp : entity.getComponents())
                if (comp.getClass().equals(clazz)) components.add(comp);
        }
        return components;
    }

    public ArrayList<Component> getAllComponentsOfTypeNearEntity(Class<CollisionCircle> clazz, Entity exclude, Vector pos, float distance)
    {
        // Can't get components from invalid position or distance
        if (pos == null || distance <= 0.0f)
             return null;

        ArrayList<Component> components = new ArrayList<>();
        for (Entity other : this.entities)
        {
            // Entity to exclude
            if (exclude != null)
                if (other.getID() == exclude.getID())
                    continue;

            Vector otherPos = ((Transform) other.getComponentByType(Transform.class)).getPosition();
            if (otherPos == null)
                return null;

            // If other entity is close to pos
            if (pos.sub(otherPos).length() <= distance)
            {
                for (Component comp : other.getComponents())
                    if (comp.getClass().equals(clazz)) components.add(comp);
            }
        }

        return components;
    }

    public int getNrEntitiesWithTag(String tag, Entity exclude)
    {
        return this.getAllEntitiesWithTag(tag, exclude).size();
    }

    public ArrayList<Entity> getAllEntitiesWithTag(String tag, Entity exclude)
    {
        ArrayList<Entity> entitiesResult = new ArrayList<>();
        for (Entity entity : this.entities)
        {
            // Entity to exclude
            if (exclude != null)
                if (entity.getID() == exclude.getID())
                    continue;
            if (entity.hasTag(tag)) entitiesResult.add(entity);
        }
        return entitiesResult;
    }

    public int getNrEntitiesInLayer(int layer, Entity exclude)
    {
        return this.getAllEntitiesInLayer(layer, exclude).size();
    }

    public ArrayList<Entity> getAllEntitiesInLayer(int layer, Entity exclude)
    {
        ArrayList<Entity> entitiesResult = new ArrayList<>();
        for (Entity entity : this.entities)
        {
            // Entity to exclude
            if (exclude != null)
                if (entity.getID() == exclude.getID())
                    continue;
            if (entity.inLayer(layer)) entitiesResult.add(entity);
        }
        return entitiesResult;
    }

    public int getNrEntitiesInLayerWithTag(String tag, int layer, Entity exclude)
    {
        return this.getAllEntitiesInLayerWithTag(tag, layer, exclude).size();
    }

    public ArrayList<Entity> getAllEntitiesInLayerWithTag(String tag, int layer, Entity exclude)
    {
        ArrayList<Entity> entitiesResult = new ArrayList<>();
        for (Entity entity : this.entities)
        {
            // Entity to exclude
            if (exclude != null)
                if (entity.getID() == exclude.getID())
                    continue;
            if (entity.inLayer(layer) && entity.hasTag(tag)) entitiesResult.add(entity);
        }
        return entitiesResult;
    }

    public void triggerDraw(Canvas canvas)
    {
        try {
            this.haltUpdating();
            this.gameDraw(canvas);
            this.continueUpdating();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }

    // OpenGL Version
    /*public void triggerDraw(int vertexBufferPosition, int colorPosition)
    {
        try {
            this.haltUpdating();
            Log.d(Logging.LOG_DEBUG_TAG, "DDDDDRRRRRRAAAAWWWWW");
            this.gameDraw(vertexBufferPosition, colorPosition);
            this.continueUpdating();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }*/

    public void haltUpdating() throws InterruptedException
    {
        this.haltUpdating.acquire();
    }

    public void continueUpdating()
    {
        this.haltUpdating.release();
    }

    public interface GameLifecycle
    {
        void create();
        void update();
        void draw(Canvas canvas);
        //OpenGL Version --> void draw(int vertexBufferPosition, int colorPosition);
        void destroy();
    }
}
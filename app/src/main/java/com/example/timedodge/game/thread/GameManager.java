package com.example.timedodge.game.thread;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.TextView;

import com.example.timedodge.R;
import com.example.timedodge.game.Public;
import com.example.timedodge.game.ecs.Component;
import com.example.timedodge.game.ecs.Entity;
import com.example.timedodge.game.ecs.components.CollisionCircle;
import com.example.timedodge.game.ecs.components.Graphics;
import com.example.timedodge.game.ecs.components.Physics;
import com.example.timedodge.game.ecs.components.PlayerController;
import com.example.timedodge.game.view.GameCanvas;
import com.example.timedodge.game.view.GameView;
import com.example.timedodge.utils.Logging;
import com.example.timedodge.utils.Tools;
import com.example.timedodge.utils.Vector;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class GameManager extends Thread implements SensorEventListener
{
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private volatile boolean running = true;
    private Vector tiltValues = new Vector(0, 0);

    private Context context;
    private GameCanvas gameCanvas;

    private int framesSinceDebugUpdate = 0;

    private long lastSysTime = 0L;
    private long currentSysTime = 0L;
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
        ball.addComponent(new Graphics());
        ball.addComponent(new PlayerController());
        ball.addComponent(new Physics());
        ball.addComponent(new CollisionCircle());
        this.addEntity(ball);

        // Create entities
        try {
            this.haltUpdating();


            for (Entity entity : this.entities)
            {
                entity.create();
            }

            this.continueUpdating();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }


        Public.spawnManager.create();
    }

    private void gameUpdate(Vector tiltValues)
    {
        this.framesSinceDebugUpdate++;

        // DeltaTime calc
        this.currentSysTime = System.nanoTime();
        float elapsed = (this.currentSysTime - this.lastSysTime) / 1000000000.0f;
        this.lastSysTime = currentSysTime;

        // Update debug screen every 5 frames.
        if (this.framesSinceDebugUpdate >= 25) {
            ((Activity) this.context).runOnUiThread(() -> { ((TextView) ((Activity) this.context).findViewById(R.id.game_debuginfo_ups)).setText(String.format("UPS: %f", (1.0f / elapsed))); });
            this.framesSinceDebugUpdate = 0;
        }

        // Skip updating on the first frame due to massive time lag with setup.
        if(this.firstFrame) {
            this.firstFrame = false;
            return;
        }

        // Handle events
        Public.gameEventHandler.handleEvents();

        // Handle spawning of entities.
        Public.spawnManager.update(elapsed, tiltValues);

        // Update entities
        try {
            this.haltUpdating();
            for (Entity entity : this.entities)
            {
                entity.update(elapsed, tiltValues);
            }
            this.continueUpdating();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        // If running, give up cpu, if not continue for stopping
        if (running)
        {
            Tools.sleepRestOfFrame(elapsed, "Game thread", (Activity) this.context, ((Activity) this.context).findViewById(R.id.game_debuginfo_gamethread_sleeptime));
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
        // Destroy for SpawnManager
        Public.spawnManager.destroy();

        // Destroy entities
        for (Entity entity : this.entities)
        {
            entity.destroy();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        this.tiltValues.set(sensorEvent.values[1], sensorEvent.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    @Override
    public void run()
    {
        super.run();

        this.gameCreate();

        while (running)
        {
            this.gameUpdate(tiltValues);
            //this.gameDraw();
        }

        this.gameDestroy();
    }

    public void shutdown()
    {
        this.running = false;
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

    public ArrayList<Component> getAllComponentsOfType(Class<CollisionCircle> clazz)
    {
        ArrayList<Component> components = new ArrayList<>();
        for (Entity entity : this.entities)
        {
            for (Component comp : entity.getComponents())
                if (comp.getClass().equals(clazz)) components.add(comp);
        }
        return components;
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
        void update(float dt, Vector tiltValues);
        void draw(Canvas canvas);
        //OpenGL Version --> void draw(int vertexBufferPosition, int colorPosition);
        void destroy();
    }
}
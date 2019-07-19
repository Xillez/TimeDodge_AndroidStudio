package com.example.timedodge.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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

import java.util.ArrayList;

public class GameManager implements SensorEventListener
{
    private Context context;
    private GameCanvas gameCanvas;

    private int framesSinceDebugUpdate = 0;

    private long lastSysTime = 0L;
    private long currentSysTime = 0L;
    private boolean firstFrame = true;

    private ArrayList<Entity> entities = new ArrayList<>();

    public GameManager(Context context)
    {
        this.context = context;
    }

    public void setup()
    {
        Entity ball = new Entity();
        ball.addComponent(new Graphics());
        ball.addComponent(new PlayerController());
        ball.addComponent(new Physics());
        ball.addComponent(new CollisionCircle());
        this.entities.add(ball);

        for (Entity entity : this.entities)
        {
            entity.create();
        }

        Public.spawnManager.create();
    }

    private void update(SensorEvent event)
    {
        this.framesSinceDebugUpdate++;

        // DeltaTime calc
        this.currentSysTime = System.currentTimeMillis();
        float elapsed = (currentSysTime - lastSysTime) / 1000.0f;
        if (this.framesSinceDebugUpdate >= 5) {
            ((TextView)((Activity) context).findViewById(R.id.game_debuginfo_fps)).setText(String.format("FPS: %f", (float)(1.0f / elapsed)));
            this.framesSinceDebugUpdate = 0;
        }
        //elapsed *= 0.01f;
        this.lastSysTime = currentSysTime;


        // Skip updating on the first frame due to massive time lag with setup.
        if(this.firstFrame) {
            this.firstFrame = false;
            return;
        }

        // Handle events
        Public.gameEventHandler.handleEvents();

        // Handle spawning of entities.
        Public.spawnManager.update(elapsed, event);

        // Update entities
        for (Entity entity : this.entities)
        {
            entity.update(elapsed, event);
        }

    }

    protected void draw(Canvas canvas)
    {
        // Draw for SpawnManager, Not really needed
        Public.spawnManager.draw(canvas);

        // Draw entities
        for (Entity entity : this.entities)
        {
            entity.draw(canvas);
        }
    }

    public void destroy()
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
        this.update(sensorEvent);

        // Update GUI, new state available
        if (this.gameCanvas != null)
            this.gameCanvas.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

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
        if (entity != null)
        {
            entity.create();
            this.entities.add(entity);
        }
    }

    public ArrayList<Component> getAllComponentsOfType(Class clazz)
    {
        ArrayList<Component> components = new ArrayList<>();
        for (Entity entity : this.entities)
        {
            //Log.d(Logging.LOG_DEBUG_TAG, "" + entity.getComponents());
            for (Component comp : entity.getComponents())
                if (comp.getClass().equals(clazz)) components.add(comp);
        }
        return components;
    }

    public void updateGameCanvas(GameCanvas gameCanvas)
    {
        this.gameCanvas = gameCanvas;
    }
}

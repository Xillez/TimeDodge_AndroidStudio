package com.example.timedodge.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.timedodge.R;
import com.example.timedodge.game.ecs.Component;
import com.example.timedodge.game.ecs.Entity;
import com.example.timedodge.game.ecs.components.CollisionCircle;
import com.example.timedodge.game.ecs.components.Graphics;
import com.example.timedodge.game.ecs.components.Physics;

import java.util.ArrayList;

public class GameManager extends View implements SensorEventListener
{
    private Context context;

    private int framesSinceDebugUpdate = 0;

    private long lastSysTime = 0L;
    private long currentSysTime = 0L;
    private boolean firstFrame = true;

    private ArrayList<Entity> entities = new ArrayList<>();

    public GameManager(Context context)
    {
        super(context);
        this.context = context;
        this.setup();
    }

    public GameManager(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        this.setup();
    }

    private void setup()
    {
        updateScreenBounds();
        Entity ball = new Entity();
        ball.addComponent(new Graphics());
        ball.addComponent(new Physics());
        ball.addComponent(new CollisionCircle());
        this.entities.add(ball);

        /*Entity background = new Entity();
        second.addComponent(new Graphics());
        this.entities.add(second);*/

        /*Entity ball = new Entity();
        ball.create(new OvalShape());
        ball.setRadius(25);
        ball.setPosition(new PointF(this.wSize.x / 2, this.wSize.y / 2));
        ball.setVelocity(new PointF(0.0f, 0.0f));
        ball.setColor(Color.GREEN);
        ball.registerCollisionCallback(this);*/

        for (Entity entity : this.entities)
        {
            entity.create();
        }
    }

    @SuppressLint("DefaultLocale")
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
        elapsed *= 0.01f;
        this.lastSysTime = currentSysTime;


        // Skip first frame due to massive time lag with setup.
        if(this.firstFrame) {
            this.firstFrame = false;
            return;
        }

        Public.gameEventHandler.handleEvents();

        // Update entities
        for (Entity entity : this.entities)
        {
            entity.update(elapsed, event);
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // Draw entities
        for (Entity entity : this.entities)
        {
            entity.draw(canvas);
        }
    }

    public void destroy()
    {
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
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    private void updateScreenBounds()
    {
        Public.screenSize.set(context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels);
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
            this.entities.add(entity);
    }

    public ArrayList<Component> getAllComponentsOfType(Class clazz)
    {
        ArrayList<Component> components = new ArrayList<>();
        for (Entity entity : this.entities)
        {
            for (Component comp : entity.getComponents())
            {
                if (comp.getClass().equals(clazz))
                    components.add(comp);
            }
        }
        return components;
    }
}

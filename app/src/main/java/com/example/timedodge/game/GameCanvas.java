package com.example.timedodge.game;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.timedodge.R;
import com.example.timedodge.game.ecs.Entity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;

public class GameCanvas extends View implements SensorEventListener
{
    private Context context;
    private Point wSize = new Point();
    private final int MARGIN = 5;

    long lastSysTime = 0L;
    long currentSysTime = 0L;
    boolean firstFrame = true;

    private ArrayList<Entity> entities = new ArrayList<>();

    public GameCanvas(Context context)
    {
        super(context);
        this.context = context;
        this.setup();
    }

    public GameCanvas(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        this.setup();
    }

    private void setup()
    {
        updateScreenBounds();

        /*Entity ball = new Entity();
        ball.create(new OvalShape());
        ball.setRadius(25);
        ball.setPosition(new PointF(this.wSize.x / 2, this.wSize.y / 2));
        ball.setVelocity(new PointF(0.0f, 0.0f));
        ball.setColor(Color.GREEN);
        ball.registerCollisionCallback(this);*/

        for (Entity entity : this.entities)
        {
            //entity.create();
        }
    }

    private void update()
    {
        // DeltaTime calc
        this.currentSysTime = System.currentTimeMillis();
        float elapsed = currentSysTime - lastSysTime;
        this.lastSysTime = currentSysTime;

        // Skip first frame due to massive time lag with setup.
        if(this.firstFrame) {
            this.firstFrame = false;
            return;
        }

        // Update entities
        for (Entity entity : this.entities)
        {
            entity.update(elapsed);
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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        this.update();

        // Update GUI, new state available
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    private void updateScreenBounds()
    {
        this.wSize.set(context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels);
    }
}

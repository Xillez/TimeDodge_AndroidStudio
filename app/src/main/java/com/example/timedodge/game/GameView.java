package com.example.timedodge.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.timedodge.game.ecs.Entity;

public class GameView extends GLSurfaceView implements SensorEventListener
{
    private Context context;
    private Point wSize = new Point();
    private final int MARGIN = 5;

    public GameView(Context context)
    {
        super(context);
        this.context = context;
    }

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
    }

    private void setup()
    {
        updateScreenBounds();
        Entity ball = new Entity();
        ball.create(new OvalShape());
        /*ball.setRadius(25);
        ball.setPosition(new PointF(this.wSize.x / 2, this.wSize.y / 2));
        ball.setVelocity(new PointF(0.0f, 0.0f));
        ball.setColor(Color.GREEN);
        ball.registerCollisionCallback(this);*/
    }

    private void update()
    {

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
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

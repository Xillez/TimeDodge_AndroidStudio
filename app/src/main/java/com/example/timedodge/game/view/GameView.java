package com.example.timedodge.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class GameView extends GLSurfaceView
{
    private Context context;
    private GameRenderer renderer;
    private Point wSize = new Point();
    private final int MARGIN = 5;

    public GameView(Context context)
    {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);


        this.renderer = new GameRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(this.renderer);
    }

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
    }

    private void update()
    {

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

    /*@Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        this.update();

        // Update GUI, new state available
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }*/
}

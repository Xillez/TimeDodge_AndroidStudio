package com.example.timedodge.game.thread;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.timedodge.game.view.GameView;

public class GameThread extends Thread
{
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run()
    {
        super.run();
        synchronized (surfaceHolder)
        {
            //this.gameView.start();
        }

        while (running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    //this.gameView.update();
                    //this.gameView.draw(canvas);
                }
            } catch (Exception e) {} finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        synchronized (surfaceHolder)
        {
            //this.gameView.destroy();
        }
    }

    public boolean isRunning()
    {
        return running;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }
}

package com.example.timedodge.game.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.timedodge.R;
import com.example.timedodge.game.systems.thread.GameManager;
import com.example.timedodge.game.Public;
import com.example.timedodge.utils.Logging;
import com.example.timedodge.utils.Tools;

public class GameCanvas extends View
{
    private Context context;

    private boolean firstFrame = true;

    private int framesSinceDebugUpdate = 0;

    private long currentSysTime;
    private long lastSysTime;

    public GameCanvas(Context context)
    {
        super(context);
        this.context = context;
        Public.gameManager = new GameManager(context);
        Public.gameManager.start();
    }

    public GameCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Public.gameManager = new GameManager(context);
        Public.gameManager.start();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        invalidate();

        // DeltaTime calc
        this.currentSysTime = System.nanoTime();
        float elapsed = (this.currentSysTime - this.lastSysTime) / 1000000000.0f;
        this.lastSysTime = currentSysTime;

        // Skip sleeping
        if (this.firstFrame)
        {
            this.firstFrame = false;
            return;
        }

            // Update debug screen every 5 frames.
        if (this.framesSinceDebugUpdate >= 5) {
            ((TextView) ((Activity) this.context).findViewById(R.id.game_debuginfo_fps)).setText(String.format("FPS: %f", ( 1.0f / elapsed)));
            Log.d(Logging.LOG_DEBUG_TAG, String.format("FPS: %f", ( 1.0f / elapsed)));
            this.framesSinceDebugUpdate = 0;
        }

        Public.gameManager.triggerDraw(canvas);

        Tools.sleepRestOfFrame(elapsed, "UI thread", (Activity) this.context, ((Activity) this.context).findViewById(R.id.game_debuginfo_uithread_sleeptime));
    }
}

package com.bulletpointgames.timedodge.game.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bulletpointgames.timedodge.R;
import com.bulletpointgames.timedodge.game.systems.thread.GameManager;
import com.bulletpointgames.timedodge.game.Public;
import com.bulletpointgames.timedodge.utils.Logging;
import com.bulletpointgames.timedodge.utils.Tools;

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

    public void dumpOnDebugImageView(ImageView imageView)
    {
        int addOffset = 750;

        Rect tempScreenRect = new Rect(Public.screenRect.left, Public.screenRect.top, Public.screenRect.right, Public.screenRect.bottom);
        Rect tempGameBoardRect = new Rect(Public.gameBoardRect.left, Public.gameBoardRect.top, Public.gameBoardRect.right, Public.gameBoardRect.bottom);

        Rect drawRect = new Rect(0, 0, 0, 0);
        this.getDrawingRect(drawRect);

        Public.dumpGameCanvas = true;
        Bitmap bitmap = Bitmap.createBitmap(drawRect.width() + (addOffset * 2), drawRect.height() + (addOffset * 2), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(addOffset, addOffset);

        Paint paint = new Paint();
        paint.setColor(0x88000000);
        canvas.drawRect(drawRect, paint);

        this.draw(canvas);
        imageView.setImageBitmap(bitmap);
        Public.dumpGameCanvas = false;

        Public.screenRect.set(tempScreenRect);
        Public.gameBoardRect.set(tempGameBoardRect);
    }
}

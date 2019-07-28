package com.example.timedodge.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.timedodge.game.GameManager;
import com.example.timedodge.game.Public;
import com.example.timedodge.game.ecs.Entity;

public class GameCanvas extends View
{
    private Context context;

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

        Public.gameManager.triggerDraw(canvas);

        invalidate();
    }
}

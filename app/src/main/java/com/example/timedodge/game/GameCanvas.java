package com.example.timedodge.game;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.timedodge.game.Public;
import com.example.timedodge.game.ecs.Entity;

public class GameCanvas extends View
{
    private Context context;

    public GameCanvas(Context context)
    {
        super(context);
        this.context = context;
        Public.gameManager.updateGameCanvas(this);
        Public.gameManager.setup();
    }

    public GameCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Public.gameManager.updateGameCanvas(this);
        Public.gameManager.setup();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Public.gameManager.draw(canvas);
        Public.spawnManager.draw(canvas);
    }
}

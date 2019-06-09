package com.example.timedialator.game.ecs;

import android.content.Context;
import android.graphics.Canvas;

import java.util.HashMap;
import java.util.Map;

public class ECSManager
{
    private int nextId = 0;
    private Map entities = new HashMap();

    private Context context = null;
    private Canvas canvas = null;

    public ECSManager(Context context, Canvas canvas)
    {
        this.context = context;
        this.canvas = canvas;
    }

    public void draw()
    {
        // TODO: DRAW ALL ENTITIES HERE!
    }
}

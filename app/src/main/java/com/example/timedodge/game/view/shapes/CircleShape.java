package com.example.timedodge.game.view.shapes;


import com.example.timedodge.utils.Color;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class CircleShape
{
    public static final int DEFAULT_SEGMENTATION = 16;
    private FloatBuffer vertexBuffer;

    static final int COORDS_PER_VERTEX = 2;
    float color[];

    public CircleShape(int lod, Color color)
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(4 * 2 * lod);
        bb.order(ByteOrder.nativeOrder());
        this.vertexBuffer = bb.asFloatBuffer();

        for (int i = lod; i >= 0; i--)
        {
            this.vertexBuffer.put((float) Math.cos((i / (float)lod) * (180.0f * Math.PI)));
            this.vertexBuffer.put((float) Math.sin((i / (float)lod) * (180.0f * Math.PI)));
        }

        this.vertexBuffer.position(0);

        this.color = color.toFloatArray();
    }

    public void draw()
    {

    }
}

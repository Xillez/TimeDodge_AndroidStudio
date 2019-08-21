package com.bulletpointgames.timedodge.game.view.shapes;


import android.opengl.GLES20;

import com.bulletpointgames.timedodge.utils.Color;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class CircleShape
{
    public static final int DEFAULT_SEGMENTATION = 16;
    private FloatBuffer vertexBuffer;

    static final int COORDS_PER_VERTEX = 2;
    public int nrVertices = 0;
    float color[];

    public CircleShape(int lod, Color color)
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(4 * COORDS_PER_VERTEX * lod * 3);
        bb.order(ByteOrder.nativeOrder());
        this.vertexBuffer = bb.asFloatBuffer();

        for (int i = lod; i >= 0; i--)
        {
            this.vertexBuffer.put(0.0f);
            this.vertexBuffer.put(0.0f);

            if (i != lod) {
                this.vertexBuffer.put((float) Math.cos(((i + 1) / (float)lod) * (180.0f * Math.PI)));
                this.vertexBuffer.put((float) Math.sin(((i + 1) / (float)lod) * (180.0f * Math.PI)));
            }
            else
            {
                this.vertexBuffer.put((float) Math.cos(((1.0f / (float) lod)) * (180.0f * Math.PI)));
                this.vertexBuffer.put((float) Math.sin(((1.0f / (float) lod)) * (180.0f * Math.PI)));
            }

            this.vertexBuffer.put((float) Math.cos((i / (float)lod) * (180.0f * Math.PI)));
            this.vertexBuffer.put((float) Math.sin((i / (float)lod) * (180.0f * Math.PI)));
            nrVertices += 3;
        }

        this.vertexBuffer.position(0);

        this.color = color.toFloatArray();
    }

    public void draw(int vertexBufferPosition, int colorPosition)
    {
        GLES20.glVertexAttribPointer(vertexBufferPosition, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                4 * COORDS_PER_VERTEX, vertexBuffer);
        GLES20.glUniform4fv(colorPosition, 1, this.color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, nrVertices);
    }
}

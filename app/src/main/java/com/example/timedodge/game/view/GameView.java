package com.example.timedodge.game.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.timedodge.game.GameManager;
import com.example.timedodge.game.Public;
import com.example.timedodge.utils.Logging;
import com.example.timedodge.utils.Vector;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GameView extends GLSurfaceView implements GLSurfaceView.Renderer
{
    private Context context;
    private Point wSize = new Point();
    private final int MARGIN = 5;

    private int shaderProg;

    public GameView(Context context)
    {
        super(context);
        this.context = context;

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(this);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        int vertexShader = this.loadShader(GLES20.GL_VERTEX_SHADER, "shaders/default_vertex.shader");
        int fragmentShader = this.loadShader(GLES20.GL_FRAGMENT_SHADER, "shaders/default_fragment.shader");

        if (vertexShader == -1 || fragmentShader == -1)
        {
            Log.e(Logging.LOG_ERR_TAG, "FAILED TO COMPILE SHADERS! ABORTING");
            return;
        }

        this.shaderProg = GLES20.glCreateProgram();
        GLES20.glAttachShader(this.shaderProg, vertexShader);
        GLES20.glAttachShader(this.shaderProg, fragmentShader);
        GLES20.glLinkProgram(this.shaderProg);
        GLES20.glUseProgram(this.shaderProg);
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config)
    {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Public.gameManager = new GameManager(context);
        Public.gameManager.start();
    }

    public void onDrawFrame(GL10 unused)
    {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Public.gameManager.triggerDraw();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        if (Public.gameManager != null)
        {
            Public.gameManager.shutdown();

            while (Public.gameManager != null)
            {
                try {
                    Public.gameManager.join();
                    Public.gameManager = null;
                } catch (InterruptedException e){}
            }
        }
        super.surfaceDestroyed(holder);
    }

    private int loadShader(int type, String fileName)
    {
        AssetManager assetManager = this.context.getAssets();
        byte byteCode[] = {};
        try
        {
            InputStream vertexInputStream = assetManager.open(fileName);
            vertexInputStream.read(byteCode);
        } catch (IOException e)
        {
            return -1;
        }

        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, new String(byteCode));
        GLES20.glCompileShader(shader);
        return shader;
    }
}

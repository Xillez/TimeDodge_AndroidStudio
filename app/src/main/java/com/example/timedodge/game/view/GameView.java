package com.example.timedodge.game.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.timedodge.game.thread.GameManager;
import com.example.timedodge.game.Public;
import com.example.timedodge.utils.Logging;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GameView extends GLSurfaceView implements GLSurfaceView.Renderer
{
    private Context context;
    private Point wSize = new Point();
    private final int MARGIN = 5;

    private int shaderProg;
    private int vertexBufferPosition;
    private int colorPosition;

    private String vertexShaderCode = "#version 120\n" +
            "\n" +
            "attribute vec2 vPosition;\n" +
            "\n" +
            "void main() {\n" +
            "    gl_Position = vec4(vPosition, 0, 0);\n" +
            "}\n";
    private String fragmentShaderCode = "#version 120\n" +
            "\n" +
            "precision mediump float;\n" +
            "\n" +
            "uniform vec4 vColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    gl_FragColor = vColor;\n" +
            "}\n";

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

        this.vertexBufferPosition = GLES20.glGetAttribLocation(this.shaderProg, "vPosition");
        GLES20.glEnableVertexAttribArray(this.vertexBufferPosition);
        this.colorPosition = GLES20.glGetUniformLocation(this.shaderProg, "vColor");
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

        // Enable for OpenGL Version!
        //Public.gameManager.triggerDraw(this.vertexBufferPosition, this.colorPosition);

        GLES20.glDisableVertexAttribArray(this.vertexBufferPosition);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        ((Activity) this.context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        while (Public.gameManager != null)
        {
            Public.gameManager.shutdown();
            try {
                Public.gameManager.join();
                Public.gameManager = null;
            } catch (InterruptedException e){}
        }
        super.surfaceDestroyed(holder);
    }

    private int loadShader(int type, String fileName)
    {
        /*AssetManager assetManager = this.context.getAssets();
        byte byteCode[] = {};
        try
        {
            InputStream vertexInputStream = assetManager.open(fileName);
            vertexInputStream.read(byteCode);
        } catch (IOException e)
        {
            return -1;
        }*/

        int shader = GLES20.glCreateShader(type);
        //GLES20.glShaderSource(shader, new String(byteCode));
        if (fileName.contains("vertex"))
            GLES20.glShaderSource(shader, this.vertexShaderCode);
        else if (fileName.contains("fragment"))
            GLES20.glShaderSource(shader, this.fragmentShaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }


}

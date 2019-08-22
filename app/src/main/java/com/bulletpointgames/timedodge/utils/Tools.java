package com.bulletpointgames.timedodge.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.widget.TextView;

import com.bulletpointgames.timedodge.game.Public;

import java.util.Random;

public class Tools
{
    public static final Random rnd = new Random();

    public static int getNanosToSleep(float elapsed)
    {
        int nanos = (int) Math.floor(Public.MAX_FRAME_TIME_NANO - (elapsed * 1000000000.0f));
        if (nanos < 0)
            nanos *= -1.0f;
        return ((nanos >= Public.MAX_FRAME_TIME_NANO) ? 0 : nanos);
    }

    public static void sleepRestOfFrame(float elapsed, String threadName, Activity activity, TextView textView)
    {
        int nanosToSleep = getNanosToSleep(elapsed);

        // Log.d(Logging.LOG_DEBUG_TAG, String.format("%s: %s", activity, textView));

        if (activity != null && textView != null)
        {
            if (threadName.contains("Game"))
            {
                activity.runOnUiThread(() -> {
                    textView.setText(String.format("%s: %f", threadName, nanosToSleep / 1000000000.0f));
                });
            }
            else if (threadName.contains("UI"))
            {
                activity.runOnUiThread(() -> {
                    textView.setText(String.format("%s: %f", threadName, nanosToSleep / 1000000000.0f));
                });
            }
        }
        try
        {
            Thread.sleep(nanosToSleep / 1000000, nanosToSleep % 1000000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static int getNewUID()
    {
        Random rnd = new Random();
        String input = "";
        for (int i = 0; i < rnd.nextInt(236) + 20; i++)
        {
            input = input.concat(Character.toString((char)rnd.nextInt(256)));
        }
        return input.hashCode();
    }

    public static Vector getRandomPointOnCircumference(Vector pos, float radius)
    {
        float angle = (rnd.nextInt(360) + 1) * (180.0f / (float) java.lang.Math.PI);
        return new Vector((radius * (float) java.lang.Math.cos(angle)) + pos.x, (radius * (float) java.lang.Math.sin(angle)) + pos.y);
    }

    public static Vector getRandomPointOnScreen()
    {
        return new Vector((rnd.nextFloat() * Public.screenRect.width()) + Public.screenRect.left, (rnd.nextFloat() * Public.screenRect.height()) + Public.screenRect.top);
    }

    public static Vector findClosestScreenUnstuckPosition(Vector pos, Vector size)
    {
        Vector unstuckPos = new Vector(pos.x, pos.y);

        // Collision with left
        if ((pos.x - (size.x / 2.0f)) <= Public.gameBoardRect.left)
        {
            unstuckPos.setAxis(Vector.Axis.x, Public.gameBoardRect.left + (size.x / 2.0f) + 0.01f);
        }

        // Collision with top
        if ((pos.y - (size.y / 2.0f)) <= Public.gameBoardRect.top)
        {
            unstuckPos.setAxis(Vector.Axis.y, Public.gameBoardRect.top + (size.y / 2.0f) + 0.01f);
        }

        // Collision with right
        if ((pos.x + (size.x / 2.0f)) >= Public.gameBoardRect.right)
        {
            unstuckPos.setAxis(Vector.Axis.x, Public.gameBoardRect.right - (size.x / 2.0f) - 0.01f);
        }

        // Collision with bottom
        if ((pos.y + (size.y / 2.0f)) >= Public.gameBoardRect.bottom)
        {
            unstuckPos.setAxis(Vector.Axis.y, Public.gameBoardRect.bottom - (size.y / 2.0f) - 0.01f);
        }

        return unstuckPos;
    }

    public static boolean isVisibleOnScreen(Rect otherRect)
    {
        return (Rect.intersects(Public.screenRect, otherRect) || Public.screenRect.contains(otherRect));
    }

    public static int fromDPtoDevicePixels(float dp)
    {
        return (int) (dp * Public.screenPixelDensity);
    }
}

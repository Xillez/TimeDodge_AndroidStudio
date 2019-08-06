package com.example.timedodge.utils;

import android.app.Activity;
import android.widget.TextView;

import com.example.timedodge.game.Public;

import java.util.Random;

public class Tools
{
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
        if (activity != null && textView != null)
            if (threadName.contains("Game"))
                activity.runOnUiThread(() -> { textView.setText(String.format("%s: %f", threadName, getNanosToSleep(elapsed) / 1000000000.0f)); });
            else if (threadName.contains("UI"))
                textView.setText(String.format("%s: %f", threadName, getNanosToSleep(elapsed) / 1000000000.0f));
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
}

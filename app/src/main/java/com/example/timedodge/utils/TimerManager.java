package com.example.timedodge.utils;

import android.util.Log;

import java.util.ArrayList;

public class TimerManager
{
    private class TimerData
    {
        public long timeUntilTrigger;
        public long timeSoFar = 0L;
        public Runnable runnable;

        public TimerData(long millisUntilTrigger, Runnable runnable)
        {
            this.timeUntilTrigger = millisUntilTrigger;
            this.runnable = runnable;
        }
    }

    private ArrayList<TimerData> timers = new ArrayList<>();

    public void registerTimer(long timeUntilTrigger, Runnable runnable)
    {
        this.timers.add(new TimerData(timeUntilTrigger, runnable));
    }

    public void update()
    {
        for (TimerData timerData : this.timers)
        {
            timerData.timeSoFar += (long) (Time.getDeltaTime() * 1000);
            if (timerData.timeSoFar >= timerData.timeUntilTrigger)
            {
                if (timerData.runnable != null)
                {
                    timerData.runnable.run();
                }
                timerData.timeSoFar = 0L;
            }
        }
    }
}

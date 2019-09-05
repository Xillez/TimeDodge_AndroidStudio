package com.bulletpointgames.timedodge.utils;

import java.util.ArrayList;

public class TimerManager
{
    private class TimerData
    {
        public long timeUntilStart;
        public long timeUntilTrigger;
        public long timeSoFar = 0L;
        public boolean started = true;
        public Runnable runnable;

        public TimerData(long timeUntilStart, long millisUntilTrigger, Runnable runnable)
        {
            this.timeUntilStart = timeUntilStart;
            this.timeUntilTrigger = millisUntilTrigger;
            this.runnable = runnable;
        }
    }

    private ArrayList<TimerData> timers = new ArrayList<>();

    public void registerTimer(long timeUntilStart, long timeUntilTrigger, Runnable runnable)
    {
        this.timers.add(new TimerData(timeUntilStart, timeUntilTrigger, runnable));
    }

    public void update()
    {
        for (TimerData timerData : this.timers)
        {
            timerData.timeSoFar += (long) (Time.getDeltaTime() * 1000);

            if (!timerData.started)
            {
                timerData.started = (timerData.timeSoFar >= timerData.timeUntilStart);
                if (timerData.started)
                    timerData.timeSoFar = 0L;
            }

            if (timerData.started && timerData.timeSoFar >= timerData.timeUntilTrigger)
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

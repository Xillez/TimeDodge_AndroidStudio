package com.bulletpointgames.timedodge.utils;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class Input implements SensorEventListener
{
    private static volatile Vector tiltValues = new Vector(0, 0);

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        tiltValues.set(sensorEvent.values[1], sensorEvent.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    public static Vector getTiltValues()
    {
        return tiltValues;
    }
}

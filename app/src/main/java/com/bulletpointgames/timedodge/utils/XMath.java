package com.bulletpointgames.timedodge.utils;

public class XMath
{
    public static float lerp(float from, float to)
    {
        return (1 - Time.getDeltaTimeNanos()) * from + Time.getDeltaTimeNanos() * to;
    }

    public static Vector lerp(Vector from, Vector to)
    {
        return new Vector((1 - Time.getDeltaTimeNanos()) * from.x + Time.getDeltaTimeNanos() * to.x, (1 - Time.getDeltaTimeNanos()) * from.y + Time.getDeltaTimeNanos() * to.y);
    }

    // A C/C++ example implementation provided by AMD. https://en.wikipedia.org/wiki/Smoothstep
    /*public static float slerp(float from, float to)
    {
        // Scale, bias and saturate x to 0..1 range
        float x = XMath.clamp((Time.getDeltaTimeNanos() - from) / (to - from), 0.0f, 1.0f);
        // Evaluate polynomial
        return x * x * (3 - 2 * x);
    }

    public static Vector slerp(Vector from, Vector to)
    {
        // Scale, bias and saturate x to 0..1 range
        float x = XMath.clamp((Time.getDeltaTimeNanos() - from.x) / (to.x - from.x), 0.0f, 1.0f);

        // Scale, bias and saturate y to 0..1 range
        float y = XMath.clamp((Time.getDeltaTimeNanos() - from.y) / (to.y - from.y), 0.0f, 1.0f);

        // Evaluate polynomial
        return new Vector(((x * x * (3 - 2 * x)) * (to.x - from.x)) + from.x , ((y * y * (3 - 2 * y)) * (to.y - from.y)) + from.y);
    }*/

    public static int clamp(int value, int max, int min)
    {
        return Math.max(min, Math.min(max, value));
    }

    public static float clamp(float value, float max, float min)
    {
        return Math.max(min, Math.min(max, value));
    }

    public static boolean floatEqual(float value, float checkAgainst, float precision)
    {
        return (value >= checkAgainst - precision && value <= checkAgainst + precision);
    }
}

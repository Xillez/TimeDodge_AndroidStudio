package com.example.timedodge.utils;

public class Color
{
    private float r;
    private float g;
    private float b;
    private float a;

    public Color()
    {
        this.r = 0.0f;
        this.g = 0.0f;
        this.b = 0.0f;
        this.a = 0.0f;
    }

    public Color(float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void set(float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public float red()
    {
        return r;
    }

    public float green()
    {
        return g;
    }

    public float blue()
    {
        return b;
    }

    public float alpha()
    {
        return a;
    }

    public float[] toFloatArray()
    {
        return new float[] {this.r, this.g, this.b, this.a};
    }
}

package com.example.timedodge.utils;

import android.graphics.PointF;

public class Vector extends PointF
{
    public enum Axis {x, y};

    public Vector(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector(PointF point)
    {
        this.x = point.x;
        this.y = point.y;
    }

    public Vector(Vector vector)
    {
        this.x = vector.x;
        this.y = vector.y;
    }

    public Vector add(float scalar)
    {
        return new Vector(this.x + scalar, this.y + scalar);
    }

    public Vector add(PointF other)
    {
        return new Vector(x + other.x, y + other.y);
    }

    public Vector add(Vector other)
    {
        return new Vector(x + other.x, y + other.y);
    }

    public Vector sub(float scalar)
    {
        return new Vector(this.x - scalar, this.y - scalar);
    }

    public Vector sub(PointF other)
    {
        return new Vector(this.x - other.x, this.y - other.y);
    }

    public Vector sub(Vector other)
    {
        return new Vector(this.x - other.x, this.y - other.y);
    }

    public Vector multi(float scalar)
    {
        return new Vector(this.x * scalar, this.y * scalar);
    }

    public Vector multi(PointF other)
    {
        return new Vector(this.x * other.x, this.y * other.y);
    }

    public Vector multi(Vector other)
    {
        return new Vector(this.x * other.x, this.y * other.y);
    }

    public Vector div(float scalar)
    {
        return new Vector(this.x / scalar, this.y / scalar);
    }

    public Vector div(PointF other)
    {
        return new Vector(this.x / other.x, this.y / other.y);
    }

    public Vector div(Vector other)
    {
        return new Vector(this.x / other.x, this.y / other.y);
    }

    public void addTo(float scalar)
    {
        this.x += scalar;
        this.y += scalar;
    }

    public void addTo(PointF other)
    {
        this.x += other.x;
        this.y += other.y;
    }

    public void addTo(Vector other)
    {
        this.x += other.x;
        this.y += other.y;
    }

    public void subTo(float scalar)
    {
        this.x -= scalar;
        this.y -= scalar;
    }

    public void subTo(PointF other)
    {
        this.x -= other.x;
        this.y -= other.y;
    }

    public void subTo(Vector other)
    {
        this.x -= other.x;
        this.y -= other.y;
    }

    public void multiTo(float scalar)
    {
        this.x *= scalar;
        this.y *= scalar;
    }

    public void multiTo(PointF other)
    {
        this.x *= other.x;
        this.y *= other.y;
    }

    public void multiTo(Vector other)
    {
        this.x *= other.x;
        this.y *= other.y;
    }

    public void divTo(float scalar)
    {
        this.x /= scalar;
        this.y /= scalar;
    }

    public void divTo(PointF other)
    {
        this.x /= other.x;
        this.y /= other.y;
    }

    public void divTo(Vector other)
    {
        this.x /= other.x;
        this.y /= other.y;
    }

    public void addToAxis(Axis axis, float scalar)
    {
        if (axis == Axis.x)
            this.x += scalar;
        else if (axis == Axis.y)
            this.y += scalar;
    }
    public void subToAxis(Axis axis, float scalar)
    {
        if (axis == Axis.x)
            this.x -= scalar;
        else if (axis == Axis.y)
            this.y -= scalar;
    }
    public void multiToAxis(Axis axis, float scalar)
    {
        if (axis == Axis.x)
            this.x *= scalar;
        else if (axis == Axis.y)
            this.y *= scalar;
    }
    public void divToAxis(Axis axis, float scalar)
    {
        if (axis == Axis.x)
            this.x /= scalar;
        else if (axis == Axis.y)
            this.y /= scalar;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}

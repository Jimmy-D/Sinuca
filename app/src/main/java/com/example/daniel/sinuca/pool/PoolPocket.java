package com.example.daniel.sinuca.pool;

import android.graphics.Color;
import android.graphics.PointF;

/**
 * Created by Daniel on 31/10/2017.
 */

public class PoolPocket {
    private int         mDebugColor = Color.CYAN;
    private PointF      mPosition;
    private float       mRadius;

    public PoolPocket(PointF position, float radius) {
        mPosition = position;
        mRadius = radius;
    }

    public int getDebugColor() {
        return mDebugColor;
    }
    public PointF getPosition() {
        return mPosition;
    }
    public float getRadius() {
        return mRadius;
    }
}

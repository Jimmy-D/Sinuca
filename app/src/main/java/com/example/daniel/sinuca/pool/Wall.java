package com.example.daniel.sinuca.pool;

import android.graphics.Color;
import android.graphics.PointF;

/**
 * Created by Daniel on 29/10/2017.
 */

public class Wall {
    private int             mDebugColor = Color.RED;
    private PointF          mInicialPoint = new PointF();
    private PointF          mFinalPoint = new PointF();
    private PointF          mNormalVector = new PointF();

    public Wall(PointF inicialPoint, PointF finalPoint, float normalDegreesClockWise) {
        mInicialPoint = inicialPoint;
        mFinalPoint = finalPoint;

        mNormalVector.set((float) Math.cos(Math.PI * normalDegreesClockWise / 180),
                (float) Math.sin(Math.PI * normalDegreesClockWise / 180));
    }

    public int getDebugColor() {
        return mDebugColor;
    }
    public PointF getInicialPoint() {
        return mInicialPoint;
    }
    public PointF getFinalPoint() {
        return mFinalPoint;
    }
    public PointF getNormalVector() {
        return mNormalVector;
    }
}

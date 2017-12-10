package com.example.daniel.sinuca.pool;

import android.graphics.PointF;

/**
 * Created by Daniel on 03/11/2017.
 */

public class CueStick {
    private PointF mSize = new PointF();
    private PointF mPosition = new PointF();
    private PointF mTargetPosition = new PointF();
    private float mAngleCW;
    private float mDistanceToPoint;
    private boolean mIsActive;

    private Image mImage;

    public CueStick(PointF size) {
        mSize = size;
    }

    public void _setRelativePosition() {
        mPosition.x = mTargetPosition.x - mDistanceToPoint - mSize.x;
        mPosition.y = mTargetPosition.y - mSize.y / 2.0f;
    }

    public PointF getSize() {
        return mSize;
    }

    public PointF getPosition() {
        return mPosition;
    }

    public PointF getTargetPosition() {
        return mTargetPosition;
    }
    public void setTargetPosition(PointF targetPosition) {
        mTargetPosition = targetPosition;

        _setRelativePosition();
    }

    public float getAngleCW() {
        return mAngleCW;
    }
    public void setAngleCW(float angleCW) {
        mAngleCW = angleCW;
    }
    public float getDistanceToPoint() {
        return mDistanceToPoint;
    }
    public void setDistanceToPoint(float distanceToPoint) {
        mDistanceToPoint = distanceToPoint;

        if (mTargetPosition != null) {
            _setRelativePosition();
        }
    }
    public boolean isActive() {
        return mIsActive;
    }
    public void setIsActive(boolean isActive) {
        mIsActive = isActive;
    }
}

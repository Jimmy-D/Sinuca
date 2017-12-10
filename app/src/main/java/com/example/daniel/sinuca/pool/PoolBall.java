package com.example.daniel.sinuca.pool;

import android.graphics.PointF;

/**
 * Created by Daniel on 30/10/2017.
 */

public class PoolBall {
    private PointF      mPosition;
    private PointF      mVelocity = new PointF();
    private float       mRadius;
    private int         mColor;
    private boolean     mIsPressed;
    private int         mNumber;

    public PoolBall(PointF initialPosition, float radius, int color, int number) {
        mPosition = initialPosition;
        mRadius = radius;
        mColor = color;
        mNumber = number;

        mVelocity.set(0, 0);
    }

    public boolean isPressed(PointF position) {
        if (position.y > mPosition.y - 3 * mRadius &&
                position.y < mPosition.y + 3 * mRadius) {
            float x = (float) Math.sqrt(mRadius * mRadius * 9 -
                    (position.y - mPosition.y) * (position.y - mPosition.y));
            if (position.x > mPosition.x - x && position.x < mPosition.x + x) {
                mIsPressed = true;
            } else {
                mIsPressed = false;
            }
        } else {
            mIsPressed = false;
        }
        return mIsPressed;
    }

    public PointF getPosition() {
        return mPosition;
    }
    public void setPosition(PointF position) {
        mPosition = position;
    }
    public void setPosition(float Px, float Py) {
        mPosition.set(Px, Py);
    }
    public PointF getVelocity() {
        return mVelocity;
    }
    public void setVelocity(PointF velocity) {
        mVelocity = velocity;
    }
    public void setVelocity(float Vx, float Vy) {
        mVelocity.set(Vx, Vy);
    }
    public void setIsPressed(boolean isPressed) {
        mIsPressed = isPressed;
    }
    public float getRadius() {
        return mRadius;
    }
    public int getNumber() {
        return mNumber;
    }
    public int getColor() {
        return mColor;
    }
}

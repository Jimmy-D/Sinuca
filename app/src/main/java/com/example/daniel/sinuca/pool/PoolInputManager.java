package com.example.daniel.sinuca.pool;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Daniel on 30/10/2017.
 */

public class PoolInputManager extends GestureDetector.SimpleOnGestureListener{
    public static final String TAG = "Pool";

    private GestureDetector mGestureDetector;
    private PoolView        mView;
    private PoolTable       mTable;

    private PointF          mTempPosition = new PointF();
    private PointF          mTempVelocity = new PointF();
    private PointF          mScallingFactor;
    private PointF          mBallToTouchPoint = new PointF();
    private float           mStickDistance;
    private float           mInitialTouchingDistance;
    private float           mTempValue;
    private double          mStickAngleCW;
    private Point           mOffsetFromOrigin;
    private boolean         mGrabedCueStick;

    public PoolInputManager(Context context, PoolView view) {
        mGestureDetector = new GestureDetector(context, this);
        mGestureDetector.setIsLongpressEnabled(false);
        mView = view;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        if (action == MotionEvent.ACTION_UP) {
            onUp(event);
        }
        return mGestureDetector.onTouchEvent(event);
    }

    public boolean onUp(MotionEvent event) {
        if (mGrabedCueStick) {
            mTable.getWhiteBall().setIsPressed(false);
            if (mBallToTouchPoint.length() < mInitialTouchingDistance) {
                mTable.hitBall();
            } else {
                mTable.getStick().setIsActive(false);
            }
            mGrabedCueStick = false;
        }
        return true;
    }
/*
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (mWasPressed) {
            if (mTable.hasNoMovement()) {
                mTempVelocity.set(velocityX / (mScallingFactor.x * 3),
                        velocityY / (mScallingFactor.y * 3));
            }
        }
        return true;
    }*/

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (mGrabedCueStick) {
            mTempPosition.set(e2.getX() / mScallingFactor.x - mOffsetFromOrigin.x,
                    e2.getY() / mScallingFactor.y - mOffsetFromOrigin.y);
            mBallToTouchPoint.set(mTempPosition.x - mTable.getWhiteBall().getPosition().x,
                    mTempPosition.y - mTable.getWhiteBall().getPosition().y);
            mTable.setTempStickDirection(mBallToTouchPoint);
            mTable.setInvisibleBallPosition();
            mStickAngleCW = Math.atan2(mBallToTouchPoint.y, mBallToTouchPoint.x);
            mTable.getStick().setAngleCW((float) (mStickAngleCW * 180 / Math.PI));
            if (mBallToTouchPoint.length() >= mInitialTouchingDistance) {
                mStickDistance = 20;
            } else if (mBallToTouchPoint.length() < mInitialTouchingDistance &&
                    mBallToTouchPoint.length() >= mInitialTouchingDistance - 150) {
                mStickDistance = 20 + (mInitialTouchingDistance - mBallToTouchPoint.length()) / 1.5f;
            } else if (mBallToTouchPoint.length() < mInitialTouchingDistance - 100) {
                mStickDistance = 120;
            }
            mTable.getStick().setDistanceToPoint(mStickDistance);
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mTable = mView.getTable();
        if (mTable.hasNoMovement()) {
            mScallingFactor = mView.getRenderer().getViewport().getScalingFactor();
            mOffsetFromOrigin = mView.getRenderer().getViewport().getOffsetFromOrigin();
            mTempPosition.set(e.getX() / mScallingFactor.x - mOffsetFromOrigin.x,
                    e.getY() / mScallingFactor.y - mOffsetFromOrigin.y);
            if (!mTable.getWhiteBall().isPressed(mTempPosition)) {
                mGrabedCueStick = true;
                mStickDistance = 20;
                mTable.getStick().setDistanceToPoint(mStickDistance);
                mTable.getStick().setTargetPosition(mTable.getWhiteBall().getPosition());
                mBallToTouchPoint.set(mTempPosition.x - mTable.getWhiteBall().getPosition().x,
                        mTempPosition.y - mTable.getWhiteBall().getPosition().y);
                mTable.setTempStickDirection(mBallToTouchPoint);
                mInitialTouchingDistance = mBallToTouchPoint.length();
                mTable.setInvisibleBallPosition();
                mStickAngleCW = Math.atan2(mBallToTouchPoint.y, mBallToTouchPoint.x);
                mTable.getStick().setAngleCW((float) (mStickAngleCW * 180 / Math.PI));
                mTable.getStick().setIsActive(true);
            }
        }
        return true;
    }
}

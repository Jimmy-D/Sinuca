package com.example.daniel.sinuca.structure;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Daniel on 21/10/2017.
 */

public class BallGenerator {
    public static final String TAG = "Aplicativo02";
    public static final float   BALL_RADIUS = 3.0f;
    public static final float   BALL_EXTERNAL_RADIUS = 20.0f;

    private float               mRadius;
    private float               mExternalRadius;
    private Canvas              mTempCanvas;
    private Point               mScreenDimensions = new Point();
    private Point               mTempPosition = new Point();
    private Paint               mTempPaint = new Paint();
    Random                      mRandom = new Random();

    private ArrayList<Ball>    mBalls = new ArrayList<Ball>();

    public BallGenerator(Point screenDimensions) {
        mScreenDimensions = screenDimensions;

        mRadius = (BALL_RADIUS / 100) * screenDimensions.x;
        mExternalRadius = (BALL_EXTERNAL_RADIUS / 100) * screenDimensions.x;
    }

    public boolean detectCollision (Point position) {
        for (Ball ball : mBalls) {
            if (position.y < ball.position.y - 2.0f * ball.getRadius() ||
                    position.y > ball.position.y + 2.0f * ball.getRadius()) {
                return false;
            } else {
                float x = (float) Math.sqrt(ball.getRadius() * ball.getRadius() * 4.0f -
                        (position.y - ball.position.y) * (position.y - ball.position.y));
                if (position.x < ball.position.x - x || position.x > ball.position.x + x) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public Ball detectBallCollision (Point position) {
        Ball collidedBall = null;
        for (Ball ball : mBalls) {
            if (position.y > ball.position.y - ball.getRadius() &&
                    position.y < ball.position.y + ball.getRadius()) {
                float x = (float) Math.sqrt(ball.getRadius() * ball.getRadius() -
                        (position.y - ball.position.y) * (position.y - ball.position.y));
                if (position.x > ball.position.x - x && position.x < ball.position.x + x) {
                    collidedBall = ball;
                }
            }
        }
        return collidedBall;
    }


    public void createBall(int color) {
        int i = 20;
        do {
            mTempPosition.set(mRandom.nextInt(mScreenDimensions.x),
                    mRandom.nextInt(mScreenDimensions.y));
            i--;
        } while (detectCollision(mTempPosition) && i > 0);
        if (i == 0) {
            Log.e(TAG, "não foi possível criar a bola");
        } else {
            mBalls.add((new Ball()).create(mTempPosition, mRadius, color));
        }
    }

    public void createBall(int color, Point position) {
        mBalls.add((new Ball()).create(position, mRadius, color));
    }

    public void createBall(int color, Point position, float radius) {
        float r = (radius / 100) * mScreenDimensions.x;
        mBalls.add((new Ball()).create(position, r, color));
    }

    public boolean deleteBall(Point position) {
        for (Ball ball : mBalls) {
            if (position == ball.getPosition()) {
                return mBalls.remove(ball);
            }
        }
        return false;
    }

    public boolean deleteBall(Ball ball) {
        for (Ball b : mBalls) {
            if (b == ball) {
                return mBalls.remove(ball);
            }
        }
        return false;
    }

    public class Ball {
        private Point position = new Point();
        private float radius;
        private int color;

        public Ball create(Point p, float r, int c) {
            position.set(p.x, p.y);
            radius = r;
            color = c;
            return this;
        }

        public Point getPosition() {return position;}
        public float getRadius() {return radius;}
        public int getColor() {return color;}
    }

    public ArrayList<Ball> getBalls() { return mBalls; }
    public float getRadius() { return mRadius; }
    public float getExternalRadius() { return mExternalRadius; }
}

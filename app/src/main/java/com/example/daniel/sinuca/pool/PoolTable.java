package com.example.daniel.sinuca.pool;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;

/**
 * Created by Daniel on 30/10/2017.
 */

public class PoolTable {
    public static final float       BALL_RADIUS = 16.0f;
    public static final float       POCKET_RADIUS = 40.0f;
    public static final float       COR = 0.98f; // Coefficient of Restitution
    public static final float       WALL_COR = 0.95f;
    public static final float       DESACELATION_RATIO = 0.986f;

    private Point                   mDimensions;
    private PointF                  mTempDistanceVector = new PointF();
    private PointF                  mTempStickDirection = new PointF();
    private boolean                 mBallWasHit;
    private boolean                 mWillBePocketed;
    private float                   mDistanceWhenHit;

    private PoolBallManager         mBallManager;
    private PoolBall                mWhiteBall;
    private PoolBall                mInvisibleBall;
    private ArrayList<Wall>         mWallList = new ArrayList<Wall>();
    private ArrayList<PoolPocket>   mPocketList = new ArrayList<PoolPocket>();
    private PoolBall                mDeletedBall;
    private CueStick                mStick;
    private boolean                 mWaiting;

    public PoolTable(Point dimensions) {
        mDimensions = new Point(dimensions);

        mBallManager = new PoolBallManager();
    }

    public void setup() {
        mWhiteBall = mBallManager.createBall(new PointF(795, 300), BALL_RADIUS, Color.WHITE, 0);
        mBallManager.createBall(new PointF(300, 300), BALL_RADIUS, Color.YELLOW, 1);
        mBallManager.createBall(new PointF(272.28f, 284), BALL_RADIUS, Color.BLUE, 2);
        mBallManager.createBall(new PointF(272.28f, 316), BALL_RADIUS, Color.RED, 3);
        mBallManager.createBall(new PointF(244.56f, 268), BALL_RADIUS, Color.rgb(102, 0, 204), 4);
        mBallManager.createBall(new PointF(244.56f, 300), BALL_RADIUS, Color.rgb(255, 165, 0), 5);
        mBallManager.createBall(new PointF(244.56f, 332), BALL_RADIUS, Color.rgb(0, 153, 51), 6);
        mBallManager.createBall(new PointF(216.84f, 252), BALL_RADIUS, Color.rgb(153, 0, 0), 7);
        mBallManager.createBall(new PointF(216.84f, 284), BALL_RADIUS, Color.BLACK, 8);
        mBallManager.createBall(new PointF(216.84f, 316), BALL_RADIUS, Color.YELLOW, 9);
        mBallManager.createBall(new PointF(216.84f, 348), BALL_RADIUS, Color.BLUE, 10);
        mBallManager.createBall(new PointF(189.12f, 236), BALL_RADIUS, Color.RED, 11);
        mBallManager.createBall(new PointF(189.12f, 268), BALL_RADIUS, Color.rgb(102, 0, 204), 12);
        mBallManager.createBall(new PointF(189.12f, 300), BALL_RADIUS, Color.rgb(255, 165, 0), 13);
        mBallManager.createBall(new PointF(189.12f, 332), BALL_RADIUS, Color.rgb(0, 153, 51), 14);
        mBallManager.createBall(new PointF(189.12f, 364), BALL_RADIUS, Color.rgb(153, 0, 0), 15);

        mWallList.add(new Wall(new PointF(40, 78), new PointF(54, 92), -45));
        mWallList.add(new Wall(new PointF(54, 92), new PointF(54, 508), 0));
        mWallList.add(new Wall(new PointF(54, 508), new PointF(40, 522), 45));
        mWallList.add(new Wall(new PointF(78, 560), new PointF(92, 546), -135));
        mWallList.add(new Wall(new PointF(92, 546), new PointF(518, 546), -90));
        mWallList.add(new Wall(new PointF(518, 546), new PointF(522, 560), -45));
        mWallList.add(new Wall(new PointF(572, 560), new PointF(576, 546), -135));
        mWallList.add(new Wall(new PointF(576, 546), new PointF(1002, 546), -90));
        mWallList.add(new Wall(new PointF(1002, 546), new PointF(1016, 560), -45));
        mWallList.add(new Wall(new PointF(1054, 522), new PointF(1040, 508), 135));
        mWallList.add(new Wall(new PointF(1040, 508), new PointF(1040, 92), 180));
        mWallList.add(new Wall(new PointF(1040, 92), new PointF(1054, 78), -135));
        mWallList.add(new Wall(new PointF(1016, 40), new PointF(1002, 54), 45));
        mWallList.add(new Wall(new PointF(1002, 54), new PointF(576, 54), 90));
        mWallList.add(new Wall(new PointF(576, 54), new PointF(572, 40), 135));
        mWallList.add(new Wall(new PointF(522, 40), new PointF(518, 54), 45));
        mWallList.add(new Wall(new PointF(518, 54), new PointF(92, 54), 90));
        mWallList.add(new Wall(new PointF(92, 54), new PointF(78, 40), 135));

        mPocketList.add(new PoolPocket(new PointF(40, 40), POCKET_RADIUS));
        mPocketList.add(new PoolPocket(new PointF(547, 14), POCKET_RADIUS));
        mPocketList.add(new PoolPocket(new PointF(1054, 40), POCKET_RADIUS));
        mPocketList.add(new PoolPocket(new PointF(40, 560), POCKET_RADIUS));
        mPocketList.add(new PoolPocket(new PointF(547, 586), POCKET_RADIUS));
        mPocketList.add(new PoolPocket(new PointF(1054, 560), POCKET_RADIUS));

        mStick = new CueStick(new PointF(500, 30));

        mInvisibleBall = new PoolBall(new PointF(), BALL_RADIUS, Color.LTGRAY, 20);
    }

    public void step(float elapsedTimeInSeconds) {
        if(elapsedTimeInSeconds > 1.0f)
        {
            elapsedTimeInSeconds = 0.1f;
        }
        if (mDeletedBall != null) {
            if (mDeletedBall == mWhiteBall) {
                if (!mWaiting) {
                    mBallManager.deleteBall(mDeletedBall);
                    mWaiting = true;
                }
                if (hasNoMovement()) {
                    mBallManager.addBall(mWhiteBall);
                    mWhiteBall.setPosition(795, 300);
                    mWhiteBall.setVelocity(0, 0);
                    mWaiting = false;
                    mDeletedBall = null;
                }

            } else {
                mBallManager.deleteBall(mDeletedBall);
                mDeletedBall = null;
            }
        }
        for (PoolBall ball : mBallManager.getBallList()) {
            ball.getPosition().set(
                    ball.getPosition().x + ball.getVelocity().x * elapsedTimeInSeconds,
                    ball.getPosition().y + ball.getVelocity().y * elapsedTimeInSeconds);
            ball.getVelocity().set(ball.getVelocity().x * DESACELATION_RATIO,
                    ball.getVelocity().y * DESACELATION_RATIO);
            if (ball.getVelocity().length() < 4) {
                ball.getVelocity().set(0, 0);
            }
            for (PoolBall ball2 : mBallManager.getBallList()) {
                if (ball != ball2) {
                    colidedWithBall(ball, ball2);
                }
            }
            for (Wall wall : mWallList) {
                colidedWithWall(ball, wall);
            }
            for (PoolPocket pocket : mPocketList) {
                if (enteredPocket(ball, pocket)) {
                    mDeletedBall = ball;
                }
            }
        }
    }

    public boolean colidedWithBall(PoolBall ball1, PoolBall ball2) {
        mTempDistanceVector.set(ball2.getPosition().x - ball1.getPosition().x,
                ball2.getPosition().y - ball1.getPosition().y);
        float centerDistance = mTempDistanceVector.length();
        if (centerDistance >= ball1.getRadius() + ball2.getRadius()) {
            return false;
        } else {
            float p1 = _dotOperation(mTempDistanceVector, ball1.getVelocity()) /
                    _dotOperation(mTempDistanceVector, mTempDistanceVector);
            PointF projV1 = new PointF(mTempDistanceVector.x * p1, mTempDistanceVector.y * p1);
            float p2 = _dotOperation(mTempDistanceVector, ball2.getVelocity()) /
                    _dotOperation(mTempDistanceVector, mTempDistanceVector);
            PointF projV2 = new PointF(mTempDistanceVector.x * p2, mTempDistanceVector.y * p2);
            PointF result = new PointF(projV1.x - projV2.x, projV1.y - projV2.y);
            if (_dotOperation(result, mTempDistanceVector) > 0) {
                PointF rject1 = new PointF(ball1.getVelocity().x - projV1.x,
                        ball1.getVelocity().y - projV1.y);
                PointF rject2 = new PointF(ball2.getVelocity().x - projV2.x,
                        ball2.getVelocity().y - projV2.y);
                float Vx = (COR * (projV2.x - projV1.x) + (projV1.x + projV2.x)) / 2;
                float Vy = (COR * (projV2.y - projV1.y) + (projV1.y + projV2.y)) / 2;
                PointF projV1After = new PointF(Vx, Vy);
                Vx = (COR * (projV1.x - projV2.x) + (projV1.x + projV2.x)) / 2;
                Vy = (COR * (projV1.y - projV2.y) + (projV1.y + projV2.y)) / 2;
                PointF projV2After = new PointF(Vx, Vy);
                ball1.setVelocity(projV1After.x + rject1.x, projV1After.y + rject1.y);
                ball2.setVelocity(projV2After.x + rject2.x, projV2After.y + rject2.y);
                return true;
            }
        }
        return false;
    }

    public boolean colidedWithWall(PoolBall ball, Wall wall) {
        PointF initialToBallPosition = new PointF(ball.getPosition().x - wall.getInicialPoint().x,
                ball.getPosition().y - wall.getInicialPoint().y);
        float distance = _dotOperation(initialToBallPosition, wall.getNormalVector());
        if (distance >= ball.getRadius()) {
            return false;
        } else {
            if (_dotOperation(ball.getVelocity(), wall.getNormalVector()) < 0) {
                PointF finalToBallPosition = new PointF(ball.getPosition().x - wall.getFinalPoint().x,
                        ball.getPosition().y - wall.getFinalPoint().y);
                PointF rjectITBP = new PointF(initialToBallPosition.x -
                        wall.getNormalVector().x * distance, initialToBallPosition.y -
                        wall.getNormalVector().y * distance);
                PointF rjectFTBP = new PointF(finalToBallPosition.x -
                        wall.getNormalVector().x * distance, finalToBallPosition.y -
                        wall.getNormalVector().y * distance);
                PointF initialToFinal = new PointF(wall.getFinalPoint().x - wall.getInicialPoint().x,
                        wall.getFinalPoint().y - wall.getInicialPoint().y);
                if (_dotOperation(rjectITBP, initialToFinal) >= 0
                        && _dotOperation(rjectFTBP, initialToFinal) <= 0) {
                    float pV = _dotOperation(ball.getVelocity(), wall.getNormalVector());
                    PointF projV = new PointF(pV * wall.getNormalVector().x,
                            pV * wall.getNormalVector().y);
                    PointF rjectV = new PointF(ball.getVelocity().x - projV.x,
                            ball.getVelocity().y - projV.y);
                    ball.setVelocity((rjectV.x - projV.x) * WALL_COR,
                            (rjectV.y - projV.y) * WALL_COR);
                    return true;
                } else if (initialToBallPosition.length() <= ball.getRadius()) {
                    PointF cornerNormalVector = new PointF(initialToBallPosition.x /
                            initialToBallPosition.length(), initialToBallPosition.y /
                            initialToBallPosition.length());
                    float pV = _dotOperation(ball.getVelocity(), cornerNormalVector);
                    PointF projV = new PointF(pV * cornerNormalVector.x,
                            pV * cornerNormalVector.y);
                    PointF rjectV = new PointF(ball.getVelocity().x - projV.x,
                            ball.getVelocity().y - projV.y);
                    ball.setVelocity((rjectV.x - projV.x) * WALL_COR,
                            (rjectV.y - projV.y) * WALL_COR);
                    return true;
                } else if (finalToBallPosition.length() <= ball.getRadius()) {
                    PointF cornerNormalVector = new PointF(finalToBallPosition.x /
                            finalToBallPosition.length(), finalToBallPosition.y /
                            finalToBallPosition.length());
                    float pV = _dotOperation(ball.getVelocity(), cornerNormalVector);
                    PointF projV = new PointF(pV * cornerNormalVector.x,
                            pV * cornerNormalVector.y);
                    PointF rjectV = new PointF(ball.getVelocity().x - projV.x,
                            ball.getVelocity().y - projV.y);
                    ball.setVelocity((rjectV.x - projV.x) * WALL_COR,
                            (rjectV.y - projV.y) * WALL_COR);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean enteredPocket(PoolBall ball, PoolPocket pocket) {
        mTempDistanceVector.set(pocket.getPosition().x - ball.getPosition().x,
                pocket.getPosition().y - ball.getPosition().y);
        if (mTempDistanceVector.length() < pocket.getRadius()) {
            return true;
        }
        return false;
    }

    public boolean hasNoMovement() {
        for (PoolBall ball : mBallManager.getBallList()) {
            if (ball.getVelocity().length() != 0) {
                return false;
            }
        }
        return true;
    }

    public void hitBall() {
        mDistanceWhenHit = mStick.getDistanceToPoint();
        mBallWasHit = true;
        mStick.setIsActive(false);
    }

    public void startMoving() {
        mWhiteBall.setVelocity(mTempStickDirection.x * mDistanceWhenHit * 16,
                mTempStickDirection.y * mDistanceWhenHit * 16);
        mBallWasHit = false;
    }

    public void setInvisibleBallPosition() {
        mInvisibleBall.setPosition(mWhiteBall.getPosition().x, mWhiteBall.getPosition().y);
        mInvisibleBall.setVelocity(mTempStickDirection.x, mTempStickDirection.y);
        while (mInvisibleBall.getPosition().x > 0 || mInvisibleBall.getPosition().x < mDimensions.x
                || mInvisibleBall.getPosition().y > 0
                || mInvisibleBall.getPosition().y < mDimensions.y) {
            mInvisibleBall.setPosition(mInvisibleBall.getPosition().x +  mTempStickDirection.x,
                    mInvisibleBall.getPosition().y +  mTempStickDirection.y);
            for (PoolBall ball : mBallManager.getBallList()) {
                if (colidedWithBall(mInvisibleBall, ball)) {
                    mWillBePocketed = false;
                    return;
                }
            }
            for (Wall wall : mWallList) {
                if (colidedWithWall(mInvisibleBall, wall)) {
                    mWillBePocketed = false;
                    return;
                }
            }
        }
        mWillBePocketed = true;
    }

    private float _dotOperation(PointF x, PointF y) {
        float result = x.x * y.x + x.y * y.y;
        return result;
    }


    public Point getDimensions() {
        return mDimensions;
    }
    public PoolBallManager getBallManager() {
        return mBallManager;
    }
    public PoolBall getWhiteBall() {
        return mWhiteBall;
    }
    public ArrayList<Wall> getWallList() {
        return mWallList;
    }
    public ArrayList<PoolPocket> getPocketList() {
        return mPocketList;
    }
    public CueStick getStick() { return  mStick; }
    public boolean ballWasHit() {
        return mBallWasHit;
    }
    public void setBallWasHit(boolean ballWasHit) {
        mBallWasHit = ballWasHit;
    }
    public PointF getTempVelocityDirection() {
        return mTempStickDirection;
    }
    public void setTempStickDirection(PointF direction) {
        mTempStickDirection.set(direction.x / direction.length(),
                direction.y / direction.length());
    }
    public PoolBall getInvisibleBall() {
        return mInvisibleBall;
    }
    public boolean willBePocketed() {
        return mWillBePocketed;
    }
}

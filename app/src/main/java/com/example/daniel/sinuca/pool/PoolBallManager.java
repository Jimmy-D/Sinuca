package com.example.daniel.sinuca.pool;

import android.graphics.PointF;

import java.util.ArrayList;

/**
 * Created by Daniel on 30/10/2017.
 */

public class PoolBallManager {
    private ArrayList<PoolBall>     mBallList = new ArrayList<PoolBall>();

    public PoolBallManager() {
    }

    public PoolBall createBall(PointF initialPosition, float radius, int color, int number) {
        PoolBall ball = new PoolBall(initialPosition, radius, color, number);
        mBallList.add(ball);
        return ball;
    }

    public void addBall(PoolBall ball) {
        mBallList.add(ball);
    }

    public void deleteBall(PoolBall ball) {
        mBallList.remove(ball);
    }

    public ArrayList<PoolBall> getBallList() {
        return mBallList;
    }
}

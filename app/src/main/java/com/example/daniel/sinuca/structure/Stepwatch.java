package com.example.daniel.sinuca.structure;

import android.os.SystemClock;

/**
 * Created by Daniel on 12/10/2017.
 */

public class Stepwatch {
    protected long  mCurrentTime;
    protected long  mLastTime;
    protected float mElapsedTime;

    protected float tick() {
        if (mCurrentTime == 0) {
            mLastTime = mCurrentTime = SystemClock.uptimeMillis();
        } else {
            mCurrentTime = SystemClock.uptimeMillis();
        }

        mElapsedTime = (mCurrentTime - mLastTime) / 1000.0f;
        mLastTime = mCurrentTime;

        return mElapsedTime;
    }
}

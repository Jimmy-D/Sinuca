package com.example.daniel.sinuca.pool;

import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;

import com.example.daniel.sinuca.structure.SwipeActivity;

/**
 * Created by Daniel on 28/10/2017.
 */

public class PoolActivity extends SwipeActivity {
    private PoolView mView;
    private PoolTable mTable;
    private PoolInputManager inputManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableKeepScreenOn();
        enableFullScreen();

        Point worldDimensions = new Point(1094, 600);

        mTable = new PoolTable(worldDimensions);

        mView = new PoolView(this, mTable);
        setContentView(mView);
        inputManager = new PoolInputManager(this, mView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return inputManager.onTouchEvent(event);
    }
}

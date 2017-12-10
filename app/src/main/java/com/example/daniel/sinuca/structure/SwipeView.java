package com.example.daniel.sinuca.structure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;

/**
 * Created by Daniel on 12/10/2017.
 */

public class SwipeView extends View {
    private Point       mDimensions = new Point();
    private Stepwatch   mStepwatch = new Stepwatch();
    private boolean     mHasStarted;

    public SwipeView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        step(canvas, mStepwatch.tick());

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mDimensions.set(w, h);

        if (!mHasStarted) {
            setup();
            mHasStarted = true;
        }
    }

    public void step(Canvas canvas, float elapsedTimeInSeconds) {
    }

    public void step(Canvas canvas) {
    }

    public void setup() {
    }

    public Point getDimensions() {
        return mDimensions;
    }
}

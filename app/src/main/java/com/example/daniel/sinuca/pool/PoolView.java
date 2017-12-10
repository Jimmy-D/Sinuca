package com.example.daniel.sinuca.pool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.daniel.sinuca.structure.SwipeView;
import com.example.daniel.sinuca.structure.Viewport;

/**
 * Created by Daniel on 28/10/2017.
 */

public class PoolView extends SwipeView {
    private Renderer2       mRenderer = new Renderer2();
    private ImageFactory    mImageFactory;
    private PoolTable       mTable;
    private Image           mImgTable;
    private Image           mImgStick;
    private boolean         mIsDebug = false;

    private float mStickScale;
    private float i=0;

    public PoolView(Context context, PoolTable table) {
        super(context);

        mTable = table;
        mImageFactory = new ImageFactory(context);
    }


    @Override
    public void step(Canvas canvas, float elapsedTimeInSeconds) {
        mTable.step(elapsedTimeInSeconds);
        mRenderer.beginDrawing(canvas, Color.LTGRAY, Color.rgb(0, 153, 51));

        mRenderer.drawBackgroundImage(mImgTable);
        mRenderer.drawAllBalls(mTable.getBallManager().getBallList());

        if (mTable.getStick().isActive()) {
            mRenderer.drawImage(mImgStick, mTable.getStick().getPosition(), mStickScale,
                    mTable.getStick().getAngleCW(), mTable.getStick().getTargetPosition());
            mRenderer.drawLine(mTable.getWhiteBall().getPosition(),
                    mTable.getInvisibleBall().getPosition(), Color.LTGRAY);
            if (!mTable.willBePocketed()) {
                mRenderer.drawBall(mTable.getInvisibleBall(), Paint.Style.STROKE);
            }
        } else if (mTable.ballWasHit()) {
            mTable.getStick().setDistanceToPoint(mTable.getStick().getDistanceToPoint()
                    - 2000 * elapsedTimeInSeconds);
            mRenderer.drawImage(mImgStick, mTable.getStick().getPosition(), mStickScale,
                    mTable.getStick().getAngleCW(), mTable.getStick().getTargetPosition());
            if (mTable.getStick().getDistanceToPoint() < 20) {
                mTable.startMoving();
            }
        }

        if (mIsDebug) {
            mRenderer.drawAllWalls(mTable.getWallList());
            mRenderer.drawAllPockets(mTable.getPocketList());
        }
    }

    @Override
    public void setup() {
        Point viewDimensions = getDimensions();
        Viewport viewport = new Viewport(mTable.getDimensions(), viewDimensions,
                Viewport.ScalingMode.FULL_SCREEN_KEEP_ORIGINAL_ASPECT);
        mRenderer.setViewport(viewport);

        mImgTable = mImageFactory.createImage("images/pool_table.png");
        mImgStick = mImageFactory.createImage("images/pool_stick_cutted.png");

        mTable.setup();
        mStickScale = mTable.getStick().getSize().x / mImgStick.getDimensions().x;
    }

    public Renderer2 getRenderer() {
        return mRenderer;
    }
    public ImageFactory getImageFactory() {
        return mImageFactory;
    }
    public PoolTable getTable() {
        return mTable;
    }
    public Image getImgTable() {
        return mImgTable;
    }
    public Image getImgStick() {
        return mImgStick;
    }

}

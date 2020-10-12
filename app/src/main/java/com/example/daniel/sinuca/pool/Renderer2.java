package com.example.daniel.sinuca.pool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;

import com.example.daniel.sinuca.structure.Viewport;

import java.util.ArrayList;

/**
 * Created by Daniel on 29/10/2017.
 */

public class Renderer2 {
    private Canvas mTempCanvas;
    private PointF mTempPosition = new PointF();
    private float mTempRadius;
    private Paint mTempPaint = new Paint();
    private Viewport    mViewport;
    private RectF mTempDstRect = new RectF();
    private PointF mTempPointOfRotation = new PointF();

    public Renderer2() {
    }

    @SuppressWarnings("deprecation")
    public void beginDrawing(Canvas canvas, int screenColor, int viewportColor) {
        mTempCanvas = canvas;
    }

    public void drawBackgroundImage(Image image) {
        if (mViewport != null) {
            if (image != null) {
                Bitmap bitmap = image.getBitmap();
                mTempCanvas.drawBitmap(bitmap, null, mViewport.getDrawingArea(), mTempPaint);
            }
        }
    }

    public void drawBall(PoolBall ball, Paint.Style style) {
        if (mViewport != null) {
            Point offsetFromOrigin = mViewport.getOffsetFromOrigin();
            PointF scalingFactor = mViewport.getScalingFactor();

            mTempPosition.set(ball.getPosition().x * scalingFactor.x + offsetFromOrigin.x,
                    ball.getPosition().y * scalingFactor.y + offsetFromOrigin.y);
            mTempRadius = ball.getRadius() * scalingFactor.x;

            mTempPaint.setColor(ball.getColor());
            mTempPaint.setStyle(style);
            mTempCanvas.drawCircle(mTempPosition.x, mTempPosition.y, mTempRadius,
                    mTempPaint);
        }

    }

    public void drawAllBalls(ArrayList<PoolBall> list) {
        for (PoolBall ball : list) {
            drawBall(ball, Paint.Style.FILL);
        }
    }

    public void drawWall(Wall wall) {
        if (mViewport != null) {
            Point offsetFromOrigin = mViewport.getOffsetFromOrigin();
            PointF scalingFactor = mViewport.getScalingFactor();

            mTempPaint.setColor(wall.getDebugColor());
            mTempPaint.setStyle(Paint.Style.FILL);
            mTempCanvas.drawLine(wall.getInicialPoint().x * scalingFactor.x + offsetFromOrigin.x,
                    wall.getInicialPoint().y * scalingFactor.y + offsetFromOrigin.y,
                    wall.getFinalPoint().x * scalingFactor.x + offsetFromOrigin.x,
                    wall.getFinalPoint().y * scalingFactor.y + offsetFromOrigin.y,
                    mTempPaint);
        }
    }

    public void drawAllWalls(ArrayList<Wall> list) {
        for (Wall wall : list) {
            drawWall(wall);
        }
    }

    public void drawPocket(PoolPocket pocket) {
        if (mViewport != null) {
            Point offsetFromOrigin = mViewport.getOffsetFromOrigin();
            PointF scalingFactor = mViewport.getScalingFactor();

            mTempPosition.set(pocket.getPosition().x * scalingFactor.x + offsetFromOrigin.x,
                    pocket.getPosition().y * scalingFactor.y + offsetFromOrigin.y);
            mTempRadius = pocket.getRadius() * scalingFactor.x;

            mTempPaint.setColor(pocket.getDebugColor());
            mTempPaint.setStyle(Paint.Style.FILL);
            mTempCanvas.drawCircle(mTempPosition.x, mTempPosition.y, mTempRadius,
                    mTempPaint);
        }

    }

    public void drawAllPockets(ArrayList<PoolPocket> list) {
        for (PoolPocket pocket : list) {
            drawPocket(pocket);
        }
    }

    public void drawImage(Image image, PointF worldDestination,
                          PointF dstDimensions) {
        if (mViewport != null) {
            Point offsetFromOrigin = mViewport.getOffsetFromOrigin();
            PointF scalingFactor = mViewport.getScalingFactor();

            mTempDstRect.left = (worldDestination.x * scalingFactor.x) + offsetFromOrigin.x;
            mTempDstRect.top = (worldDestination.y * scalingFactor.y) + offsetFromOrigin.y;
            mTempDstRect.right = ((worldDestination.x + dstDimensions.x)
                    * scalingFactor.x) + offsetFromOrigin.x;
            mTempDstRect.bottom = ((worldDestination.y + dstDimensions.y)
                    * scalingFactor.y) + offsetFromOrigin.y;

            if (image != null) {
                Bitmap bitmap = image.getBitmap();
                mTempCanvas.drawBitmap(bitmap, null, mTempDstRect, mTempPaint);
            }
        }
    }

    public void drawImage(Image image, PointF worldDestination,float scale, float rotationCW,
                          PointF pointOfRotation) {
        if (mViewport != null) {
            Point offsetFromOrigin = mViewport.getOffsetFromOrigin();
            PointF scalingFactor = mViewport.getScalingFactor();
            Matrix matrix = new Matrix();

            mTempPosition.set(worldDestination.x * scalingFactor.x + offsetFromOrigin.x,
                    worldDestination.y * scalingFactor.y + offsetFromOrigin.y);
            mTempPointOfRotation.set(pointOfRotation.x * scalingFactor.x + offsetFromOrigin.x,
                    pointOfRotation.y * scalingFactor.y + offsetFromOrigin.y);
            matrix.setTranslate(mTempPosition.x, mTempPosition.y);
            matrix.preScale(scalingFactor.x * scale, scalingFactor.y * scale);
            matrix.postRotate(rotationCW, mTempPointOfRotation.x, mTempPointOfRotation.y);

            if (image != null) {
                Bitmap bitmap = image.getBitmap();
                mTempCanvas.drawBitmap(bitmap, matrix, mTempPaint);
            }
        }
    }

    public void drawLine(PointF initial, PointF end, int color) {
        if (mViewport != null) {
            Point offsetFromOrigin = mViewport.getOffsetFromOrigin();
            PointF scalingFactor = mViewport.getScalingFactor();

            mTempPaint.setColor(color);
            mTempPaint.setStyle(Paint.Style.FILL);
            mTempCanvas.drawLine(initial.x * scalingFactor.x + offsetFromOrigin.x,
                    initial.y * scalingFactor.y + offsetFromOrigin.y,
                    end.x * scalingFactor.x + offsetFromOrigin.x,
                    end.y * scalingFactor.y + offsetFromOrigin.y,
                    mTempPaint);
        }
    }

    public void setViewport(Viewport viewport) {
        mViewport = viewport;
    }
    public Viewport getViewport() {
        return mViewport;
    }
}

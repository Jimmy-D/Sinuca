package com.example.daniel.sinuca.pool;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Created by Daniel on 30/10/2017.
 */

public class Image {
    private Bitmap mBitmap = null;
    private Point mDimensions = new Point();

    public Image(Bitmap bitmap) {
        mBitmap = bitmap;
        mDimensions.set(mBitmap.getWidth(), mBitmap.getHeight());
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public Point getDimensions() {
        return mDimensions;
    }
}

package com.example.daniel.sinuca.pool;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Daniel on 30/10/2017.
 */

public class ImageFactory {
    protected Context mContext = null;

    public ImageFactory(Context context)
    {
        mContext = context;
    }

    public Image createImage(String filename)
    {
        Bitmap bitmap = null;

        try
        {
            AssetManager assetManager = mContext.getAssets();
            InputStream inputStream = assetManager.open(filename);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        }
        catch (IOException e)
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SGImageFactory.createImage(): arquivo ");
            stringBuilder.append(filename);
            stringBuilder.append(" não encontrado!");
            Log.d("SimpleGameEngine", stringBuilder.toString());
            return null;
        }

        Image image = new Image(bitmap);

        return image;
    }
}

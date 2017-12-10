package com.example.daniel.sinuca.structure;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Daniel on 12/10/2017.
 */

public class SwipeActivity extends Activity {
    public static final String TAG = "Aplicativo02";

    public void enableFullScreen()
    {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.requestFeature(Window.FEATURE_NO_TITLE);

        if(android.os.Build.VERSION.SDK_INT >= 19)
        {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void enableKeepScreenOn()
    {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart () chamado.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart () chamado.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume () chamado.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause () chamado.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop () chamado.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy () chamado.");
    }
}

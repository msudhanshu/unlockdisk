package com.unlockdisk.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.unlockdisk.android.opengl.Config;
import com.unlockdisk.android.opengl.GameLevelVar;
import com.unlockdisk.android.opengl.SimpleOpenGLES20Renderer;
import com.unlockdisk.android.sphere.SphereRenderer;
import com.unlockdisk.android.util.IntArray2;

public class CommonGLSurfaceView extends GLSurfaceView {

    protected CommonGLRenderer mRenderer;
    protected SimpleOpenGLES20Renderer tRenderer;

    public CommonGLSurfaceView(Context context) {
    	super(context);
       /* // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
       mRenderer = new SphereRenderer();
        tRenderer = new SimpleOpenGLES20Renderer();
        
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
       setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
   */ }

    protected final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    protected float mPreviousX;
    protected float mPreviousY;
    protected float dx;
    protected float dy;
    
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:

            float dx = x - mPreviousX;
            float dy = y - mPreviousY;

            // reverse direction of rotation above the mid-line
            if (y > getHeight() / 2) {
              dx = dx * -1 ;
            }

            // reverse direction of rotation to left of the mid-line
            if (x < getWidth() / 2) {
              dy = dy * -1 ;
            }

            mRenderer.mAngleX += (dx ) * TOUCH_SCALE_FACTOR;  // = 180.0f / 320
            mRenderer.mAngleY += ( dy) * TOUCH_SCALE_FACTOR;  // = 180.0f / 320
            requestRender();
    }

    mPreviousX = x;
    mPreviousY = y;
    return true;
    }
}

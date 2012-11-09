package com.unlockdisk.android.disk;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.unlockdisk.android.opengl.CommonGLRenderer;
import com.unlockdisk.android.opengl.CommonGLSurfaceView;
import com.unlockdisk.android.opengl.Config;
import com.unlockdisk.android.opengl.GameLevelVar;
import com.unlockdisk.android.opengl.SimpleOpenGLES20Renderer;
import com.unlockdisk.android.util.IntArray2;

public class DiskSurfaceView extends CommonGLSurfaceView {

    public DiskSurfaceView(Context context) {
    	super(context);
        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new DiskRenderer();
       // tRenderer = new SimpleOpenGLES20Renderer();
        
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
       setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
        	dx +=  (x - mPreviousX);
        	dy +=  (y - mPreviousY);
           

            case MotionEvent.ACTION_UP:

                 dx += (x - mPreviousX);
                 dy +=  (y - mPreviousY);

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
              //    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                //  dy = dy * -1 ;
                }

             //  mRenderer.mAngleX += dx* TOUCH_SCALE_FACTOR;  // = 180.0f / 320
               //  mRenderer.mAngleY += dy* TOUCH_SCALE_FACTOR;  // = 180.0f / 320
                
                if ( Math.abs(dx) >= Config.DRAG_THRESH && Math.abs(dx)>Math.abs(dy))
                {
                	if (dx > 0 ) {
                		mRenderer.MoveDir = CommonGLRenderer.KeyPressed.MKEY_RIGHT;
                		IntArray2 temp = GameLevelVar.LeftOfVoid( Config.mGameLevelVar[0][0].ArenaGrid, mRenderer.RingNo, mRenderer.SliceNo);
                	    mRenderer.imoveRing = temp.x;
                	    mRenderer.imoveSlice = temp.y;
                	}
                	else {
                		mRenderer.MoveDir = CommonGLRenderer.KeyPressed.MKEY_LEFT;
                	//	GameLevelVar.RightOfVoid( Config.mGameLevelVar[0][0].ArenaGrid, Config.mGameLevelVar[0][0].ArenaGridCount[0], Config.mGameLevelVar[0][0].ArenaGridCount[1]);
                		IntArray2 temp = GameLevelVar.RightOfVoid( Config.mGameLevelVar[0][0].ArenaGrid, mRenderer.RingNo, mRenderer.SliceNo);
                	    mRenderer.imoveRing = temp.x;
                	    mRenderer.imoveSlice = temp.y;
                	}
                }
                else if ( Math.abs(dy) >= Config.DRAG_THRESH && Math.abs(dy)>Math.abs(dx))
                {
                	if (dy > 0 ) {
                		mRenderer.MoveDir = CommonGLRenderer.KeyPressed.MKEY_UP;
                		IntArray2 temp = GameLevelVar.UpOfVoid( Config.mGameLevelVar[0][0].ArenaGrid, mRenderer.RingNo, mRenderer.SliceNo);
                	    mRenderer.imoveRing = temp.x;
                	    mRenderer.imoveSlice = temp.y;
                	}
                	else { 
                		mRenderer.MoveDir = CommonGLRenderer.KeyPressed.MKEY_RIGHT;
                		IntArray2 temp = GameLevelVar.DownOfVoid( Config.mGameLevelVar[0][0].ArenaGrid, mRenderer.RingNo, mRenderer.SliceNo);
                	    mRenderer.imoveRing = temp.x;
                	    mRenderer.imoveSlice = temp.y;
                	}
                }
                requestRender();
                dx = 0;
                dy = 0;
        }
        mPreviousX = x;
        mPreviousY = y;

        return true;
    }
}

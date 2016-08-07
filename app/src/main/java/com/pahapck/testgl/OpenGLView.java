package com.pahapck.testgl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class OpenGLView extends GLSurfaceView {

    private OpenGLRenderer4 mRenderer;

    private float mDownX = 0.0f;
    private float mDownY = 0.0f;
    private boolean isTouch = true;
    public OpenGLView(Context context, int id, boolean isTouch) {
        super(context);
        this.isTouch = isTouch;
        mRenderer = new OpenGLRenderer4(context, id, isTouch);
        this.setRenderer(mRenderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isTouch){
            return  false;
        }
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            case MotionEvent.ACTION_MOVE:
                float mX = event.getX();
                float mY = event.getY();
                mRenderer.mLightX += (mX-mDownX)/10;
                mRenderer.mLightY -= (mY-mDownY)/10;
                mDownX = mX;
                mDownY = mY;
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
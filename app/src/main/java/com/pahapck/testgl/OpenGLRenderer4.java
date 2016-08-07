package com.pahapck.testgl;

/**
 * Created by Home on 2016-07-26.
 */
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;

public class OpenGLRenderer4 implements Renderer {

    // Ambient light
    private final float[] mat_ambient = { 0.2f, 0.3f, 0.4f, 1.0f };
    private FloatBuffer mat_ambient_buf;
    // Parallel incident light
    private final float[] mat_diffuse = { 0.4f, 0.6f, 0.8f, 1.0f };
    private FloatBuffer mat_diffuse_buf;
    // The highlighted area
    private final float[] mat_specular = { 0.2f * 0.4f, 0.2f * 0.6f, 0.2f * 0.8f, 1.0f };
    private FloatBuffer mat_specular_buf;

    private Sphere2 mSphere; // = new Sphere();

    public volatile float mLightX = 10f;
    public volatile float mLightY = 10f;
    public volatile float mLightZ = 10f;
    private Context mContext;
    private int id = 0;
    private boolean isTouch = true;
    private float pos = 0;
    public OpenGLRenderer4(Context mContext, int id, boolean istouch){
        this.mContext = mContext;
        this.id = id;
        this.isTouch = istouch;
        mSphere = new Sphere2(3, 2);
    }
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0.0f, 0.0f);

        if(isTouch) {
            gl.glRotatef(-mLightX, 0.0f, 1.0f, 0.0f);
           // gl.glRotatef(mLightY, 1.0f, 0.0f, 0.0f);
        }else{
            pos += 0.2f;
            gl.glRotatef(pos, 0.0f, 1.0f, 0.0f);
        }
        mSphere.draw(gl);
    }
    private static final float CLEAR_RED = 0.0f;

    /** Clear colour, alpha component. */
    private static final float CLEAR_GREEN = 0.0f;

    /** Clear colour, alpha component. */
    private static final float CLEAR_BLUE = 0.0f;

    /** Clear colour, alpha component. */
    private static final float CLEAR_ALPHA = 0.5f;
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mSphere.loadGLTexture(gl, this.mContext, id);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearColor(CLEAR_RED, CLEAR_GREEN, CLEAR_BLUE, CLEAR_ALPHA);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        // Set the output screen size
        gl.glViewport(0, 0, width, height);

        // Projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl.glLoadIdentity();
        // Set the viewport size
        // gl.glFrustumf(0, width, 0, height, 0.1f, 100.0f);

        GLU.gluPerspective(gl, 90.0f, (float) width / height, 0.1f, 50.0f);

        // Select the model view matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();

    }



    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
        // On the perspective correction
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        // Background: Black
        gl.glClearColor(0, 0.0f, 0.0f, 0.0f);
        // Start the smooth shading
        gl.glShadeModel(GL10.GL_SMOOTH);

        // Reset the depth buffer
        gl.glClearDepthf(1.0f);
        // Start the depth test
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // Type the depth test
        gl.glDepthFunc(GL10.GL_LEQUAL);

        initBuffers();
    }

    private void initBuffers() {
        ByteBuffer bufTemp = ByteBuffer.allocateDirect(mat_ambient.length * 4);
        bufTemp.order(ByteOrder.nativeOrder());
        mat_ambient_buf = bufTemp.asFloatBuffer();
        mat_ambient_buf.put(mat_ambient);
        mat_ambient_buf.position(0);

        bufTemp = ByteBuffer.allocateDirect(mat_diffuse.length * 4);
        bufTemp.order(ByteOrder.nativeOrder());
        mat_diffuse_buf = bufTemp.asFloatBuffer();
        mat_diffuse_buf.put(mat_diffuse);
        mat_diffuse_buf.position(0);

        bufTemp = ByteBuffer.allocateDirect(mat_specular.length * 4);
        bufTemp.order(ByteOrder.nativeOrder());
        mat_specular_buf = bufTemp.asFloatBuffer();
        mat_specular_buf.put(mat_specular);
        mat_specular_buf.position(0);
    }
}
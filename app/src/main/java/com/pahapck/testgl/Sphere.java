package com.pahapck.testgl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

// Calculation of spherical vertex
public class Sphere {
    private float[] texture = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    FloatBuffer getFloatBufferFromTextureArray(float texture[]) {
        ByteBuffer tbb = ByteBuffer.allocateDirect(texture.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = tbb.asFloatBuffer();
        buffer.put(texture);
        buffer.position(0);
        return buffer;
    }
    public void draw(GL10 gl) {

        float	angleA, angleB;
        float	cos, sin;
        float	r1, r2;
        float	h1, h2;
        float	step = 3.0f; //
        float[][] v = new float[32][3];
        ByteBuffer vbb;
        FloatBuffer vBuf;

        vbb = ByteBuffer.allocateDirect(v.length * v[0].length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vBuf = vbb.asFloatBuffer();

//        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
//         FloatBuffer textureBuffer;
//        textureBuffer = getFloatBufferFromTextureArray(texture);
//        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
//
//        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        for (angleA = -90.0f; angleA <90.0f; angleA += step) {
            int	n = 0;

            r1 = (float)Math.cos(angleA * Math.PI / 180.0);
            r2 = (float)Math.cos((angleA + step) * Math.PI / 180.0);
            h1 = (float)Math.sin(angleA * Math.PI / 180.0);
            h2 = (float)Math.sin((angleA + step) * Math.PI / 180.0);

            // Fixed latitude, 360 degrees rotation to traverse a weft
            for (angleB = 0.0f; angleB <= 360.0f; angleB += step) {

                cos = (float)Math.cos(angleB * Math.PI / 180.0);
                sin = -(float)Math.sin(angleB * Math.PI / 180.0);

                v[n][0] = (r2 * cos);
                v[n][1] = (h2);
                v[n][2] = (r2 * sin);
                v[n + 1][0] = (r1 * cos);
                v[n + 1][1] = (h1);
                v[n + 1][2] = (r1 * sin);

                vBuf.put(v[n]);
                vBuf.put(v[n + 1]);

                n += 2;

                if(n>31){
                    vBuf.position(0);

                    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
                    gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
                    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);

                    n = 0;
                    angleB -= step;
                }

            }
            vBuf.position(0);

            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
            gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
    }

    private final int[] mTextures = new int[1];

    public void loadGLTexture(final GL10 gl, final Context context, final int texture) {
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), texture);

        // Generate one texture pointer, and bind it to the texture array.
        gl.glGenTextures(1, this.mTextures, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, this.mTextures[0]);

        // Create nearest filtered texture.
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        // Use Android GLUtils to specify a two-dimensional texture image from our bitmap.
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        // Tidy up.
        bitmap.recycle();
    }
}
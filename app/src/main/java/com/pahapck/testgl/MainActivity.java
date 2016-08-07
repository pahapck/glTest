package com.pahapck.testgl;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private OpenGLView mOpenGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Go to the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Set screen

        setContentView(R.layout.activity_main);

        ViewPager  glPager = (ViewPager)findViewById(R.id.pager);
        GLPagerAdapter  glPagerAdapter = new GLPagerAdapter(this);
        glPager.setAdapter(glPagerAdapter);
        glPager.setOffscreenPageLimit(0);

//        mOpenGLView = new OpenGLView(this, R.drawable.image2);
//        setContentView(mOpenGLView);
    }
}
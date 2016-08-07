package com.pahapck.testgl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * Created by Rio on 2016-03-14.
 */
public class GLPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int[] idArray = {R.drawable.image0,R.drawable.image2,
            R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image9,
            R.drawable.image10, R.drawable.image11};


    public GLPagerAdapter(Context context) {
        super();
        this.mContext = context;
    }

    //페이지 갯수를 1000개로 초기화
    @Override
    public int getCount() {
        return idArray.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        final  int id = idArray[position];

        LinearLayout mainLayout = new LinearLayout(mContext);
        OpenGLView mOpenGLView = new OpenGLView(mContext, id, false);

        mainLayout.addView(mOpenGLView);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewDetailActivity.class);
                intent.putExtra("id", id);
                ((Activity)mContext).startActivity(intent);
            }
        });
        container.addView(mainLayout, 0);
        return mainLayout;
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
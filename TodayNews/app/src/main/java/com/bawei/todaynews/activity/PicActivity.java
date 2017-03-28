package com.bawei.todaynews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.bawei.todaynews.R;
import com.bawei.todaynews.adapter.SimpleViewPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2017/3/21.
 */
public class PicActivity extends Activity {


    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_item);
        initView();
        // if (Math.abs(e1.getRawX() - e2.getRawX()) > 250) {
// // System.out.println("水平方向移动距离过大");
// return true;
// }
// System.out.println("手指移动的太慢了");
// 手势向下 down
//在此处控制关闭
// 手势向上 up
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // if (Math.abs(e1.getRawX() - e2.getRawX()) > 250) {
                // // System.out.println("水平方向移动距离过大");
                // return true;
                // }
                if (Math.abs(velocityY) < 100) {
                    // System.out.println("手指移动的太慢了");
                    return true;
                }

                // 手势向下 down
                if ((e2.getRawY() - e1.getRawY()) > 200) {
                    finish();//在此处控制关闭
                    return true;
                }
                // 手势向上 up
                if ((e1.getRawY() - e2.getRawY()) > 200) {
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }

        });

    }

    private void initView() {
        ViewPager viewpager= (ViewPager) findViewById(R.id.pic_viewpager);
        Intent intent = getIntent();
        ArrayList<String> imgs = intent.getStringArrayListExtra("imgs");
        ArrayList<View> list=new ArrayList<View>();
        for(int i=0;i<imgs.size();i++){
            PhotoView photoView = new PhotoView(PicActivity.this);
            Picasso.with(PicActivity.this).load(imgs.get(i)).error(R.mipmap.icon).placeholder(R.mipmap.icon).into(photoView);
            list.add(photoView);

        }
        SimpleViewPagerAdapter adapter = new SimpleViewPagerAdapter(list);
        viewpager.setAdapter(adapter);


    }


    //2.让手势识别器 工作起来
//当activity被触摸的时候调用的方法.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }



}

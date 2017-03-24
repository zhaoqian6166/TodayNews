package com.bawei.todaynews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bawei.todaynews.R;
import com.bawei.todaynews.adapter.SimpleViewPagerAdapter;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2017/3/21.
 */
public class PicActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_item);
        initView();
    }

    private void initView() {
        ViewPager viewpager= (ViewPager) findViewById(R.id.pic_viewpager);
        Intent intent = getIntent();
        ArrayList<String> imgs = intent.getStringArrayListExtra("imgs");
        ArrayList<View> list=new ArrayList<View>();
        for(int i=0;i<imgs.size();i++){
         /*   View view = View.inflate(PicActivity.this, R.layout.detail_photoview, null);
            PhotoView pv= (PhotoView) view.findViewById(R.id.detail_pv);*/
            PhotoView photoView = new PhotoView(PicActivity.this);
            Picasso.with(PicActivity.this).load(imgs.get(i)).error(R.mipmap.icon).placeholder(R.mipmap.icon).into(photoView);
            list.add(photoView);

        }
        SimpleViewPagerAdapter adapter = new SimpleViewPagerAdapter(list);
        viewpager.setAdapter(adapter);

    }
}

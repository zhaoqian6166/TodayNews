package com.bawei.todaynews.sunVideoFragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.todaynews.R;
import com.bawei.todaynews.adapter.VideopagerAdapter;
import com.bawei.todaynews.adapter.ViewpagerAdapter;
import com.bawei.todaynews.shouyeFragment.Shouye_tuijian;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/10.
 */
public class SunVideo extends Fragment {

    private View view;
    private ViewPager viewpager;
    private VideopagerAdapter adapter;
    private MagicIndicator magicIndicator;
    private ArrayList<String> list_string;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.video_f,null);
        initView();
        return view;
    }

    private void initView() {
    //    tab = (TabLayout) view.findViewById(R.id.f1_table_title);
        magicIndicator = (MagicIndicator) view.findViewById(R.id.magic_indicator);
        final CommonNavigator commonNavigator = new CommonNavigator(getContext());
        viewpager = (ViewPager) view.findViewById(R.id.video_viewpager);
        //设置选项卡的数据
        list_string = new ArrayList<String>();
        list_string.add("热点");
       list_string.add("娱乐");
        list_string.add("搞笑");
        list_string.add("精品");
        list_string.add("小品");
        list_string.add("爆笑");
        list_string.add("乐趣");
        list_string.add("生活");

        list_string.add("Hight");
        list_string.add("体育");
        list_string.add("时尚");

        ArrayList<Fragment> list_frag=new ArrayList<Fragment>();
        Video_tuijian tuijian = new Video_tuijian();
        Video_yule video_yule = new Video_yule();
        Video_gaoxiao video_gaoxiao = new Video_gaoxiao();
        Video_jinpin video_jinpin = new Video_jinpin();

        Video_tuijian tuijian2 = new Video_tuijian();
        Video_yule video_yule2 = new Video_yule();
        Video_gaoxiao video_gaoxiao2 = new Video_gaoxiao();
        Video_jinpin video_jinpin2 = new Video_jinpin();

        Video_yule video_yule3 = new Video_yule();
        Video_gaoxiao video_gaoxiao3 = new Video_gaoxiao();
        Video_jinpin video_jinpin3 = new Video_jinpin();

        list_frag.add(tuijian);
        list_frag.add(video_yule);
        list_frag.add(video_gaoxiao);
        list_frag.add(video_jinpin);

        list_frag.add(tuijian2);
        list_frag.add(video_yule2);
        list_frag.add(video_gaoxiao2);
        list_frag.add(video_jinpin2);

        list_frag.add(video_yule3);
        list_frag.add(video_gaoxiao3);
        list_frag.add(video_jinpin3);
        //得到适配器  设置适配器
        adapter = new VideopagerAdapter(getChildFragmentManager(), list_frag);
        viewpager.setAdapter(adapter);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return list_string == null ? 0 : list_string.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setTextSize(30);
                //clipPagerTitleView.setW

                clipPagerTitleView.setText(list_string.get(index));
                clipPagerTitleView.setTextColor(Color.BLACK);
                clipPagerTitleView.setClipColor(Color.RED);

                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewpager.setCurrentItem(index);
                    }
                });

                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;    // 没有指示器，因为title的指示作用已经很明显了
            }
        });
        magicIndicator.setNavigator(commonNavigator);
//给tab设置适配器
      //  tab.setTabsFromPagerAdapter(this.adapter);

       /* //viewpager的监听（这个监听是为tablayout专门设计的）
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        //tablayout的监听
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //得到当前的tab的id，对应的viewpager也显示对应的页
                int position = tab.getPosition();
                viewpager.setCurrentItem(position);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });

        viewpager.setCurrentItem(1);
    }
}

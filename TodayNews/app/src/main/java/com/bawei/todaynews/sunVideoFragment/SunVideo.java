package com.bawei.todaynews.sunVideoFragment;

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

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/10.
 */
public class SunVideo extends Fragment {

    private View view;
    private TabLayout tab;
    private ViewPager viewpager;
    private VideopagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shouye_f, null);
        initView();
        return view;
    }

    private void initView() {
        tab = (TabLayout) view.findViewById(R.id.f1_table_title);
        viewpager = (ViewPager) view.findViewById(R.id.f1_viewpager);
        //设置选项卡的数据
        ArrayList<String> list_String = new ArrayList<String>();
        list_String.add("热点");
       list_String.add("娱乐");
        list_String.add("搞笑");
        list_String.add("精品");
        //设置选项卡的内容
        for (int i = 0; i < list_String.size(); i++) {
            tab.addTab(tab.newTab().setText(list_String.get(i)));
        }
        ArrayList<Fragment> list_frag=new ArrayList<Fragment>();
        Video_tuijian tuijian = new Video_tuijian();
        Video_yule video_yule = new Video_yule();
        Video_gaoxiao video_gaoxiao = new Video_gaoxiao();
        Video_jinpin video_jinpin = new Video_jinpin();
        list_frag.add(tuijian);
        list_frag.add(video_yule);
        list_frag.add(video_gaoxiao);
        list_frag.add(video_jinpin);
        //得到适配器  设置适配器
       /* adapter = new ViewpagerAdapter(getChildFragmentManager(),list_String,list_frag);
        viewpager.setAdapter(adapter);*/
        adapter = new VideopagerAdapter(getChildFragmentManager(), list_String, list_frag);
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);
//给tab设置适配器
        tab.setTabsFromPagerAdapter(this.adapter);
       /* for (int i = 0; i < list_String.size(); i++){
            TabLayout.Tab tl = this.tab.getTabAt(i);
            if (tl != null) {
                tl.setCustomView(adapter.getabView(i));
                if (tl.getCustomView() != null) {
                    View tabView = (View) tl.getCustomView().getParent();
                    tabView.setTag(i);
                    tabView.setOnClickListener(mTabOnClickListener);
                }
            }
            //  TabLayout.Tab tab = tab.getTabAt(i);
        }*/
        //viewpager的监听（这个监听是为tablayout专门设计的）
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
        });
    }
}

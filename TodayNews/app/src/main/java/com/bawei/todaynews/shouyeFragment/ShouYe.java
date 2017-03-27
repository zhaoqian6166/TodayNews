package com.bawei.todaynews.shouyeFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bawei.todaynews.R;
import com.bawei.todaynews.adapter.ViewpagerAdapter;
import com.bawei.todaynews.bean.MyChannel;
import com.trs.channellib.channel.channel.helper.ChannelDataHelepr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */
public class ShouYe extends Fragment implements ChannelDataHelepr.ChannelDataRefreshListenter{

    private View view;
    private ViewPager viewpager;
    ChannelDataHelepr<MyChannel> dataHelepr;
    private ImageView img;
    private ArrayList<MyChannel> ch_list;
    private int needShowPosition=-1;
    private ViewpagerAdapter adapter;
    ArrayList<MyChannel> myChannels;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shouye_f, null);
        initView();
        return view;
    }

    private void initView() {
        TabLayout tab = (TabLayout) view.findViewById(R.id.f1_table_title);
        viewpager = (ViewPager) view.findViewById(R.id.f1_viewpager);
        img = (ImageView) view.findViewById(R.id.shouye_add);
        LinearLayout ll= (LinearLayout) view.findViewById(R.id.ll);
        img.setAlpha(200);
        //设置选项卡的数据
        ArrayList<String> list_String = new ArrayList<String>();
        list_String.add("推荐");
       list_String.add("热点");
        list_String.add("社会");
        list_String.add("正能量");
        list_String.add("图片");
        list_String.add("趣图");
        list_String.add("段子");
        //设置选项卡的内容
        for (int i = 0; i < list_String.size(); i++) {
            tab.addTab(tab.newTab().setText(list_String.get(i)));
        }
        ArrayList<Fragment> list_frag=new ArrayList<Fragment>();
        Shouye_tuijian shouye_tuijian = new Shouye_tuijian();
        Shouye_hot shouye_hot = new Shouye_hot();
        Shouye_shehui shouye_shehui = new Shouye_shehui();
        Shouye_zhengnengliang shouye_zhengnengliang = new Shouye_zhengnengliang();
        Shouye_tupian shouye_tupian = new Shouye_tupian();
        Shouye_Gift shouye_gift = new Shouye_Gift();
        Shouye_duanzi shouye_duanzi = new Shouye_duanzi();
        list_frag.add(shouye_tuijian);
        list_frag.add(shouye_hot);
        list_frag.add(shouye_shehui);
        list_frag.add(shouye_zhengnengliang);
        list_frag.add(shouye_tupian);
        list_frag.add(shouye_gift);
        list_frag.add(shouye_duanzi);
        //实例化ChannelDataHelepr对象  第一个参数是上下文，第二个参数是响应的频道管理器的监听，第三个是
        //要将管理器显示在哪个控件下方
        dataHelepr = new ChannelDataHelepr(getContext(), this, view.findViewById(R.id.ll));
        dataHelepr.setSwitchView(img);//触发频道管理的控件
        //创建频道管理对象的集合
        ch_list = new ArrayList<MyChannel>();
        // 标题  类型  是否是固定频道  是否订阅
        ch_list.add(new MyChannel("推荐",0,1,1,shouye_tuijian));
        ch_list.add(new MyChannel("热点",0,1,1,shouye_hot));
        ch_list.add(new MyChannel("社会",0,0,1,shouye_shehui));
        ch_list.add(new MyChannel("正能量",0,0,1,shouye_zhengnengliang));
        ch_list.add(new MyChannel("图片",0,0,1,shouye_tupian));
        ch_list.add(new MyChannel("趣图",0,0,1,shouye_gift));
        ch_list.add(new MyChannel("段子",0,0,1,shouye_duanzi));
        myChannels = new ArrayList<MyChannel>();

        //得到适配器  设置适配器
      //  adapter = new ViewpagerAdapter(getChildFragmentManager(),list_String,list_frag);
        adapter=new ViewpagerAdapter(getChildFragmentManager(),myChannels);
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);
//给tab设置适配器
       // tab.setTabsFromPagerAdapter(adapter);
        loadData();
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

    @Override
    public void updateData() {
        loadData();
    }

    @Override
    public void onChannelSeleted(boolean update, int posisiton) {
        if(!update) {
            //如果没有更新  那么viewpager显示的是posisiton位置的数据
            viewpager.setCurrentItem(posisiton);
        }else {
            //如果更新数据了  那么默认显示的位置为posisiton，用needShowPosition接出来
            needShowPosition=posisiton;
        }
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

               /* String data = getFromRaw();
                List<MyChannel> alldata = GsonUtil.jsonToBeanList(data, MyChannel.class);*/
                //过滤数据，如果有新的频道会自动订阅并保存到数据库。
                final List<MyChannel> showChannels = dataHelepr.getShowChannels(ch_list);
                //runOnUiThread()是属于Activity的方法
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myChannels.clear();
                        myChannels.addAll(showChannels);
                        adapter.notifyDataSetChanged();
                        if(needShowPosition!=-1){
                            viewpager.setCurrentItem(needShowPosition);
                            needShowPosition=-1;
                        }
                    }
                });

            }
        }).start();
    }
}

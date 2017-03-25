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

import com.bawei.todaynews.R;
import com.bawei.todaynews.adapter.ViewpagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/10.
 */
public class ShouYe extends Fragment {

    private View view;
    private ViewPager viewpager;
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
        ImageView img= (ImageView) view.findViewById(R.id.shouye_add);
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
      /*   list_String.add("图片");

        list_String.add("体育");*/
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
        //得到适配器  设置适配器
        ViewpagerAdapter adapter = new ViewpagerAdapter(getChildFragmentManager(),list_String,list_frag);
        viewpager.setAdapter(adapter);
        tab.setupWithViewPager(viewpager);
//给tab设置适配器
        tab.setTabsFromPagerAdapter(adapter);
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
    //网络请求
}

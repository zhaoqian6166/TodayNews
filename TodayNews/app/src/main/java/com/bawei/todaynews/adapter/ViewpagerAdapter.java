package com.bawei.todaynews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.todaynews.bean.MyChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10.
 */
public class ViewpagerAdapter extends FragmentPagerAdapter {
  //  private ArrayList<String> list_String;
  //  private ArrayList<Fragment> list_frag;
    //新的
    private ArrayList<MyChannel> list;
    int id=1;
    Map<String,Integer> IdsMap=new HashMap<>();
    List<String> preIds=new ArrayList<>();
    int positions=0;


    public ViewpagerAdapter(FragmentManager fm, ArrayList<MyChannel> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        Log.e("频道getItem(int position)",position+"");
      //  i=position;
        return list.get(position).fragment;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).title;
    }

    @Override
    public long getItemId(int position) {
        Log.e("测试频道",IdsMap.get(getPageTitle(position))+"==");
       positions=position;
        return IdsMap.get(getPageTitle(position));
    }
    @Override
    public int getItemPosition(Object object) {
       // Log.e("频道管理测试",positions+"接出来的");
        String title = list.get(positions).title;
        int preId = preIds.indexOf(title);
        int newId=-1;
        int i=0;
        int size=getCount();
        for(;i<size;i++){
            if(getPageTitle(i).equals(title)){
                newId=i;
                break;
            }
        }
        if(newId!=-1&&newId==preId){
            return POSITION_UNCHANGED;
        }
        if(newId!=-1){
            return newId;
        }
        return POSITION_NONE;
    }
    //更新
    @Override
    public void notifyDataSetChanged() {
        //为每一个频道生成唯一的id 用map集合来保存  频道的名称与id对应，使用
        //一个list来保存之前的position顺序，记得notifyDataSetChanged中初始化，由于
        //list保存的是之前的position ，所以需要在完成更新后再添加
        for(MyChannel info:list){
            if(!IdsMap.containsKey(info.title)){
                IdsMap.put(info.title,id++);
            }
        }
        super.notifyDataSetChanged();
        preIds.clear();
        int size=getCount();
        for(int i=0;i<size;i++){
            preIds.add((String) getPageTitle(i));
        }
    }
}

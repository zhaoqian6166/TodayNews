package com.bawei.todaynews.shouyeFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bawei.todaynews.R;

import java.util.ArrayList;

import xlistview.example.administrator.myxlistviewlibrary.XListView;

/**
 * Created by Administrator on 2017/3/14.
 */
public class Shouye_tupian extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f_shouye_tuijian, null);
        initView();
        return view;
    }

    private void initView() {
        XListView listView = (XListView) view.findViewById(R.id.tuijian_listView);
        ArrayList<String> list=new ArrayList<String>();
        for (int i=0;i<50;i++){
            list.add("条目："+i);

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

    }
}

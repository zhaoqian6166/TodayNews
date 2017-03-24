package com.bawei.todaynews.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.todaynews.R;
import com.bawei.todaynews.activity.DetailActivity;
import com.bawei.todaynews.adapter.LoveListAdapter;
import com.bawei.todaynews.bean.LoveBean;
import com.bawei.todaynews.sqlite.MySqlite;
import com.bawei.todaynews.sqlite.SqUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/14.
 */
public class Colect extends Fragment {

    private View view;
    private LoveListAdapter adapter;
    private ListView listview;
    private TextView text;
    private ArrayList<LoveBean> loveBeen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.colect, null);
        listview = (ListView) view.findViewById(R.id.love_listview);
        text = (TextView) view.findViewById(R.id.colect_no);
        return view;
    }

    private void initView() {
        //二级列表展示  段子  动态图

        MySqlite mySqlite = new MySqlite(getContext());
        SQLiteDatabase db = mySqlite.getWritableDatabase();
        SqUtil sqUtil = new SqUtil(db);
        loveBeen = sqUtil.selectAll();
        Log.i("测试查询到的数据", loveBeen.size()+"--");
        if (loveBeen.size()==0){
            text.setVisibility(View.VISIBLE);
           // Toast.makeText(getContext(), "您还没有任何收藏，快去收藏吧", Toast.LENGTH_SHORT).show();
        }else{
            text.setVisibility(View.GONE);
            //如果结合不为空的时候  就设置适配器
          /*  if (adapter==null){

            }else{
                adapter.notifyDataSetChanged();
            }*/
            adapter = new LoveListAdapter(loveBeen, getContext(), sqUtil);
            listview.setAdapter(adapter);
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击后跳转到详情页面
                Intent intent=new Intent(getContext(), DetailActivity.class);
             //   Log.i("段子跳转",)
                intent.putExtra("web", loveBeen.get(position).url);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onHiddenChanged(boolean hidden) {

        super.onHiddenChanged(hidden);
        if (!hidden){
            initView();
        }

    }
}

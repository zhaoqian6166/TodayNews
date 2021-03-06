package com.bawei.todaynews.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bawei.todaynews.activity.DetailActivity;
import com.bawei.todaynews.adapter.GiftListAdapter;
import com.bawei.todaynews.adapter.PicListViewAdapter;
import com.bawei.todaynews.bean.News;
import com.bawei.todaynews.bean.SY_tuijian_Info;
import com.bawei.todaynews.sqlite.SqUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import xlistview.example.administrator.myxlistviewlibrary.XListView;

/**
 * Created by Administrator on 2017/3/16.
 */
public class GiftGetDataUtil {
    private Gson gson;
    private Context context;
    private String path;
    private ArrayList<News> list_news;
    private XListView listView;
    private SqUtil sqUtil;


    public GiftGetDataUtil(Gson gson, Context context, String path, ArrayList<News> list_news, XListView listView, SqUtil sqUtil) {
        this.gson = gson;
        this.context = context;
        this.path = path;
        this.list_news = list_news;
        this.listView = listView;
        this.sqUtil = sqUtil;
    }

    //网络请求数据
    public void getData() {
        RequestParams params = new RequestParams(path);
        params.setCacheMaxAge(1000 * 60);//设置数据缓存个的最大时常
        x.http().get(params, new Callback.CacheCallback<String>() {

            private String result = null;
            @Override
            public boolean onCache(String result) {
                this.result = result;
                //默认false不走缓存 true 走缓存 过期之后就不走缓存
                Toast.makeText(context, "走缓存", Toast.LENGTH_SHORT).show();
//得到解析数据
                ArrayList<SY_tuijian_Info.Data_tuijian> data = getDataAll(result);

                for (int i = 0; i < data.size(); i++) {
                    String json = data.get(i).content;
                    News news = gson.fromJson(json, News.class);
                    if (news.large_image.url_list.size()!=0){
                        list_news.add(news);
                    }
                }
               // PicListViewAdapter adapter = new PicListViewAdapter(list_news, context);
                GiftListAdapter adapter = new GiftListAdapter(list_news, context,sqUtil);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("-----","点击趣图");
                        Intent intent=new Intent(context, DetailActivity.class);
                        Log.i("趣图---",list_news.get(position).share_url);
                        intent.putExtra("web",list_news.get(position).share_url);
                        context.startActivity(intent);
                    }
                });
                return true;
            }

            @Override
            public void onSuccess(String result) {
                //得到解析数据
                ArrayList<SY_tuijian_Info.Data_tuijian> data = getDataAll(result);
                String head = data.get(0).content;//头布局json
                if (list_news==null){
                    list_news = new ArrayList<News>();//每个条目的对象集合
                }

                //每次得到的数据，第一条都为头布局
                for (int i = 0; i < data.size(); i++) {
                    String json = data.get(i).content;
                    News news = gson.fromJson(json, News.class);
                    if (news.large_image.url_list.size()!=0){
                        list_news.add(news);
                    }
                }
                GiftListAdapter adapter = new GiftListAdapter(list_news, context,sqUtil);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(context, "点击了", Toast.LENGTH_SHORT).show();
                        Log.i("-----","点击趣图");
                        Intent intent=new Intent(context, DetailActivity.class);
                        Log.i("趣图---",list_news.get(position).share_url);
                        intent.putExtra("web",list_news.get(position).share_url);
                        context.startActivity(intent);
                    }
                });

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    ArrayList<SY_tuijian_Info.Data_tuijian> getDataAll(String result){
        if (gson==null){
            gson = new Gson();
        }
        SY_tuijian_Info info = gson.fromJson(result, SY_tuijian_Info.class);
        ArrayList<SY_tuijian_Info.Data_tuijian> data = info.data;

        return data;

    }

}

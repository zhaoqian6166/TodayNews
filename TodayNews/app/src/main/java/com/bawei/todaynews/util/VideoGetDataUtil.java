package com.bawei.todaynews.util;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.bawei.todaynews.adapter.Video_ListViewAdapter;
import com.bawei.todaynews.bean.SY_tuijian_Info;
import com.bawei.todaynews.bean.VideoBean;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import xlistview.example.administrator.myxlistviewlibrary.XListView;

/**
 * Created by Administrator on 2017/3/15.
 */
public class VideoGetDataUtil {
    private static Gson gson;
    private String path;
    private Context context;
    private XListView listView;
   private ArrayList<VideoBean> list_video;
    private int index;
    private ArrayList<VideoBean> vb;
    private static ArrayList<VideoBean> vb1;

    public VideoGetDataUtil(Gson gson, String path, Context context, XListView listView,ArrayList<VideoBean> list_video,int index) {
        this.gson = gson;
        this.path = path;
        this.context = context;
        this.listView = listView;
        this.list_video = list_video;
        this.index=index;
        Log.i("适配器中","集合长度"+list_video.size());
    }

    public void getData() {
      //  ArrayList<>
       // String path = "http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/10-10.html";
        RequestParams params = new RequestParams(path);
        params.setCacheMaxAge(1000 * 60);//设置数据缓存个的最大时常
        x.http().get(params, new Callback.CacheCallback<String>() {
            private ArrayList<VideoBean> list;
            private Video_ListViewAdapter adapter;
            private String result = null;

            @Override
            public boolean onCache(String result) {
                this.result = result;
                list = parseData(result);
                if (index==0){
                    //如果是刷新  用当前解析到的集合
                    adapter = new Video_ListViewAdapter(list, context);
                }else{
                    //
                    for (int i=0;i<list.size();i++){
                        list_video.add(list.get(i));
                    }
                    adapter = new Video_ListViewAdapter(list_video, context);
                }
                //   Video_ListViewAdapter adapter = new Video_ListViewAdapter(list_video, context);
                listView.setAdapter(adapter);

                return true;
            }

            @Override
            public void onSuccess(String result) {
              /*  Log.i("成功","-----------------ok------------");
                ArrayList<VideoBean> list = gson.fromJson(result, new TypeToken<ArrayList<VideoBean>>(){}.getType());
          Log.i("list",list.size()+"");*/
                //解析数据
                list = parseData(result);
//   Log.i("测试是否成功", "成功");
               /* if(index==0){
                    //下拉刷新，直接new一个新的list，填充给适配器就好

                }else{
                    //上拉加载的时候，需要将请求到的数据添加到一个大集合中 将大集合传入到适配器中

                }*/
               if (index==0){
                   //如果是刷新  用当前解析到的集合
                   adapter = new Video_ListViewAdapter(list, context);
               }else{
                   //
                   for (int i=0;i<list.size();i++){
                       list_video.add(list.get(i));
                   }
                   adapter = new Video_ListViewAdapter(list_video, context);
               }
             //   Video_ListViewAdapter adapter = new Video_ListViewAdapter(list_video, context);
                listView.setAdapter(adapter);

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

 /*   //得到所有的解析数据
    ArrayList<SY_tuijian_Info.Data_tuijian> getDataAll(String result) {
        if (gson == null) {
            gson = new Gson();
        }
        SY_tuijian_Info info = gson.fromJson(result, SY_tuijian_Info.class);
        ArrayList<SY_tuijian_Info.Data_tuijian> data = info.data;
        return data;

    }*/

    public static ArrayList<VideoBean> getList(){
        vb1 = new ArrayList<VideoBean>();
        return vb1;

    }

    public  ArrayList<VideoBean> parseData(String json) {
        try {
            ArrayList<VideoBean> list_video=new ArrayList<VideoBean>();
            JSONObject resultObject = new JSONObject(json);
            Iterator<String> keys = resultObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                JSONArray nextArray = resultObject.optJSONArray(next);
                for (int i = 0; i < nextArray.length(); i++) {
                    JSONObject object = nextArray.optJSONObject(i);
                    VideoBean bean = gson.fromJson(object.toString(), VideoBean.class);
                    list_video.add(bean);
                }
            }
            vb1=list_video;
            return list_video;
          //  Log.i("集合", list_video.size() + "----");

        } catch (JSONException e) {
            e.printStackTrace();
        }
         return null;
    }
}

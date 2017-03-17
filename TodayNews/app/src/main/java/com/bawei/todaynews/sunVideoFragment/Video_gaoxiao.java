package com.bawei.todaynews.sunVideoFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.todaynews.R;
import com.bawei.todaynews.bean.News;
import com.bawei.todaynews.bean.VideoBean;
import com.bawei.todaynews.util.VideoGetDataUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import xlistview.example.administrator.myxlistviewlibrary.XListView;

/**
 * Created by Administrator on 2017/3/15.
 */
public class Video_gaoxiao extends Fragment {

    private View view;
    private XListView listView;
    private Gson gson;
    private ArrayList<News> list_news;
    private ArrayList<VideoBean> list_video;
    private VideoGetDataUtil util;
    private String path;
    private int start=0;
    private int end=10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f_shouye_tuijian, null);
        initView();
        return view;

    }

    private void initView() {
        listView = (XListView) view.findViewById(R.id.tuijian_listView);
        list_video = new ArrayList<VideoBean>();
        gson = new Gson();
        /**
         * 3.设置视频/MP3地址、缩略图地址、标题

         JCVideoPlayer jCVideoPlayer = (JCVideoPlayer) findViewById(R.id.videocontroller);
         videoController.setUp("视频/MP3地址","视频/MP3标题");
         videoController.ivThumb.setThumbInCustomProject("视频/MP3缩略图地址");---->得不到方法
         Picasso.with(context).load(list_news.get(position).middle_image.url).into(holder.jp.ivThumb);
         //holder.jp.ivThumb得到ImageView
         4.在包含播放器的Fragment或Activity的onPause()方法中调用JCVideoPlayer.releaseAllVideos();
         注：全屏导致崩溃的请在清单文件中当前activity中加上：

         [html] view plain copy
         在CODE上查看代码片派生到我的代码片

         <activity
         android:name=".activity.MainActivity"
         android:configChanges="orientation"
         android:screenOrientation="portrait"></activity>
         http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/10-10.html
         http://c.3g.163.com/nc/video/list/V9LG4CHOR/n/10-10.html
         */
        path = "http://c.3g.163.com/nc/video/list/V9LG4E6VR/n/"+start+"-"+end+".html";
       // ArrayList<VideoBean> list = VideoGetDataUtil.getList();
        util = new VideoGetDataUtil(gson, path, getContext(), listView,list_video,0);
        util.getData();
        ArrayList<VideoBean> list = VideoGetDataUtil.getList();
        list_video.addAll(list);
        //上拉下拉：
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                //刷新
                listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  下拉刷新 集合清空 请求最新数据
                        //list_video = new ArrayList<VideoBean>();
                        start=0;
                        end=10;
                        path = "http://c.3g.163.com/nc/video/list/V9LG4E6VR/n/"+start+"-"+end+".html";
                        list_video.clear();
                        util = new VideoGetDataUtil(gson, path, getContext(), listView, list_video,0);
                        util.getData();
                        Log.i("下拉","集合长度"+list_video.size());
                        Log.i("下拉",path);
                       // String path_new=" http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/10-10.html";
                        ArrayList<VideoBean> list = VideoGetDataUtil.getList();
                        list_video.addAll(list);//请求道数据之后添加到大集合
                        listView.stopRefresh();
                    }
                },2000);

            }

            @Override
            public void onLoadMore() {
                listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        start+=10;
                        end+=10;
                        // 上拉加载
                   //     util = new VideoGetDataUtil(gson, path, getContext(), listView, list_video);
                        util = new VideoGetDataUtil(gson, path, getContext(), listView,list_video,1);
                        path = "http://c.3g.163.com/nc/video/list/V9LG4E6VR/n/"+start+"-"+end+".html";
                        Log.i("上拉",path);
                        util.getData();
                        ArrayList<VideoBean> list = VideoGetDataUtil.getList();
                        list_video.addAll(list);
                        Log.i("上拉","集合长度"+list_video.size());
                        listView.stopLoadMore();
                    }
                },2000);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }


}

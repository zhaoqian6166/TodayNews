package com.bawei.todaynews.shouyeFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bawei.todaynews.R;
import com.bawei.todaynews.bean.News;
import com.bawei.todaynews.util.NewsgetDataUtil;
import com.bawei.todaynews.util.PicGetDataUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

import xlistview.example.administrator.myxlistviewlibrary.XListView;

/**
 * Created by Administrator on 2017/3/14.
 */
public class Shouye_tupian extends Fragment {

    private View view;
    private ArrayList<News> list_news;
    private Gson gson;
    private XListView listView;
    private PicGetDataUtil util;
    private String path;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f_shouye_tuijian, null);
        initView();
        return view;
    }

    private void initView() {
        listView = (XListView) view.findViewById(R.id.tuijian_listView);
        list_news = new ArrayList<News>();
        gson = new Gson();
        path = "http://lf.snssdk.com/api/news/feed/v51/?category=%E7%BB%84%E5%9B%BE&refer=1&count=20&min_behot_time=1489475253&last_refresh_sub_entrance_interval=87&loc_mode=5&loc_time=1489417430&latitude=37.00125&longitude=110.56358166666665&city=%E5%90%95%E6%A2%81&tt_from=pull&lac=37142&cid=46142&cp=5b8bcc7f9570dq1&iid=8742166056&device_id=35398884506&ac=wifi&channel=update&aid=13&app_name=news_article&version_code=606&version_name=6.0.6&device_platform=android&ab_version=96766%2C111060%2C111305%2C101786%2C112146%2C111546%2C112157%2C101533%2C109464%2C110341%2C109891%2C109776%2C112072%2C106784%2C97142%2C111339%2C101558%2C104322%2C112166%2C109317%2C94043%2C105610%2C112343%2C105825%2C111316%2C108977%2C111798%2C111258%2C111581%2C108487%2C111897%2C110795%2C111418%2C98044%2C105475&ab_client=a2%2Cc2%2Ce1%2Cf1%2Cg2%2Cf7&ab_feature=102749%2C94563&ssmix=a&device_type=vivo+Y31&device_brand=vivo&language=zh&os_api=19&os_version=4.4.2&uuid=864394010201061&openudid=206A8A39620A0000&manifest_version_code=605&resolution=720*1280&dpi=240&update_version_code=6060&_rticket=1489475341772";
        util = new PicGetDataUtil(gson, getContext(), path, list_news, listView);
        util.getData();

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
                        list_news.clear();
                        util = new PicGetDataUtil(gson, getContext(), path, list_news, listView);
                        util.getData();
                        listView.stopRefresh();
                    }
                },2000);
            }

            @Override
            public void onLoadMore() {
                listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 上拉加载
                        util.getData();
                        listView.stopLoadMore();
                    }
                },2000);

            }
        });

      /*  AsyncHttpClient client = new AsyncHttpClient();
        client.get(getContext(), path, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //请求成功之后

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ArrayList<SY_tuijian_Info.Data_tuijian> data = getDataAll(responseString);
                if (list_news ==null){
                    list_news = new ArrayList<News>();//每个条目的对象集合
                }

                //每次得到的数据，第一条都为头布局
                for (int i = 0; i < data.size(); i++) {
                    String json = data.get(i).content;
                    News news = gson.fromJson(json, News.class);
                    if (news.image_list.size()!=0){
                        list_news.add(news);
                    }
                }
                PicListViewAdapter adapter = new PicListViewAdapter(list_news, getContext());
                listView.setAdapter(adapter);
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

    }*/
    }
}

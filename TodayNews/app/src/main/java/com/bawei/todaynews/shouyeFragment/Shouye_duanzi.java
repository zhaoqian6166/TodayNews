package com.bawei.todaynews.shouyeFragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bawei.todaynews.R;
import com.bawei.todaynews.bean.News;
import com.bawei.todaynews.sqlite.MySqlite;
import com.bawei.todaynews.sqlite.SqUtil;
import com.bawei.todaynews.util.DZGetDataUtil;
import com.bawei.todaynews.util.GiftGetDataUtil;
import com.bawei.todaynews.util.NewsgetDataUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

import xlistview.example.administrator.myxlistviewlibrary.XListView;

/**
 * Created by Administrator on 2017/3/14.
 */
public class Shouye_duanzi extends Fragment {

    private View view;
    private XListView listView;
    private DZGetDataUtil util;
    private ArrayList<News> list_views;
    private Gson gson;
    private String path;
    private SqUtil sqUtil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f_shouye_tuijian, null);
        initView();
        return view;
    }

    private void initView() {
        listView = (XListView) view.findViewById(R.id.tuijian_listView);
        path = "http://lf.snssdk.com/api/news/feed/v51/?category=essay_joke&refer=1&count=20&min_behot_time=1489477035&last_refresh_sub_entrance_interval=1489477068&loc_mode=5&loc_time=1489417430&latitude=37.00125&longitude=110.56358166666665&city=%E5%90%95%E6%A2%81&tt_from=pull&lac=37142&cid=46142&cp=5582c57695dccq1&iid=8742166056&device_id=35398884506&ac=wifi&channel=update&aid=13&app_name=news_article&version_code=606&version_name=6.0.6&device_platform=android&ab_version=96766%2C111060%2C112376%2C111305%2C101786%2C112146%2C111546%2C112157%2C101533%2C109464%2C110341%2C109891%2C109776%2C112072%2C106784%2C97142%2C112369%2C111339%2C101558%2C104322%2C112166%2C109317%2C94043%2C105610%2C112343%2C105825%2C111316%2C108977%2C111798%2C111258%2C111581%2C108487%2C111897%2C110795%2C111418%2C98044%2C105475&ab_client=a2%2Cc2%2Ce1%2Cf1%2Cg2%2Cf7&ab_feature=102749%2C94563&ssmix=a&device_type=vivo+Y31&device_brand=vivo&language=zh&os_api=19&os_version=4.4.2&uuid=864394010201061&openudid=206A8A39620A0000&manifest_version_code=605&resolution=720*1280&dpi=240&update_version_code=6060&_rticket=1489477068287";
        gson = new Gson();
        list_views = new ArrayList<>();
      //  SqUtil sqUtil = new SqUtil(getContext());
        MySqlite mySqlite = new MySqlite(getContext());
        SQLiteDatabase db = mySqlite.getWritableDatabase();
        sqUtil = new SqUtil(db);


        util = new DZGetDataUtil(gson, getContext(), path, list_views, listView, sqUtil);
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
                        list_views.clear();
                        util = new DZGetDataUtil(gson, getContext(), path, list_views, listView, sqUtil);
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


    }
}

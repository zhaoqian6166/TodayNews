package com.bawei.todaynews.util;

import android.content.Context;
import android.widget.Toast;

import com.bawei.todaynews.adapter.PicListViewAdapter;
import com.bawei.todaynews.adapter.SY_ListViewAdapter;
import com.bawei.todaynews.bean.News;
import com.bawei.todaynews.bean.SY_tuijian_Info;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import xlistview.example.administrator.myxlistviewlibrary.XListView;

/**
 * Created by Administrator on 2017/3/16.
 */
public class PicGetDataUtil {
    private Gson gson;
    private Context context;
    private String path;
    private ArrayList<News> list_news;
    private XListView listView;

    public PicGetDataUtil(Gson gson, Context context, String path, ArrayList<News> list_news, XListView listView) {
        this.gson = gson;
        this.context = context;
        this.path = path;
        this.list_news = list_news;
        this.listView = listView;
    }

    //网络请求数据
    public void getData() {
       // String path = "http://lf.snssdk.com/api/news/feed/v51/?concern_id=6286225228934679042&refer=1&count=20&min_behot_time=1489474181&last_refresh_sub_entrance_interval=1489474190&loc_mode=5&loc_time=1489084784&latitude=37.00125&longitude=110.56358166666665&city=%E5%90%95%E6%A2%81&tt_from=pull&lac=37142&cid=46142&cp=518fcb7a9d28eq1&iid=8742166056&device_id=35398884506&ac=wifi&channel=update&aid=13&app_name=news_article&version_code=606&version_name=6.0.6&device_platform=android&ab_version=96766%2C111060%2C111305%2C101786%2C112146%2C111546%2C112157%2C101533%2C109464%2C110341%2C109891%2C109776%2C112072%2C106784%2C97142%2C111339%2C101558%2C104322%2C112166%2C109317%2C94043%2C105610%2C112343%2C105825%2C111316%2C108977%2C111798%2C111258%2C111581%2C108487%2C111897%2C110795%2C111418%2C98044%2C105475&ab_client=a2%2Cc2%2Ce1%2Cf1%2Cg2%2Cf7&ab_feature=102749%2C94563&ssmix=a&device_type=vivo+Y31&device_brand=vivo&language=zh&os_api=19&os_version=4.4.2&uuid=864394010201061&openudid=206A8A39620A0000&manifest_version_code=605&resolution=720*1280&dpi=240&update_version_code=6060&_rticket=1489474190803";
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
                    if (news.image_list.size()!=0){
                        list_news.add(news);
                    }
                }
                PicListViewAdapter adapter = new PicListViewAdapter(list_news, context);
                listView.setAdapter(adapter);
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
                    if (news.image_list.size()!=0){
                        list_news.add(news);
                    }
                }
                PicListViewAdapter adapter = new PicListViewAdapter(list_news, context);
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
    ArrayList<SY_tuijian_Info.Data_tuijian> getDataAll(String result){
        if (gson==null){
            gson = new Gson();
        }
        SY_tuijian_Info info = gson.fromJson(result, SY_tuijian_Info.class);
        ArrayList<SY_tuijian_Info.Data_tuijian> data = info.data;

        return data;

    }

}

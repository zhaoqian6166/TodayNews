package com.bawei.todaynews.util;

import android.util.Log;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.common.task.Priority;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.security.PublicKey;

/**
 * Created by Administrator on 2017/3/10.
 */
public class NetData {
    public static void getDataFromNet(String url){
        // String json=null;
        RequestParams params = new RequestParams(url);//实例化出一个请求参数的对象
        params.setCacheMaxAge(1000 * 60);//设置数据缓存个的最大时常
//调用请求数据且缓存的方法
        x.http().get(params, new Callback.CacheCallback<String>() {
            public String josn;

            @Override
            public boolean onCache(String result) {
                result = result;
                //默认false不走缓存 true 走缓存 过期之后就不走缓存
               // Toast.makeText(MainActivity.this, "走缓存", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    josn = result;
                }
                //得到数据之后返回数据
                Log.i("aaa", result);

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
}

package com.bawei.todaynews.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bawei.todaynews.R;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Administrator on 2017/3/23.
 */
public class DetailActivity extends SwipeBackActivity {
    Handler handler=new Handler();
    private WebView webView;
  //  private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        SwipeBackLayout mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setScrimColor(Color.TRANSPARENT);
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        //得到跳转传来的值
        Intent intent = getIntent();
        String web = intent.getStringExtra("web");
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl(web);
        //1. 打开网页时不调用系统浏览器， 而是在本WebView中显示：
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

       /* gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // if (Math.abs(e1.getRawX() - e2.getRawX()) > 250) {
                // // System.out.println("水平方向移动距离过大");
                // return true;
                // }
                if (Math.abs(velocityY) < 100) {
                    // System.out.println("手指移动的太慢了");
                    return true;
                }

                // 手势向下 down
                if ((e2.getRawY() - e1.getRawY()) > 200) {
                    finish();//在此处控制关闭
                    return true;
                }
                // 手势向上 up
                if ((e1.getRawY() - e2.getRawY()) > 200) {
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }

        });*/

   /*   //  2. 通过java代码调用javascript


        webView.addJavascriptInterface(new Object() {
            public void clickOnAndroid() {
                handler.post(new Runnable() {
                    public void run() {
                        webView.loadUrl("javascript:wave()");
                    }
                });
            }
        }, "demo");*/
        //4. 打开页面时， 自适应屏幕：
        WebSettings webSettings =   webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);  //设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
       /* webView.addJavascriptInterface(new Object() {
            public void clickOnAndroid() {
                handler.post(new Runnable() {
                    public void run() {
                        webView.loadUrl("javascript:wave()");
                    }
                });
            }
        }, "demo");*/
    }
    //   3. 按返回键时， 不退出程序而是返回上一浏览页面：

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
//关闭这个Activity （写完上面滑动删除Activity也就基本实现了，但是会出现黑色背景闪过）
    @Override
    public void onBackPressed() {
        scrollToFinishActivity();
    }

  /*  @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }*/


}

package com.bawei.todaynews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bawei.todaynews.R;

/**
 * Created by Administrator on 2017/3/23.
 */
public class DetailActivity extends Activity{
    Handler handler=new Handler();
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
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
        webView.addJavascriptInterface(new Object() {
            public void clickOnAndroid() {
                handler.post(new Runnable() {
                    public void run() {
                        webView.loadUrl("javascript:wave()");
                    }
                });
            }
        }, "demo");
    }
    //   3. 按返回键时， 不退出程序而是返回上一浏览页面：

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





}

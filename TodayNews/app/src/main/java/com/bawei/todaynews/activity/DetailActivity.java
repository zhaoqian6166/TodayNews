package com.bawei.todaynews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.bawei.todaynews.R;

/**
 * Created by Administrator on 2017/3/23.
 */
public class DetailActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        Intent intent = getIntent();
        String web = intent.getStringExtra("web");
        WebView webView= (WebView) findViewById(R.id.webview);
        webView.loadUrl(web);
    }
}

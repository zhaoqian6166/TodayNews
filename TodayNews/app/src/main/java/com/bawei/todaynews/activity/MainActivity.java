package com.bawei.todaynews.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.todaynews.R;
import com.bawei.todaynews.fragment.Colect;
import com.bawei.todaynews.shouyeFragment.ShouYe;
import com.bawei.todaynews.sunVideoFragment.SunVideo;
import com.bawei.todaynews.util.ThemeManager;
import com.bawei.todaynews.util.XCRoundImageView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;


import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.ContactsPage;
import cn.smssdk.gui.RegisterPage;


//@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity implements View.OnClickListener, ThemeManager.OnThemeChangeListener {
    private FragmentManager manager;
    private ShouYe shouYe;
    private SunVideo sunVideo;
    private LinearLayout login_layout;
    private LinearLayout unlogin_layout;
    private XCRoundImageView user_img;
    private TextView user_name;
    private SlidingMenu slidingMenu;
    private ImageView phone;
    private TextView mode_text;
    private TextView destroy;
    private LinearLayout theme;
    private ActionBar supportActionBar;
    private LinearLayout sliding_main;
    private ImageView mode_img;
    private RadioButton sliding_mode;
    private Colect colect;
    /*
    @ViewInject(R.id.frame_layout)
    FrameLayout frameLayout;
    @ViewInject(R.id.main_rg)
    RadioGroup main_rg;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //   x.view().inject(this);//初始化
        SMSSDK.initSDK(this, "1c0e2609bb4aa", "a941cdb1b2e606adc23902d0f08b60cf");
        initView();

    }

    private void initView() {
        ThemeManager.registerThemeChangeListener(this);
        supportActionBar = getActionBar();
        //找到控件
        theme = (LinearLayout) findViewById(R.id.theme);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        RadioGroup rg = (RadioGroup) findViewById(R.id.main_rg);
        RadioButton shouye = (RadioButton) findViewById(R.id.main_shouye);
        RadioButton sun = (RadioButton) findViewById(R.id.main_sun);
        RadioButton guanzhu = (RadioButton) findViewById(R.id.main_guanzhu);
        rg.check(R.id.main_shouye);
        shouye.setOnClickListener(this);
        sun.setOnClickListener(this);
        guanzhu.setOnClickListener(this);
        SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        shouYe = new ShouYe();
        sunVideo = new SunVideo();
        colect = new Colect();
        transaction.add(R.id.frame_layout, shouYe, "shouye");
        transaction.add(R.id.frame_layout, sunVideo, "sunVideo");
        transaction.add(R.id.frame_layout, colect, "colect");
        //   transaction.add(R.id.frame_layout,shouYe,"shouye");
        transaction.show(shouYe);
        transaction.hide(sunVideo);
        transaction.hide(colect);

        transaction.commit();

//---------------------设置侧滑-----------------------------
        slidingMenu = new SlidingMenu(this);
//从左边滑出
        slidingMenu.setMode(SlidingMenu.LEFT);
//设置整个屏幕都可以滑出
// slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//设置整个屏幕都不可以滑出菜单
// slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//设置主屏幕滑出的宽度
        slidingMenu.setBehindOffset(200);
        slidingMenu.attachToActivity(MainActivity.this, SlidingMenu.SLIDING_CONTENT);
//设置侧滑的布局页
        slidingMenu.setMenu(R.layout.sliding_main);
        initSlidingView();

    }

    private void initSlidingView() {
        //侧滑页面中的监听
        sliding_main = (LinearLayout) findViewById(R.id.sliding_main);
        login_layout = (LinearLayout) findViewById(R.id.sliding_login);
        unlogin_layout = (LinearLayout) findViewById(R.id.sliding_unlogin);
        user_img = (XCRoundImageView) findViewById(R.id.user_img);
        user_name = (TextView) findViewById(R.id.user_name);
        phone = (ImageView) findViewById(R.id.login_phone);
        TextView text= (TextView) findViewById(R.id.download);//下载


        ImageView login_qq = (ImageView) findViewById(R.id.login_qq);
        ImageView login_sina = (ImageView) findViewById(R.id.login_sina);
        RadioButton sliding_like = (RadioButton) findViewById(R.id.sliding_like);
        RadioButton siliding_history = (RadioButton) findViewById(R.id.siliding_history);
        sliding_mode = (RadioButton) findViewById(R.id.sliding_mode);
        destroy = (TextView) findViewById(R.id.sliding_login_destroy);
        //监听
        login_qq.setOnClickListener(this);
        destroy.setOnClickListener(this);
        phone.setOnClickListener(this);
        sliding_mode.setOnClickListener(this);
        text.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_phone:
                //手机号码登录
                phoneLogin();
                break;
            case R.id.login_qq://点击qq之后跳转到第三方登录
                Toast.makeText(MainActivity.this, "点击QQ", Toast.LENGTH_SHORT).show();
                UMShareAPI.get(MainActivity.this).doOauthVerify(MainActivity.this, SHARE_MEDIA.QQ.toSnsPlatform().mPlatform, umAuthListener);
                break;
            case R.id.sliding_login_destroy:
                UMShareAPI.get(MainActivity.this).deleteOauth(MainActivity.this, SHARE_MEDIA.QQ.toSnsPlatform().mPlatform, umAuthListener);
                break;
            case R.id.sliding_mode:
                ThemeManager.setThemeMode(ThemeManager.getThemeMode() == ThemeManager.ThemeMode.DAY
                        ? ThemeManager.ThemeMode.NIGHT : ThemeManager.ThemeMode.DAY);

                break;
            //底部按钮的监听
            case R.id.main_shouye:
                FragmentTransaction transaction1 = manager.beginTransaction();
                transaction1.show(shouYe);
                transaction1.hide(sunVideo);
                transaction1.hide(colect);
                transaction1.commit();
                break;
            case R.id.main_sun:
                FragmentTransaction transaction2 = manager.beginTransaction();
                transaction2.show(sunVideo);
                transaction2.hide(shouYe);
                transaction2.hide(colect);
                transaction2.commit();
                break;
            case R.id.main_guanzhu:
                FragmentTransaction transaction3 = manager.beginTransaction();
                transaction3.show(colect);
                transaction3.hide(shouYe);
                transaction3.hide(sunVideo);
                transaction3.commit();
                break;
            case R.id.download:
                Intent intent=new Intent(MainActivity.this,DownloadActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void phoneLogin() {
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
// 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    login_layout.setVisibility(View.VISIBLE);
                    unlogin_layout.setVisibility(View.GONE);
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    user_name.setText("+ " + country + phone);

                    // 提交用户信息（此方法可以不调用）
                    //     registerUser(country, phone);
                }
            }
        });
        registerPage.show(MainActivity.this);
        ContactsPage contactsPage = new ContactsPage();
        contactsPage.show(MainActivity.this);
    }

    //授权
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            //
            switch (action) {
                case ACTION_AUTHORIZE://授权登录后执行的操作
                    //获得数据
                    UMShareAPI.get(MainActivity.this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                    unlogin_layout.setVisibility(View.GONE);
                    login_layout.setVisibility(View.VISIBLE);
                    break;
                case ACTION_DELETE://注销
                    Picasso.with(MainActivity.this).load(R.mipmap.icon).into(user_img);
                    user_name.setText("登录");
                    login_layout.setVisibility(View.GONE);
                    unlogin_layout.setVisibility(View.VISIBLE);
                    break;
                case ACTION_GET_PROFILE://获取用户信息
                    //页面切换
                    Picasso.with(MainActivity.this).load(data.get("iconurl")).error(R.mipmap.icon).placeholder(R.mipmap.icon).into(user_img);
                    user_name.setText(data.get("screen_name") + "(" + data.get("gender") + "," + data.get("city") + ")");
                    Log.i("----用户信息---", data.get("name") + ",性别;" + data.get("gender") + ",city" + data.get("city"));
                    break;
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    public void initTheme() {
        sliding_mode.setChecked(sliding_mode.isChecked());
        theme.setBackgroundColor(getResources().getColor(ThemeManager.getCurrentThemeRes(MainActivity.this, R.color.backgroundColor)));
        sliding_main.setBackgroundColor(getResources().getColor(ThemeManager.getCurrentThemeRes(MainActivity.this, R.color.backgroundColor)));
        // 设置标题栏颜色
        if (supportActionBar != null) {
            supportActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(ThemeManager.getCurrentThemeRes(MainActivity.this, R.color.colorPrimary))));
        }
        // 设置状态栏颜色
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(ThemeManager.getCurrentThemeRes(MainActivity.this, R.color.colorPrimary)));
    }
}

    //日夜间模式的切换
    @Override
    public void onThemeChanged() {
        initTheme();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThemeManager.unregisterThemeChangeListener(this);
    }

}

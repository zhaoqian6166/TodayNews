package com.bawei.todaynews.bean;

/**
 * Created by Administrator on 2017/3/24.
 */
public class LoveBean {
    public  int id;
    public int type;
    public String title;
    public String img;
    public String url;

    public LoveBean(int id, int type, String title, String img, String url) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.img = img;
        this.url = url;
    }
}

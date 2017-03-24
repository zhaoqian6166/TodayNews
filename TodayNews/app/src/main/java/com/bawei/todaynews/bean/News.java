package com.bawei.todaynews.bean;

import org.xutils.db.annotation.Table;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/14.
 */
public class News {
    public String comment_count;
    public boolean has_image;
    public boolean has_video;
    public ArrayList<Img> image_list;
    public ArrayList<LargeImg> large_image_list;
    public MiddleImg middle_image;
    public String source;
    public String url;
    public String title;
    public String repin_count;
    public Gift large_image;
    public String screen_name;
    public String content;
    public String share_url;
    public boolean love=false;



    public class Img{
        public String url;
    }
    public class MiddleImg{
        public String url;
    }
    public class LargeImg{
        public String url;
       // public ArrayList<Gift> url_list;
    }
    public class Gift{
        public ArrayList<Gift_list> url_list;

    }
    public class Gift_list{
        public String url;
    }





}

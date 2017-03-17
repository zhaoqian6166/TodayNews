package com.bawei.todaynews.bean;

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


    public class Img{
        public String url;
    }
    public class MiddleImg{
        public String url;
    }
    public class LargeImg{
        public String url;
    }





}

package com.bawei.todaynews.bean;

/**
 * Created by Administrator on 2017/3/15.
 */
public class VideoBean {
    public String videosource;
    public String topicDesc;
    public String cover;
    public String title;
    public int playCount;
    public String mp4_url;
    public int playersize;
   public  String topicImg;
    public Object m3u8Hd_url;
    public String topicName;
    public String m3u8_url;
    public String ptime;
    public VideoTopicBean videoTopic;
    public static class VideoTopicBean {
        /**
         * alias : 电视剧追追追不能停！
         * tname : 追剧小达人
         * ename : T1465961077843
         * tid : T1465961077843
         * topic_icons : http://img2.cache.netease.com/m/newsapp/topic_icons/T1465961077843.png
         */
        public String alias;
        public String tname;
        public String ename;
        public String tid;
        public String topic_icons;

    }
}

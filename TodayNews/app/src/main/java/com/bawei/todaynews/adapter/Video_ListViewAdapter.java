package com.bawei.todaynews.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.todaynews.R;
import com.bawei.todaynews.bean.News;
import com.bawei.todaynews.bean.VideoBean;
import com.bawei.todaynews.util.XCRoundImageView;
import com.squareup.picasso.Picasso;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Administrator on 2017/3/15.
 */
public class Video_ListViewAdapter extends BaseAdapter{
    private ArrayList<VideoBean> list_news;
    private Context context;

    public Video_ListViewAdapter(ArrayList<VideoBean> vb, Context context) {
        this.list_news = vb;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list_news.size();
    }

    @Override
    public Object getItem(int position) {
        return list_news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=convertView.inflate(context, R.layout.video_item,null);
            holder=new ViewHolder();
            holder.jp= (JCVideoPlayerStandard) convertView.findViewById(R.id.vedio);
            holder.img= (XCRoundImageView) convertView.findViewById(R.id.video_img);
            holder.share= (ImageView) convertView.findViewById(R.id.video_share);
            holder.tname= (TextView) convertView.findViewById(R.id.video_tname);
            holder.miaoshu= (TextView) convertView.findViewById(R.id.video_alias);

            convertView.setTag(holder);
        }else{
          holder= (ViewHolder) convertView.getTag();
        }
       /* ImageOptions.Builder builder = new ImageOptions.Builder();
        builder.setCircular(true);   //设置圆形图片
        builder.setCrop(true);
        builder.setSize(60,60);
        builder.setLoadingDrawableId(R.mipmap.icon);
        ImageOptions build = builder.build();    //注意设置图片的时候 ，添加的是这个参数
        x.image().bind(holder.img,list_news.get(position).topicImg,build);*/
        Picasso.with(context).load(list_news.get(position).topicImg).error(R.mipmap.icon).placeholder(R.mipmap.icon).into(holder.jp.thumbImageView);
        holder.jp.setUp(list_news.get(position).mp4_url,list_news.get(position).title);
        holder.tname.setText(list_news.get(position).topicName);
        holder.miaoshu.setText(list_news.get(position).playCount+"播放");
       // Log.i("----",list_news.get(position).videoTopic.alias+"--");
        //holder.jp.ivThumb得到ImageView
        holder.jp.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(context).load(list_news.get(position).cover).into(holder.jp.thumbImageView);
       //分享的监听，点击后可以分享信息
        return convertView;
    }
    class ViewHolder{
        JCVideoPlayerStandard jp;
        XCRoundImageView img;
        TextView tname;
        ImageView share;
        TextView miaoshu;

    }
}

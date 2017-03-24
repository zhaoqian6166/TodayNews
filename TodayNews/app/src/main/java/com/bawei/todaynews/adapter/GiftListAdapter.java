package com.bawei.todaynews.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bawei.todaynews.R;
import com.bawei.todaynews.bean.News;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.util.ArrayList;



/**
 * Created by Administrator on 2017/3/21.
 */
public class GiftListAdapter extends BaseAdapter {

    private ArrayList<News> list;
    private Context context;


    public GiftListAdapter(ArrayList<News> list, Context context) {

        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView==null){
            convertView=convertView.inflate(context, R.layout.gift_item,null);
            holder=new ViewHolder();
            holder.comment= (RadioButton) convertView.findViewById(R.id.gift_comment);
            holder.like= (RadioButton) convertView.findViewById(R.id.gift_like);
            holder.share= (RadioButton) convertView.findViewById(R.id.gift_share);
            holder.img= (ImageView) convertView.findViewById(R.id.gift_view);
            holder.title= (TextView) convertView.findViewById(R.id.gift_text);
            holder.colect= (RadioButton) convertView.findViewById(R.id.gift_colect);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).screen_name);
        holder.comment.setText(list.get(position).comment_count+"评论");
        holder.like.setText(list.get(position).repin_count+"");

        //收藏按钮点击监听————————————————————
       // BitmapFactory.
    // GifDrawable drawable = new GifDrawable(byte[]);  参数为数组流
     //   BitmapFactory.
      //  Uri uri = new Uri(list.get(position).url);
       // HttpURLConnection con=new Http
        //Glide可以直接加载动态图片      并且可以添加缓存  asGift()这个方法，如果图片不是动态图，会报错，这个方法去掉页可以加载出动态图
       // Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.img);
        Glide.with(context).load(list.get(position).large_image.url_list.get(0).url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.img);
     //   final android.widget.MediaController mediaController = new android.widget.MediaController(context);
    //    mediaController.setMediaPlayer((GifDrawable) holder.img.getDrawable());
       /* holder.img.setOnClickListener(new View.OnCli ckListener() {
            @Override
            public void onClick(View v) {
                mediaController.show();
                Snackbar.make(holder.img, "可以点击哦", Snackbar.LENGTH_LONG).show();
            }
        });*/

        return convertView;
    }

    class ViewHolder{
        TextView title;
        ImageView img;
        RadioButton like;
        RadioButton share;
        RadioButton comment;
        RadioButton colect;


    }
}

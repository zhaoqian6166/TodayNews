package com.bawei.todaynews.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.todaynews.R;
import com.bawei.todaynews.bean.News;
import com.bawei.todaynews.sqlite.SqUtil;
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
    private SqUtil sqUtil;


    public GiftListAdapter(ArrayList<News> list, Context context, SqUtil sqUtil) {
        this.list = list;
        this.context = context;
        this.sqUtil = sqUtil;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView==null){
            convertView=convertView.inflate(context, R.layout.gift_item,null);
            holder=new ViewHolder();
            holder.comment= (TextView) convertView.findViewById(R.id.gift_comment_text);
            holder.like= (TextView) convertView.findViewById(R.id.gift_like_text);
            holder.share= (ImageView) convertView.findViewById(R.id.gift_share);
            holder.img= (ImageView) convertView.findViewById(R.id.gift_view);
            holder.title= (TextView) convertView.findViewById(R.id.gift_text);
            holder.colect= (Button) convertView.findViewById(R.id.gift_love);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).screen_name);
        holder.comment.setText(list.get(position).comment_count+"评论");
        holder.like.setText(list.get(position).repin_count+"");
     //   Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
        Glide.with(context).load(list.get(position).large_image.url_list.get(0).url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.img);
        //收藏按钮点击监听————————————————————
        holder.colect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).love){
                    //如果原来状态是true  那么就变为false  同时删除数据库中的内容，并且吐司取消收藏
                    holder.colect.setBackgroundResource(R.mipmap.b_newcare_tabbar);
                    list.get(position).love=false;
                    sqUtil.delete(list.get(position).share_url);
                    Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show();
                }else{
                    //如果原来是false  那么就变为true  同时向数据库中添加内容，并且吐司收藏成功
                    holder.colect.setBackgroundResource(R.mipmap.b_newcare_tabbar_press);
                    list.get(position).love=true;
                    //动态图的type是2
                    sqUtil.addData(2,list.get(position).screen_name,list.get(position).share_url,list.get(position).large_image.url_list.get(0).url);
                    notifyDataSetChanged();
                    Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                }
                //将数据存储到数据库中

            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView title;//标题
        ImageView img;//图片
        TextView like;//赞
        ImageView share;//分享
        TextView comment;//评论
        Button colect;//收藏


    }
}

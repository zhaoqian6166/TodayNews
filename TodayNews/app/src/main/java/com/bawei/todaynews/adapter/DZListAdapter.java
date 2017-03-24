package com.bawei.todaynews.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.todaynews.R;
import com.bawei.todaynews.bean.News;
import com.bawei.todaynews.sqlite.MySqlite;
import com.bawei.todaynews.sqlite.SqUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/3/21.
 */
public class DZListAdapter extends BaseAdapter {

    private ArrayList<News> list;
    private Context context;
    private SqUtil sqUtil;

    public DZListAdapter(ArrayList<News> list, Context context, SqUtil sqUtil) {
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
            convertView=convertView.inflate(context, R.layout.duanzi_item,null);
            holder=new ViewHolder();
            holder.comment= (TextView) convertView.findViewById(R.id.dz_comment_text);
            holder.like= (TextView) convertView.findViewById(R.id.dz_like_text);
            holder.share= (ImageView) convertView.findViewById(R.id.dz_share);
            holder.content= (TextView) convertView.findViewById(R.id.dz_content);
            holder.colect= (Button) convertView.findViewById(R.id.dz_love);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.content.setText(list.get(position).content);
        holder.comment.setText(list.get(position).comment_count+"评论");
        holder.like.setText(list.get(position).repin_count+"");

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
                    //段子的type是1
                    sqUtil.addData(1,list.get(position).content,list.get(position).share_url,null);
                   notifyDataSetChanged();
                    Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                }
                //将数据存储到数据库中

            }
        });


        return convertView;
    }

    class ViewHolder{
        TextView content;
        TextView like;//点赞
        ImageView share;//分享
        TextView comment;
        Button colect;//收藏


    }
}

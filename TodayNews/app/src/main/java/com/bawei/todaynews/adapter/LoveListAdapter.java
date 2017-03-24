package com.bawei.todaynews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.todaynews.R;
import com.bawei.todaynews.bean.LoveBean;
import com.bawei.todaynews.sqlite.SqUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/24.
 */
public class LoveListAdapter extends BaseAdapter {
    ArrayList<LoveBean> list;
    Context context;
    SqUtil sq;

    public LoveListAdapter(ArrayList<LoveBean> list, Context context, SqUtil sq) {
        this.list = list;
        this.context = context;
        this.sq = sq;
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
        final MyHolder holder;
        if (convertView==null){
            convertView=convertView.inflate(context,R.layout.love_item,null);
            holder=new MyHolder();
            holder.colect= (Button) convertView.findViewById(R.id.love_colect);
            holder.img= (ImageView) convertView.findViewById(R.id.love_item_img);
            holder.title= (TextView) convertView.findViewById(R.id.love_item_title);
            holder.type= (TextView) convertView.findViewById(R.id.love_item_type);
            convertView.setTag(holder);
        }else{
            holder = (MyHolder) convertView.getTag();
        }
        holder.colect.setSelected(true);
        holder.title.setText(list.get(position).title);
        if (list.get(position).type==1){
            //如果是1   那么是段子   隐藏掉img
            holder.type.setText("段子");
            holder.img.setVisibility(View.GONE);
        }else{
            holder.type.setText("趣图");
            holder.img.setVisibility(View.VISIBLE);
            Glide.with(context).load(list.get(position).img).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.img);
        }
        //点击取消关注后   删除  按钮不可点击
        holder.colect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("-loveadapter-url----",list.get(position).url);
                sq.delete(list.get(position).url);
                list.remove(position);
                //从数据库中删除
                notifyDataSetChanged();
                Toast.makeText(context, "取消关注", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
    class MyHolder{
        TextView type;
        TextView title;
        Button colect;
        ImageView img;

    }
}

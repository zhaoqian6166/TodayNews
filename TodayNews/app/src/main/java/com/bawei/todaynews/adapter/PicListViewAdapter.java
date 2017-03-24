package com.bawei.todaynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.todaynews.R;
import com.bawei.todaynews.activity.PicActivity;
import com.bawei.todaynews.bean.News;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2017/3/21.
 */
public class PicListViewAdapter extends BaseAdapter {
    ArrayList<News> list;
    private Context context;

    public PicListViewAdapter(ArrayList<News> list, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=convertView.inflate(context, R.layout.news_photoview,null);
            holder=new ViewHolder();
            holder.count= (TextView) convertView.findViewById(R.id.pic_count);
            holder.username= (TextView) convertView.findViewById(R.id.pic_username);
            holder.title= (TextView) convertView.findViewById(R.id.pic_title);
            holder.photo= (PhotoView) convertView.findViewById(R.id.pic_photoview);
            convertView.setTag(holder);
        }else{
           holder= (ViewHolder) convertView.getTag();
        }
            holder.count.setText(list.get(position).comment_count+"评论");
            holder.username.setText(list.get(position).source);
            holder.title.setText(list.get(position).title);
        Glide.with(context).load(list.get(position).image_list.get(0).url).error(R.mipmap.icon).placeholder(R.mipmap.icon).into(holder.photo);
        //点击图片之后跳转到新的Activity中，展示剩下的图片
       /* holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        holder.photo.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View view, float x, float y) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "点击跳转", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, PicActivity.class);
                ArrayList<News.Img> image_list = list.get(position).image_list;
                String[] s=new String[image_list.size()];
                ArrayList<String> list_s=new ArrayList<String>();
                for (int i=0;i<image_list.size();i++){
                    String url = image_list.get(i).url;
                    if (i>0){
                        url=url.replace("list","list/640x360");
                    }
                    Log.i("=====",i+"--"+url);
                    list_s.add(url);

                }
                Log.i("测试传递的数据",list_s.size()+"---");
                intent.putStringArrayListExtra("imgs",list_s);
            //    intent.putExtra("imgs",s);
                context.startActivity(intent);
              //  context.finish();

            }
        });


        return convertView;
    }
    class ViewHolder{
        PhotoView photo;
        TextView username;
        TextView count;
        TextView title;
    }
}

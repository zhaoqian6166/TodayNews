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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.bawei.todaynews.R.id.cancel_action;
import static com.bawei.todaynews.R.id.item0_text;

/**
 * Created by Administrator on 2017/3/15.
 */
public class SY_ListViewAdapter extends BaseAdapter{
    private ArrayList<News> list_news;
    private Context context;

    public SY_ListViewAdapter(ArrayList<News> list_news, Context context) {
        this.list_news = list_news;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        News news = list_news.get(position);
     //   Log.i("图片信")
        if (news.has_image) {
            if (news.image_list.size() ==3) {
                //上面TextView+下面3张图片  item0
                return 0;
            } else if (news.image_list.size() == 0 && news.large_image_list.size() != 0) {
                //上面TextView+下面一张图片   item1
                return 1;
            } else if (news.image_list.size() == 0 && news.large_image_list.size() == 0 && news.middle_image.url != null) {
                //左边TextView+右边Img   item2
                return 2;
            }else{
                //纯文本显示 item3
                return 3;
            }
        }else if (news.has_video&&news.large_image_list.size()!=0){
            if (news.large_image_list.size()!=0){
                return 1;
            }else if(news.middle_image.url!=null){

                return 1;

            }else{
                return 3;
            }
            //返回一个上面textView  下面一张大图片的视图 item1
        }else{
            return 3;
        }
    }

    @Override
    public int getViewTypeCount() {
        //条目类型总数
        return 4;
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
        ViewHolder0 holder0=null;
        ViewHolder1 holder1=null;
        ViewHolder2 holder2=null;
        ViewHolder3 holder3=null;
        int type=getItemViewType(position);
        Log.d("测试得到的","位置："+position+",type："+type);
       // if (convertView==null){
            switch (type){
                case 0:
                    if (convertView==null){
                        convertView=convertView.inflate(context, R.layout.item0,null);
                        holder0=new ViewHolder0();
                        holder0.item0_img1= (ImageView) convertView.findViewById(R.id.item0_img1);
                        holder0.item0_img2= (ImageView) convertView.findViewById(R.id.item0_img2);
                        holder0.item0_img3= (ImageView) convertView.findViewById(R.id.item0_img3);
                   holder0.item0_text= (TextView) convertView.findViewById(R.id.item0_text);
                 holder0.item0_comment= (TextView) convertView.findViewById(R.id.item0_comment);
                 holder0.item0_from= (TextView) convertView.findViewById(R.id.item0_from);
                   convertView.setTag(holder0);
                    }

                    break;
                case 1:
                    if (convertView==null){
                        convertView=convertView.inflate(context,R.layout.item1,null);
                        holder1=new ViewHolder1();
                        holder1.item1_img= (ImageView) convertView.findViewById(R.id.item1_img);
                        holder1.item1_text= (TextView) convertView.findViewById(R.id.item1_text);
                        holder1.item1_comment= (TextView) convertView.findViewById(R.id.item1_comment);
                        holder1.item1_from= (TextView) convertView.findViewById(R.id.item1_from);
                        convertView.setTag(holder1);
                    }
                    break;
                case 2:
                    if (convertView==null){
                        convertView=convertView.inflate(context,R.layout.item2,null);
                        holder2=new ViewHolder2();
                        holder2.item2_img= (ImageView) convertView.findViewById(R.id.item2_img);
                        holder2.item2_text= (TextView) convertView.findViewById(R.id.item2_text);
                        holder2.item2_comment= (TextView) convertView.findViewById(R.id.item2_comment);
                        holder2.item2_from= (TextView) convertView.findViewById(R.id.item2_from);
                        convertView.setTag(holder2);
                    }

                    break;
                case 3:
                    if (convertView==null){
                        convertView=convertView.inflate(context,R.layout.item3,null);
                        holder3=new ViewHolder3();
                        holder3.item3_text= (TextView) convertView.findViewById(R.id.item3_text);
                        holder3.item3_comment= (TextView) convertView.findViewById(R.id.item3_comment);
                        holder3.item3_from= (TextView) convertView.findViewById(R.id.item3_from);
                        convertView.setTag(holder3);
                    }
                    break;

            }
        //得到holder
        switch (type){
            case 0:
                holder0= (ViewHolder0) convertView.getTag();
                break;
            case 1:
                holder1= (ViewHolder1) convertView.getTag();
                break;
            case 2:
                holder2= (ViewHolder2) convertView.getTag();
                break;
            case 3:
                holder3= (ViewHolder3) convertView.getTag();
                break;

        }

      //  给控件赋值
        switch (type){
            case 0:
                holder0.item0_text.setText(list_news.get(position).title);
                holder0.item0_comment.setText(list_news.get(position).comment_count+"评论");
                holder0.item0_from.setText(list_news.get(position).source);
           //     Picasso.with(context).load(list_news.get(position))

                Picasso.with(context).load(list_news.get(position).image_list.get(0).url).into(holder0.item0_img1);
                Picasso.with(context).load(list_news.get(position).image_list.get(1).url).into(holder0.item0_img2);
                Picasso.with(context).load(list_news.get(position).image_list.get(2).url).into(holder0.item0_img3);

                break;
            case 1:
                holder1.item1_text.setText(list_news.get(position).title);
                holder1.item1_comment.setText(list_news.get(position).comment_count+"评论");
                holder1.item1_from.setText(list_news.get(position).source);
                holder1.item1_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.with(context).load(list_news.get(position).large_image_list.get(0).url).into(holder1.item1_img);

                break;
            case 2:
                holder2.item2_text.setText(list_news.get(position).title);
                holder2.item2_comment.setText(list_news.get(position).comment_count+"评论");
                holder2.item2_from.setText(list_news.get(position).source);
                Picasso.with(context).load(list_news.get(position).middle_image.url).into(holder2.item2_img);
                break;
            case 3:
                holder3.item3_text.setText(list_news.get(position).title);
                holder3.item3_comment.setText(list_news.get(position).comment_count+"评论");
                holder3.item3_from.setText(list_news.get(position).source);
                break;
        }

        return convertView;
    }
    //ViewHodler
    class ViewHolder0{
        ImageView item0_img1;
        ImageView item0_img2;
        ImageView item0_img3;
        TextView item0_text;
        TextView item0_from;
        TextView item0_comment;

    }
    class ViewHolder1{
        ImageView item1_img;
        TextView item1_text;
        TextView item1_from;
        TextView item1_comment;
    }

    class ViewHolder2{
        ImageView item2_img;
        TextView item2_text;
        TextView item2_from;
        TextView item2_comment;

    }
    class ViewHolder3{
        TextView item3_text;
        TextView item3_from;
        TextView item3_comment;

    }
}

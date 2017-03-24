package com.bawei.todaynews.sqlite;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bawei.todaynews.bean.LoveBean;
import com.bawei.todaynews.bean.News;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/24.
 */
public class SqUtil {
    private SQLiteDatabase db;//可以读写的数据库对象

    public SqUtil(SQLiteDatabase db) {
        this.db = db;
    }

    //add  添加数据   id  类型 标题 地址  图片
    public void addData(int type,String title,String url,String img){
        Log.i("数据库添加",type+"--"+title+"--"+url);
        String sql="insert into love (type,title,url,img) values(?,?,?,?)";
        db.execSQL(sql,new Object[]{type,title,url,img});
    }
    //dele
    public void delete(String url){
        String sql="delete from love where url=?";
        db.execSQL(sql,new Object[]{url});
    }
    //select
    public ArrayList<LoveBean> selectAll(){
        String sql="select * from love";
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<LoveBean> list=new ArrayList<LoveBean>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            String title=cursor.getString(cursor.getColumnIndex("title"));
            String url=cursor.getString(cursor.getColumnIndex("url"));
            String img=cursor.getString(cursor.getColumnIndex("img"));
            LoveBean loveBean = new LoveBean(id,type,title,img,url);
            list.add(loveBean);

        }
        return list;

    }
   /* //select someOne
     public ArrayList<LoveBean> selectAll(String url){
        String sql="select * from love where url=?";
        Cursor cursor = db.rawQuery(sql, new String[]{url});
        ArrayList<LoveBean> list=new ArrayList<LoveBean>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            String title=cursor.getString(cursor.getColumnIndex("title"));
           // String url_=cursor.getString(cursor.getColumnIndex("url"));
            String img=cursor.getString(cursor.getColumnIndex("img"));
            LoveBean loveBean = new LoveBean(id,type,title,img,url);
            list.add(loveBean);

        }
        return list;

    }
    }*/

}

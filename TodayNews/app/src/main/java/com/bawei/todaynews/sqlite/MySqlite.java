package com.bawei.todaynews.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bawei.todaynews.bean.News;

/**
 * Created by Administrator on 2017/3/23.
 */
public class MySqlite extends SQLiteOpenHelper {
    /*static {
        // register our models
        cupboard().register(News.class);
        cupboard().register(Author.class);
    }*/
    public MySqlite(Context context) {
        super(context, "myLib.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //数据库存储   id  类型  标题 地址  图片
        String sql="create table love(id integer primary key autoincrement," +
                "type int," +
                "title int," +
                "url varchar," +
                "img varchar) ";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

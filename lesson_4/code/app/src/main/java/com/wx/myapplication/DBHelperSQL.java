package com.wx.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by wanxu on 2016/10/17.
 * Email:wanxu_pursue@163.com
 */

class DBHelperSQL extends SQLiteOpenHelper {
    //数据库名
    public static final String SQL_NAME="db_sql";
    //表名
    public static final String TABLE_NAME="user";
    //字段
    public static final String FIELD_ID="id";
    public static final String FIELD_NAME="name";
    public static final String FIELD_AGE="age";
    public static final String FIELD_PHONE="phone";
    public DBHelperSQL(Context context) {
        super(context, SQL_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //在数据库中建表
        //执行SQL语句，更多SQL语法见百度或sqlite.org
        Log.e("wanxu","create table "+TABLE_NAME);
        db.execSQL("CREATE TABLE "+TABLE_NAME+"(" +
                //主键id
                FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                //三个自定义字段
                FIELD_NAME+" TEXT DEFAULT \"\"," +
                FIELD_AGE+" INTEGER ," +
                FIELD_PHONE+" TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库版本更新时需要进行的操作在这里进行
        Log.e("wanxu","Sql is updated!");
    }
}

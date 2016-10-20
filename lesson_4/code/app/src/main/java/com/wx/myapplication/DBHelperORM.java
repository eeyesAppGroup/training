package com.wx.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by wanxu on 2016/10/17.
 * Email:wanxu_pursue@163.com
 */

public class DBHelperORM extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "db_orm";
    private static final int DATABASE_VERSION = 1;

    public DBHelperORM(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            Log.i(DBHelperORM.class.getName(), "onCreate");
            //通过User类创建表
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            Log.e(DBHelperORM.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            Log.i(DBHelperORM.class.getName(), "onUpgrade");
            //通过User类销毁表
            TableUtils.dropTable(connectionSource, User.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DBHelperORM.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    Dao<User,Integer> userDao=null;
    public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }
}

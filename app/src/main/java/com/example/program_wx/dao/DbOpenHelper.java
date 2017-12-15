package com.example.program_wx.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.program_wx.MyApp;

/**
 * 创建数据库SQLiteOpenHelper
 * Created by YinTao on 2017/12/11.
 */
public class DbOpenHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static DbOpenHelper instance;

    /** 创建表 */
    private static final String CREATE_USER_TABLE = "Create Table "
            + UserDao.TABLE_NAME + "("
            + UserDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY NOT NULL ,"
            + UserDao.COLUMN_NAME_NICK + " TEXT, "
            + UserDao.COLUMN_NAME_AVATAR + " TEXT, "
            + UserDao.COLUMN_NAME_INFO + " TEXT " +");";

    public DbOpenHelper(Context context)
    {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }

    private static String getUserDatabaseName()
    {
        return MyApp.getApp().getUsername() + "_app.db";
    }

    /** 获取DbOpenHelper单例 */
    public static DbOpenHelper getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new DbOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    /** 创建数据库 */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_USER_TABLE);//创建用户表
    }

    /** 升级数据库 */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}

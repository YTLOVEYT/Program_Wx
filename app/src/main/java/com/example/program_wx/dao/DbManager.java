package com.example.program_wx.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.program_wx.MyApp;
import com.example.program_wx.entity.User;
import com.example.program_wx.utils.UserUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作者
 * * 创建数据库的DbOpenHelper
 * * 做具体的数据库增删改查
 * Created by YinTao on 2017/12/11.
 */

public class DbManager
{
    private static DbManager dbManager = new DbManager();
    private DbOpenHelper dbOpenHelper;

    private DbManager()
    {
        dbOpenHelper = DbOpenHelper.getInstance(MyApp.getAppContext());
    }

    /** 获取实例 */
    public static synchronized DbManager getInstance()
    {
        if (dbManager == null)
        {
            dbManager = new DbManager();
        }
        return dbManager;
    }

    /** 保存所有联系人 */
    synchronized public void saveContactList(List<User> contactList)
    {
        SQLiteDatabase wDb = dbOpenHelper.getWritableDatabase();
        if (wDb.isOpen())
        {
            wDb.delete(UserDao.TABLE_NAME, null, null);//先清空数据库
            for (User user : contactList)
            {
                ContentValues values = new ContentValues();
                values.put(UserDao.COLUMN_NAME_ID, user.getUsername());
                if (user.getNick() != null)
                {
                    values.put(UserDao.COLUMN_NAME_NICK, user.getNick());
                }
                if (user.getAvatar() != null)
                {
                    values.put(UserDao.COLUMN_NAME_AVATAR, user.getAvatar());
                }
                if (user.getUserInfo() != null)
                {
                    values.put(UserDao.COLUMN_NAME_INFO, user.getUserInfo());
                }
                wDb.replace(UserDao.TABLE_NAME, null, values); //插入数据
            }
        }

    }

    /** 获取所有联系人 */
    synchronized public Map<String, User> getContactList()
    {
        SQLiteDatabase rDb = dbOpenHelper.getReadableDatabase();
        Map<String, User> contacts = new HashMap<>();
        if (rDb.isOpen())
        {
            Cursor cursor = rDb.rawQuery(" Select  *  From " + UserDao.TABLE_NAME, null);
            while (cursor.moveToNext())
            {
                String username = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_ID));
                String nick = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_AVATAR));
                String userInfo = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_INFO));
                User user = new User(username);
                user.setNick(nick);
                user.setAvatar(avatar);
                user.setUserInfo(userInfo);
                UserUtil.setUserInitialLetter(user);
                contacts.put(username, user);
            }
            cursor.close();
        }
        return contacts;
    }

    /**
     * 删除联系人
     * @param username 联系人的唯一用户名
     */
    synchronized public void deleteContact(String username)
    {
        SQLiteDatabase wDb = dbOpenHelper.getWritableDatabase();
        if (wDb.isOpen())
        {
            wDb.delete(UserDao.TABLE_NAME, UserDao.COLUMN_NAME_ID + " = ?", new String[]{username});
        }
    }

    /**
     * 保存联系人
     * @param user 联系人
     */
    synchronized public void saveContact(User user)
    {
        SQLiteDatabase wDb = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDao.COLUMN_NAME_ID, user.getUsername());
        if (user.getNick() != null)
        {
            values.put(UserDao.COLUMN_NAME_NICK, user.getNick());
        }
        if (user.getAvatar() != null)
        {
            values.put(UserDao.COLUMN_NAME_AVATAR, user.getAvatar());
        }
        if (user.getUserInfo() != null)
        {
            values.put(UserDao.COLUMN_NAME_INFO, user.getUserInfo());
        }
        if (wDb.isOpen())
        {
            wDb.replace(UserDao.TABLE_NAME, null, values);
        }
    }

}

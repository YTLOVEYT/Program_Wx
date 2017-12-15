package com.example.program_wx.dao;

import android.content.Context;

import com.example.program_wx.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 用户数据表 负责用户的添加删除等
 * Created by YinTao on 2017/12/11.
 */

public class UserDao
{
    public static final String TABLE_NAME = "uers";
    public static final String COLUMN_NAME_ID = "username";
    public static final String COLUMN_NAME_NICK = "nick";
    public static final String COLUMN_NAME_AVATAR = "avatar";
    public static final String COLUMN_NAME_INFO = "userInfo";
    public static final String PREF_TABLE_NAME = "pref";
    public static final String COLUMN_NAME_DISABLED_GROUPS = "disabled_groups";
    public static final String COLUMN_NAME_DISABLED_IDS = "disabled_ids";

    public static final String ROBOT_TABLE_NAME = "robots";
    public static final String ROBOT_COLUMN_NAME_ID = "username";
    public static final String ROBOT_COLUMN_NAME_NICK = "nick";
    public static final String ROBOT_COLUMN_NAME_AVATAR = "avatar";

    public UserDao(Context context)
    {
    }

    /** 增 */
    public void saveContactList(List<User> contactList)
    {
        DbManager.getInstance().saveContactList(contactList);
    }

    /** 保存单个用户 */
    public void saveContact(User user)
    {
        DbManager.getInstance().saveContact(user);
    }

    /** 获取用户集合 */
    public Map<String, User> getContactList()
    {
        return DbManager.getInstance().getContactList();
    }

    /** 删除用户 */
    public void deleteContact(String username)
    {
        DbManager.getInstance().deleteContact(username);
    }

}

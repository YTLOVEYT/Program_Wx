package com.example.program_wx.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.program_wx.MyApp;

/**
 * SharedPreferences保存数据
 * commit同步提交到磁盘，apply先原子提交到内存后异步提交到磁盘
 * Created by YinTao on 2017/12/5.
 */

public class SpUtil
{
    private static SharedPreferences sp;

    private SpUtil()
    {
        sp = PreferenceManager.getDefaultSharedPreferences(MyApp.getAppContext());
    }

//    private static SharedPreferences getSh() //我也不知道哪种方法好
//    {
//        return PreferenceManager.getDefaultSharedPreferences(MyApp.getAppContext());
//    }

    public static boolean saveString(String tag, String value)
    {
        if (sp == null)
        {
            sp = PreferenceManager.getDefaultSharedPreferences(MyApp.getAppContext());
        }
        return sp.edit().putString(tag, value).commit();
    }

    public static String getString(String tag)
    {
        if (sp == null)
        {
            sp = PreferenceManager.getDefaultSharedPreferences(MyApp.getAppContext());
        }
        return sp.getString(tag, null);
    }

    public static boolean saveInt(String tag, int value)
    {
        if (sp == null)
        {
            sp = PreferenceManager.getDefaultSharedPreferences(MyApp.getAppContext());
        }
        return sp.edit().putInt(tag, value).commit();
    }

    public static int getInt(String tag)
    {
        if (sp == null)
        {
            sp = PreferenceManager.getDefaultSharedPreferences(MyApp.getAppContext());
        }
        return sp.getInt(tag, -1);
    }

    public static boolean saveBoolean(String tag, boolean value)
    {
        if (sp == null)
        {
            sp = PreferenceManager.getDefaultSharedPreferences(MyApp.getAppContext());
        }
        return sp.edit().putBoolean(tag, value).commit();
    }

    public static boolean getBoolean(String tag)
    {
        if (sp == null)
        {
            sp = PreferenceManager.getDefaultSharedPreferences(MyApp.getAppContext());
        }
        return sp.getBoolean(tag, false);
    }

}

package com.example.program_wx.utils;

import android.util.Log;

/**
 * 打印日志管理
 * Created by YinTao on 2017/12/5.
 */

public class LogUtil
{
    private static boolean flag = true;//默认开启log
    private static String TAG = "COM";//默认开启log

    private LogUtil()
    {
    }

    /**
     * log初始化，决定是否打印log
     * @param open 是否开启
     * @param tag  打印标签
     */
    public static void openLog(boolean open, String tag)
    {
        flag = open;
        TAG = tag;
    }

    public static void e(String msg)
    {
        if (!flag)
        {
            return;
        }
        Log.e(TAG, msg);
    }

    public static void i(String msg)
    {
        if (!flag)
        {
            return;
        }
        Log.i(TAG, msg);
    }

}

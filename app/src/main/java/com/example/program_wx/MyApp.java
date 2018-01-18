package com.example.program_wx;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;

import com.example.program_wx.dao.ContactsManager;
import com.example.program_wx.utils.LogUtil;
import com.example.program_wx.utils.SpUtil;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 全局进程
 * Created by YinTao on 2017/12/5.
 */

public class MyApp extends Application
{
    private static final String TAG = "MyApp";
    private static Context appContext;
    private static MyApp app;
    private static ArrayList<Activity> activities;

    @Override
    public void onCreate()
    {
        int pid = Process.myPid();
        String appName = getAppName(pid);
        if (appName == null || !appName.equalsIgnoreCase(this.getPackageName()))
        {
            return;
        }
        super.onCreate();
        appContext = this;
        app = this;
        activities = new ArrayList<>();
        LogUtil.openLog(true, "------------------");
//        EMClientHelper.init(this);
//        HTClient.init(this.getApplicationContext());
        ContactsManager.init(this);//初始化数据库操作
        if (!LeakCanary.isInAnalyzerProcess(this))//添加内存检测
        {
            LeakCanary.install(this);
        }
        EMClientHelper.init(this.getApplicationContext());
        // FIXME: 2017/12/5 添加其他初始化操作

    }

    private String getAppName(int pID)
    {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext())
        {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try
            {
                if (info.pid == pID)
                {
                    processName = info.processName;
                    return processName;
                }
            }
            catch (Exception e)
            {
                LogUtil.e("获取appName抛异常");
            }
        }
        return null;
    }

    public static Context getAppContext()
    {
        return appContext;
    }

    public static MyApp getApp()
    {
        return app;
    }

    public String getUsername()
    {
        return SpUtil.getString(MyConst.JSON_KEY_HXID);
    }

    /** 保存所有的activity */
    public void saveActivities(Activity activity)
    {
        if (activity != null)
        {
            activities.add(activity);
        }
    }

    /** 销毁所有的activity */
    public void finishActivities()
    {
        for (Activity activity : activities)
        {
            if (activity != null && !activity.isFinishing())
            {
                activity.finish();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
    }
}

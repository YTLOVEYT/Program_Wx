package com.example.program_wx;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;

import com.example.program_wx.utils.LogUtil;
import com.squareup.leakcanary.LeakCanary;

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

        LogUtil.openLog(true, "------------------");

        if (!LeakCanary.isInAnalyzerProcess(this))//添加内存检测
        {
            LeakCanary.install(this);
        }
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
}

package com.example.program_wx.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间相关函数方法工具
 * Created by YinTao on 2017/12/19.
 */

public class TimeUtil
{
    private static SimpleDateFormat sdf = new SimpleDateFormat();

    public static String long2StringDate(long time)
    {
        Date d1 = new Date(time);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Date d2 = new Date();
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        int year1 = c1.get(Calendar.YEAR);
        int mouth1 = c1.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DATE);
        int hour = c1.get(Calendar.HOUR_OF_DAY);//24
        int year2 = c2.get(Calendar.YEAR);
        int mouth2 = c2.get(Calendar.MONTH);
        int day2 = c2.get(Calendar.DATE);
        if (year1 == year2 && mouth1 == mouth2) //同一天
        {
            if (day1 == day2)
            {
                if (hour < 6 && hour > 0)
                {
                    sdf.applyPattern("hh:ss");
                    return "凌晨" + sdf.format(d1);
                }
                else if (hour >= 6 && hour <= 12)
                {
                    sdf.applyPattern("hh:ss");
                    return "上午" + sdf.format(d1);
                }
                else if (hour > 12 && hour < 18)
                {
                    sdf.applyPattern("hh:ss");
                    return "下午" + sdf.format(d1);
                }
                else
                {
                    sdf.applyPattern("hh:ss");
                    return "晚上" + sdf.format(d1);
                }
            }
            else if ((day1 + 1) == day2)
            {
                return "昨天";
            }
            else
            {
                sdf.applyPattern("MM-dd");
                return sdf.format(d1);
            }
        }
        else
        {
            sdf.applyPattern("yyyy-MM-dd");
            return sdf.format(d1);
        }
    }
}

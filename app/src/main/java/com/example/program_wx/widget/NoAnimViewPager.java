package com.example.program_wx.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 自定义viewPager
 * Created by YinTao on 2017/12/13.
 */

public class NoAnimViewPager extends ViewPager
{
    public NoAnimViewPager(Context context)
    {
        super(context);
    }

    public NoAnimViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    // FIXME: 2017/12/13   去除页面切换时的滑动翻页效果
    @Override
    public void setCurrentItem(int item, boolean smoothScroll)
    {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item)
    {
        super.setCurrentItem(item);
    }
}

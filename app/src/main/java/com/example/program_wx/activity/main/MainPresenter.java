package com.example.program_wx.activity.main;

import android.content.Context;

/**
 * 主界面的操作者
 * Created by YinTao on 2017/12/13.
 */

public class MainPresenter implements MainBasePresenter
{
    private MainView mainView;
    private Context context;

    public MainPresenter(MainView mainView)
    {
        this.mainView = mainView;
        mainView.setPresenter(this);
        context = mainView.getContext();
    }

    @Override
    public void start()
    {

    }

    @Override
    public void checkVersion()
    {

    }
}

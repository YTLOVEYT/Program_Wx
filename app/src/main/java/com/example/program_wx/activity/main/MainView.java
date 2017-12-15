package com.example.program_wx.activity.main;

import android.app.Activity;

import com.example.program_wx.activity.BaseView;

/**
 * 主界面View接口
 * Created by YinTao on 2017/12/13.
 */

public interface MainView extends BaseView<MainPresenter>
{
    /** 显示升级dialog */
    void showUpdateDialog(String message, String url, String isForce);

    /** 获取上下文 */
    Activity getContext();
    // FIXME: 2017/12/13

}

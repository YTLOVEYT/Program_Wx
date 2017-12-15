package com.example.program_wx.test.mvp.login.view;

import android.app.Activity;

import com.example.program_wx.test.mvp.IView;

/**
 * LoginView视图层的操作
 * Created by YinTao on 2017/12/14.
 */

public interface LoginViewImpl extends IView
{
    /** 获取上下文 */
    Activity getContext();

    /** 显示 */
    void showDialog(String data);

}

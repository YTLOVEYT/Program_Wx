package com.example.program_wx.test.mvp.login.presenter;


import com.example.program_wx.test.mvp.IPresenter;

/**
 * loginPresenter的实现接口 指定loginPresenter的任务
 * Created by YinTao on 2017/12/14.
 */

public interface LoginPresenterImpl extends IPresenter
{
    /** 登录 */
    void login(String name, String pwd);

    /** 登出 */
    void logout();
}

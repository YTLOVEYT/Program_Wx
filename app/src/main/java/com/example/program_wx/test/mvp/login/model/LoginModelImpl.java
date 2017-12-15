package com.example.program_wx.test.mvp.login.model;

import com.example.program_wx.test.mvp.IModel;
import com.example.program_wx.test.mvp.MyCallBack;

/**
 * 业务层业务
 * Created by YinTao on 2017/12/14.
 */

public interface LoginModelImpl extends IModel
{
    /** 登录 */
    void login(String username, String pwd, MyCallBack myCallBack);

    /** 登出 */
    void logout(MyCallBack myCallBack);
}

package com.example.program_wx.test.mvp.login.model;

import com.example.program_wx.test.mvp.MyCallBack;

/**
 * LoginModel业务
 * Created by YinTao on 2017/12/14.
 */

public class LoginModel implements LoginModelImpl
{

    @Override
    public void login(String username, String pwd, MyCallBack myCallBack)
    {
        if (username.equals("yinTao"))
        {
            myCallBack.onSuccess("正确");
        }
        else
        {
            myCallBack.onFailed("错误");
        }
    }

    @Override
    public void logout(MyCallBack myCallBack)
    {
        myCallBack.onFailed("错误");
    }

}

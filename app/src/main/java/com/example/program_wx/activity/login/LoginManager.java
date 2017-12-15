package com.example.program_wx.activity.login;

import android.app.Activity;

import com.example.program_wx.activity.BasePresenter;
import com.example.program_wx.activity.BaseView;

/**
 * 登录相关操作接口
 * Created by YinTao on 2017/12/6.
 */

interface LoginManager
{
    interface Presenter extends BasePresenter
    {
        /**
         * 向服务器发送请求登录
         * @param username 用户名tel
         * @param password 密码
         * @param isAuth   验证
         */
        void requestServer(String username, String password, boolean isAuth);
        // FIXME: 2017/12/6 加载国家列表的请求

    }

    interface View extends BaseView<Presenter>
    {
        /** 获取上下文 */
        Activity getBaseActivity();

        /** 显示Dialog */
        void showDialog();

        /** 取消dialog */
        void cancelDialog();

        /** 提示用户 */
        void showToast(String msg);
    }
}

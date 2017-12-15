package com.example.program_wx.activity.register;

import android.app.Activity;

import com.example.program_wx.activity.BasePresenter;
import com.example.program_wx.activity.BaseView;

/**
 * 登录相关操作接口
 * Created by YinTao on 2017/12/6.
 */

interface RegisterManager
{
    interface Presenter extends BasePresenter
    {
        /**
         * 向服务器发送请求注册
         * @param username 用户名tel
         * @param password 密码
         * @param nickName 昵称
         */
        void requestServer(String username, String password, String nickName);

        /**
         * 请求验证码
         * @param mobile 手机
         */
        void sendSmsCode(String mobile);

        // FIXME: 2017/12/6 注册还有个

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

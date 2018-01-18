package com.example.program_wx.activity.main.contacts;

import android.app.Activity;

import com.example.program_wx.activity.BaseView;
import com.example.program_wx.entity.User;

/**
 * ContactFragment页面操作
 * Created by YinTao on 2018/1/8.
 */

public interface ContactView extends BaseView<ContactPresenter>
{
    void refresh();

    void showContactCount(int count);

    void showInvitationCount();

    void showItemDialog(User user);

    Activity getBaseActivity();
}

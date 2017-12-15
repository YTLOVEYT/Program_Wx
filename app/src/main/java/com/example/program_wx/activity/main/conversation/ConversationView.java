package com.example.program_wx.activity.main.conversation;

import android.app.Activity;

import com.example.program_wx.activity.BaseView;
import com.hyphenate.chat.EMConversation;

/**
 * ConversationView
 * Created by YinTao on 2017/12/14.
 */

public interface ConversationView extends BaseView<ConversationPresenter>
{
    /** 显示Dialog */
    void showItemDialog(EMConversation conversation);

    /** 刷新列表 */
    void refresh();

    /** 获取上下文 */
    Activity getBaseContext();
}

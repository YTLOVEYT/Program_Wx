package com.example.program_wx.activity.main.conversation;

import com.example.program_wx.activity.BasePresenter;
import com.hyphenate.chat.EMConversation;

import java.util.Map;

/**
 * ConversationPresenter的操作接口
 * Created by YinTao on 2017/12/14.
 */

public interface BaseConversationPresenter extends BasePresenter
{
    /** 加载所有的会话 */
    Map<String, EMConversation> getAllConversations();

    /** 删除会话 */
    void deleteConversation(EMConversation conversation);

    /** 置顶 */
    void setTopConversation(EMConversation conversation);

    /** 取消置顶 */
    void cancelTopConversation(EMConversation conversation);

    /** 刷新会话 */
    void refreshConversations();

    /** 获取未读的消息 */
    int getUnreadMsgCount();

    /** 标记已读 */
    void markMessageRead(EMConversation conversation);

    // FIXME: 2017/12/14 添加方法
}

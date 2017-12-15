package com.example.program_wx.activity.main.conversation;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.List;
import java.util.Map;

/**
 * ConversationPresenter
 * Created by YinTao on 2017/12/14.
 */

public class ConversationPresenter implements BaseConversationPresenter
{
    private ConversationView conversationView;
    private Context context;
    private Map<String, EMConversation> allConversations;

    public ConversationPresenter(ConversationView view)
    {
        conversationView = view;
        conversationView.setPresenter(this);
        context = conversationView.getBaseContext();
        allConversations = EMClient.getInstance().chatManager().getAllConversations();//加载所欲会话
        // FIXME: 2017/12/15 加载公告


    }

    @Override
    public Map<String, EMConversation> getAllConversations()
    {
        return allConversations;
    }

    @Override
    public void deleteConversation(EMConversation conversation)
    {
        // conversation.conversationId() 单聊就是用户名 ，群聊是群ID
        allConversations.remove(conversation.conversationId());
        EMClient.getInstance().chatManager().deleteConversation(conversation.conversationId(), true);
        conversationView.refresh();//刷新列表
    }

    @Override
    public void setTopConversation(EMConversation conversation)
    {
        // FIXME: 2017/12/14
    }

    @Override
    public void cancelTopConversation(EMConversation conversation)
    {
        // FIXME: 2017/12/14
    }

    @Override
    public void refreshConversations()
    {
        allConversations.clear();
        allConversations.putAll(EMClient.getInstance().chatManager().getAllConversations());
    }

    @Override
    public int getUnreadMsgCount()
    {
        int totalUnReadMsg = 0;
        List<EMConversation> conversations = (List<EMConversation>) allConversations.values();
        for (int i = 0; i < conversations.size(); ++i)
        {
            totalUnReadMsg += conversations.get(i).getUnreadMsgCount();
        }
        return totalUnReadMsg;
    }

    @Override
    public void markMessageRead(EMConversation conversation)
    {
        conversation.markAllMessagesAsRead();
//        EMClient.getInstance().chatManager().markAllConversationsAsRead();
    }

    @Override
    public void start()
    {

    }
}

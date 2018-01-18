package com.example.program_wx.activity.main.conversation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.program_wx.R;
import com.example.program_wx.utils.LogUtil;
import com.example.program_wx.utils.TimeUtil;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Conversation界面的适配器
 * Created by YinTao on 2017/12/14.
 */

public class ConversationAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<EMConversation> conversationsList = new ArrayList<>();

    public ConversationAdapter(Context context, Map<String,EMConversation> conversations)
    {
        this.context = context;
        conversationsList.clear();
        for (EMConversation conversation : conversations.values())
        {
            conversationsList.add(conversation);
        }
    }

    @Override
    public int getCount()
    {
        return conversationsList.size();
    }

    @Override
    public EMConversation getItem(int position)
    {
        return conversationsList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_conversation_item, parent, false);
            holder = new ViewHolder();
            holder.tv_unread = (TextView) convertView.findViewById(R.id.unread_msg_number);
            holder.tv_group_tag = (TextView) convertView.findViewById(R.id.tv_group_tag);
            holder.tv_name = (TextView) convertView.findViewById(R.id.name);
            holder.tv_time = (TextView) convertView.findViewById(R.id.time);
            holder.tv_content = (TextView) convertView.findViewById(R.id.message);
            holder.tv_mentioned = (TextView) convertView.findViewById(R.id.mentioned);
            holder.ivAvatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.other_music = (ImageView) convertView.findViewById(R.id.other);
            holder.unread = (ImageView) convertView.findViewById(R.id.msg_state);
            holder.llItem = (LinearLayout) convertView.findViewById(R.id.conversation_item);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        EMConversation conversation = getItem(position);
        LogUtil.e("conversation=" + conversation.toString());
        EMMessage lastMessage = conversation.getLastMessage(); //最后一条消息
        if (conversation.isGroup()) //群聊
        {
            holder.tv_group_tag.setVisibility(View.VISIBLE); //群标志
            holder.tv_name.setText(conversation.conversationId());//
        }
        else
        {
            holder.tv_group_tag.setVisibility(View.GONE);
            holder.tv_name.setText(conversation.conversationId());//
        }
        // FIXME: 2017/12/19 判断消息类型
        if (lastMessage!=null)
        {
            LogUtil.e("lastMessage=" + lastMessage.getBody().toString());
            String str[] = lastMessage.getBody().toString().split(":");
            LogUtil.e("str[]=" + Arrays.toString(str));
            if (str[0].equals("txt"))
            {
                holder.tv_content.setText(str[1].substring(1, str[1].length()-1));//最后一条消息
            }
            else if (str[0].equals("image"))
            {
                holder.tv_content.setText("[图片]");//最后一条消息
            }
            LogUtil.e("getMsgTime=" + lastMessage.getMsgTime());
            holder.tv_time.setText(TimeUtil.long2StringDate(lastMessage.getMsgTime()));//时间
        }
        int unreadMsg = conversation.getUnreadMsgCount();
        if (unreadMsg > 0)
        {
            holder.tv_unread.setText(String.valueOf(unreadMsg));//未读
            holder.tv_unread.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.tv_unread.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder
    {
        TextView tv_unread;
        TextView tv_group_tag;
        TextView tv_name;
        TextView tv_time;
        TextView tv_content;
        TextView tv_mentioned;
        ImageView ivAvatar, other_music, unread;
        LinearLayout llItem;
    }


}

package com.example.program_wx.activity.chat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.program_wx.R;
import com.example.program_wx.activity.BaseActivity;
import com.example.program_wx.dao.ContactsManager;
import com.example.program_wx.entity.User;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

public class ChatActivity extends BaseActivity
{

    private ChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String userId = getIntent().getExtras().getString("userId");
        int chatType = getIntent().getExtras().getInt("chatType", ChatManager.SINGLE);
        if (chatType == ChatManager.GROUP)
        {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(userId);
            String groupName = group.getGroupName();
            if (TextUtils.isEmpty(groupName))
            {
                groupName = group.getGroupId();
            }
            setTitle(groupName);

            showRightView(R.drawable.icon_setting_group, new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // FIXME: 2017/12/25 跳转到设置界面

                }
            });
        }
        else if (chatType == ChatManager.SINGLE)
        {
            User user = ContactsManager.getInstance().getContactList().get(userId);//直接从数据库中获取聊天用户
            if (user != null)
            {
                String nick = user.getNick();
                if (TextUtils.isEmpty(nick))
                {
                    nick = user.getUsername();
                }
                setTitle(nick);
            }
            showRightView(R.drawable.icon_setting_single, new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // FIXME: 2017/12/25 跳转到设置界面
                }
            });
        }
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ChatActivity.this.finish();
            }
        });
        chatFragment = (ChatFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (chatFragment == null)
        {
            chatFragment = new ChatFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.contentFrame, chatFragment).commit();
        }
        chatFragment.setArguments(getIntent().getExtras());

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }
}

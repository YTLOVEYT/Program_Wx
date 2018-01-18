package com.example.program_wx;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.program_wx.utils.LogUtil;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.adapter.EMAError;

import java.util.List;

/**
 * jar包，没懂，好像是全局监听，在application中初始化的
 * Created by YinTao on 2017/12/6.
 */

public class EMClientHelper
{
    private static EMClientHelper htClientHelper;
    private Context context;

    public static void init(Context context)
    {
        htClientHelper = new EMClientHelper(context);
    }

    private EMClientHelper(final Context context)
    {
        this.context = context;
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EMClient.getInstance().init(context, options);
        LogUtil.e("初始化EM");
        /* 信息监听 */
        EMClient.getInstance().setDebugMode(true);
        // 环信状态监听
        EMClient.getInstance().addConnectionListener(new EMConnectionListener() //注册监听
        {
            @Override
            public void onConnected()
            {
                LogUtil.e("EM连接上");
            }

            @Override
            public void onDisconnected(int errorCode)
            {
                switch (errorCode)
                {
                    case EMAError.USER_REMOVED://账号被移除
                        LogUtil.e("账号被移除");
                        break;
                    case EMAError.USER_LOGIN_ANOTHER_DEVICE://账号异地
                        LogUtil.e("账号异地登录");
                        break;
                    default:
                        // FIXME: 2017/12/12 网络判断
                        LogUtil.e("EM未知异常");
                        break;
                }
            }
        });
        //聊天监听
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener()
        {
            @Override
            public void onMessageReceived(List<EMMessage> messages)
            {
                LogUtil.e("onMessageReceived" + messages.get(0));
                Intent intent = new Intent(MyConst.RECEIVE_NEW_MESSAGE).putExtra("newMsg", messages.get(0));
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages)
            {
                LogUtil.e("onCmdMessageReceived=" + messages.get(0));
                Intent intent = new Intent(MyConst.RECEIVE_CMD_MESSAGE).putExtra("cmdMsg", messages.get(0));
                LocalBroadcastManager.getInstance(MyApp.getAppContext()).sendBroadcast(intent);
            }

            @Override
            public void onMessageRead(List<EMMessage> messages)
            {
                LogUtil.e("onMessageRead" + messages);
            }

            @Override
            public void onMessageDelivered(List<EMMessage> messages)
            {
                LogUtil.e("onMessageDelivered" + messages);
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change)
            {
                LogUtil.e("onMessageChanged" + message);
            }
        });
        //联系人监听
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener()
        {
            @Override
            public void onContactAdded(String username)
            {

            }

            @Override
            public void onContactDeleted(String username)
            {

            }

            @Override
            public void onContactInvited(String username, String reason)
            {

            }

            @Override
            public void onFriendRequestAccepted(String username)
            {

            }

            @Override
            public void onFriendRequestDeclined(String username)
            {

            }
        });
    }


}

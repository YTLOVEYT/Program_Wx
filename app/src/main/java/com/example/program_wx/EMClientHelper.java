package com.example.program_wx;

import android.content.Context;

import com.example.program_wx.utils.LogUtil;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.adapter.EMAError;

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

    private EMClientHelper(Context context)
    {
        this.context = context;
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EMClient.getInstance().init(context, options);
        LogUtil.e("初始化EM");
        /* 信息监听 */
        EMClient.getInstance().setDebugMode(true);
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
    }


}

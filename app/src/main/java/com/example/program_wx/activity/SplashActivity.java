package com.example.program_wx.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.example.program_wx.R;
import com.example.program_wx.activity.login.LoginActivity;
import com.example.program_wx.activity.main.MainActivity;
import com.htmessage.sdk.client.HTClient;

/**
 * 话说继承Activity开屏没有白页
 */
public class SplashActivity extends Activity
{
    private LinearLayout llSplash;
    private AlphaAnimation alphaAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        alphaAnimation=new AlphaAnimation(0.5f,1.0f);
        alphaAnimation.setDuration(2000);
        llSplash.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)//结束
            {
                if (HTClient.getInstance().isLogined())//已经登录
                {
                    // FIXME: 2017/12/5 上传最近登录时间到服务器
                    Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });


    }

    /** 初始化界面控件 */
    private void initViews()
    {
        llSplash= (LinearLayout) findViewById(R.id.ll_splash);
    }
}

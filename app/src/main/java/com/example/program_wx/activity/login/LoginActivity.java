package com.example.program_wx.activity.login;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.program_wx.R;
import com.example.program_wx.activity.BaseActivity;
import com.example.program_wx.utils.LogUtil;

public class LoginActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录");
        boolean isAuth = getIntent().getBooleanExtra("isAuth", false);//false
        if (!isAuth)
        {
            hideBackView();//隐藏
        }
        else
        {
            // FIXME: 2017/12/6 退出
            LogUtil.e("退出");
        }
        requestPermissions();//请求权限
        //TODO 为什么不直接使用Activity
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (loginFragment == null)
        {
            loginFragment = new LoginFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFrame, loginFragment);
            transaction.commit();
        }
        LoginPresenter presenter = new LoginPresenter(loginFragment);
    }

    /** 运行期请求权限 */
    private void requestPermissions()
    {
        // FIXME: 2017/12/12 申请manifest中的权限

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }
}

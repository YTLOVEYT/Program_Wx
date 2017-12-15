package com.example.program_wx.activity.register;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.program_wx.R;
import com.example.program_wx.activity.BaseActivity;

public class RegisterActivity extends BaseActivity
{
    // FIXME: 2017/12/13 开启相机权限
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("用户注册");
        RegisterFragment registerFragment = (RegisterFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (registerFragment == null)
        {
            registerFragment = new RegisterFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFrame, registerFragment);
            transaction.commit();
        }
        RegisterPresenter presenter = new RegisterPresenter(registerFragment);
    }


}

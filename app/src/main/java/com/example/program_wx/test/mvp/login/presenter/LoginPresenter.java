package com.example.program_wx.test.mvp.login.presenter;

import com.example.program_wx.test.mvp.MyCallBack;
import com.example.program_wx.test.mvp.login.model.LoginModel;
import com.example.program_wx.test.mvp.login.model.LoginModelImpl;
import com.example.program_wx.test.mvp.login.view.LoginViewImpl;

/**
 * 操纵者
 * Created by YinTao on 2017/12/14.
 */

public class LoginPresenter implements LoginPresenterImpl
{

    private LoginModelImpl loginModel;
    private LoginViewImpl loginView;

    public LoginPresenter(LoginViewImpl loginView)
    {
        this.loginView = loginView;
        loginModel = new LoginModel();
    }

    @Override
    public void login(String name, String pwd)
    {
        loginModel.login(name, pwd, new MyCallBack()
        {
            @Override
            public void onSuccess(String content)
            {
                loginView.showDialog(content);
            }

            @Override
            public void onFailed(String error)
            {
                loginView.showDialog(error);
            }

            @Override
            public void onProgress(int progress)
            {

            }
        });
    }

    @Override
    public void logout()
    {
        loginModel.logout(new MyCallBack()
        {
            @Override
            public void onSuccess(String content)
            {
                loginView.showDialog(content);
            }

            @Override
            public void onFailed(String error)
            {
                loginView.showDialog(error);
            }

            @Override
            public void onProgress(int progress)
            {

            }
        });
    }
}

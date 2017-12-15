package com.example.program_wx.test.mvp.login.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.program_wx.R;
import com.example.program_wx.test.mvp.login.presenter.LoginPresenter;
import com.example.program_wx.test.mvp.login.presenter.LoginPresenterImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MvpLoginActivity extends AppCompatActivity implements LoginViewImpl
{

    @Bind(R.id.login)
    Button login;
    @Bind(R.id.logout)
    Button logout;

    private LoginPresenterImpl loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);

    }

    @Override
    public Activity getContext()
    {
        return this;
    }

    @Override
    public void showDialog(String data)
    {
        // FIXME: 2017/12/14
        Toast.makeText(this, "data=" + data, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.login, R.id.logout})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.login:
                loginPresenter.login("yinTao", "123");
                break;
            case R.id.logout:
                loginPresenter.logout();
                break;
        }
    }
}

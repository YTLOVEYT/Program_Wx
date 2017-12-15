package com.example.program_wx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.program_wx.MyApp;
import com.example.program_wx.R;

/**
 * 基类Activity
 * Created by YinTao on 2017/12/6.
 */

public class BaseActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        MyApp.getApp().saveActivities(this);
    }

    /** 设置标题 */
    public void setTitle(String title)
    {
        TextView textView = (TextView) this.findViewById(R.id.tv_title);
        if (textView != null)
        {
            textView.setText(title);
        }
    }

    /** 隐藏后退的View */
    public void hideBackView()
    {
        ImageView iv_back = (ImageView) this.findViewById(R.id.iv_back);
        View view = this.findViewById(R.id.view_temp);
        if (iv_back != null && view != null)
        {
            iv_back.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }
    }
}

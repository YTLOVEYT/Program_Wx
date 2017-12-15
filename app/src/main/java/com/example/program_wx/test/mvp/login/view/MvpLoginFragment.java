package com.example.program_wx.test.mvp.login.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.program_wx.R;
import com.example.program_wx.utils.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MvpLoginFragment extends Fragment
{


    public MvpLoginFragment()
    {
        LogUtil.e("MvpLoginFragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_mvp_login, container, false);
    }

}

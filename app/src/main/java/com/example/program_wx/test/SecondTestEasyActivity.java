package com.example.program_wx.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.program_wx.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondTestEasyActivity extends AppCompatActivity
{
    @Bind(R.id.showPermissions)
    Button showPermissions;
    @Bind(R.id.getSinglePermission)
    Button getSinglePermission;
    @Bind(R.id.getMulPermission)
    Button getMulPermission;
    @Bind(R.id.permissions)
    TextView permissions;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_test);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.showPermissions, R.id.getSinglePermission, R.id.getMulPermission})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.showPermissions:
                

                break;
            case R.id.getSinglePermission:

                break;
            case R.id.getMulPermission:

                break;
        }
    }
}

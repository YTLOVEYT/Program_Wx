package com.example.program_wx.test;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.program_wx.R;
import com.example.program_wx.utils.LogUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThirdTestRxActivity extends AppCompatActivity
{
    private static final String TAG = "ThirdTestRxActivity";
    @Bind(R.id.showPermissions)
    Button showPermissions;
    @Bind(R.id.getSinglePermission)
    Button getSinglePermission;
    @Bind(R.id.getMulPermission)
    Button getMulPermission;
    @Bind(R.id.permissions)
    TextView permissions;


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

                Set<String> allPermissions = getAllPermissions();
                for (String mPermission : allPermissions)
                {
                    Log.e(TAG, "allPermissions: " + mPermission);
                }

                break;
            case R.id.getSinglePermission:
                List<String> requestPermissions = getPackagePermissions();
                for (String mPermission : requestPermissions)
                {
                    Log.e(TAG, "requestPermissions: " + mPermission);
                }
                break;
            case R.id.getMulPermission:

                break;
        }
    }

    /** 获取所有需要申请的权限 */
    private Set<String> getAllPermissions()
    {
        Set<String> mPermissions = new HashSet<String>();
        Field[] fields = Manifest.permission.class.getFields();
        for (Field field : fields)
        {
            String name = null;
            try
            {
                name = (String) field.get("");
            }
            catch (IllegalAccessException e)
            {
                Log.e(TAG, "Could not access field");
            }
            mPermissions.add(name);
        }
        return mPermissions;
    }

    /** 获取本包中需要申请的权限 */
    private List<String> getPackagePermissions()
    {
        PackageInfo packageInfo = null;
        List<String> list = new ArrayList<String>();
        try
        {
            LogUtil.e("packageName" + this.getPackageName());
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        if (packageInfo != null)
        {
            String[] permissions = packageInfo.requestedPermissions;
            if (permissions != null)
            {
                Collections.addAll(list, permissions);
            }
        }
        return list;
    }

    //请求权限
    private void requestRxPermissions(String... permissions)
    {
        RxPermissions rxPermissions = new RxPermissions(this);
    }
}

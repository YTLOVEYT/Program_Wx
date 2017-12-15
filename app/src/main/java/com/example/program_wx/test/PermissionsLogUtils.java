package com.example.program_wx.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 只有easy permission 有自带权限检查功能
 * Created by YinTao on 2017/12/12.
 */

public class PermissionsLogUtils
{
    private static StringBuffer logStringBuffer = new StringBuffer();

    public static String checkPermissions(Context context, String... permissions)
    {
        logStringBuffer.delete(0, logStringBuffer.length());
        for (String permission : permissions)
        {
            logStringBuffer.append(permission);
            logStringBuffer.append(" is applied? \n     ");
            logStringBuffer.append(isAppliedPermission(context, permission));
            logStringBuffer.append("\n\n");
        }
        return logStringBuffer.toString();
    }

    public static String easyCheckPermissions(Context context, String... permissions)
    {
        logStringBuffer.delete(0, logStringBuffer.length());
        for (String permission : permissions)
        {
            logStringBuffer.append(permission);
            logStringBuffer.append(" is applied? \n     ");
            logStringBuffer.append(EasyPermissions.hasPermissions(context, permission));

            logStringBuffer.append("\n\n");
        }
        return logStringBuffer.toString();
    }

    @TargetApi(23)
    private static boolean isAppliedPermission(Context context, String permission)
    {
        if (Build.VERSION.SDK_INT > 22) //区分对待
        {
            return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }
}

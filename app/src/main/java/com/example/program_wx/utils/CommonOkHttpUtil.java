package com.example.program_wx.utils;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.example.program_wx.MyConst;
import com.example.program_wx.entity.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共网络操作
 * Created by YinTao on 2017/12/12.
 */

public class CommonOkHttpUtil
{
    /** 上传登录时间 */
    public static void upLoadLoginTime(Context context)
    {
        LogUtil.e("上传时间");
        List<Param> params = new ArrayList<Param>();
        new OkHttpUtil(context).post(MyConst.URL_SEND_LOCAL_LOGIN_TIME, params, new OkHttpUtil.HttpCallBack()
        {
            @Override
            public void onResponse(String response)
            {
                if (response.startsWith("<!DOCTYPE html>"))
                {
                    return;
                }
                JSONObject jsonObject = JSONObject.parseObject(response);
                int code = jsonObject.getIntValue("code");
                switch (code)
                {
                    case 1:
                        LogUtil.e("上传成功");
                        break;
                    default:
                        LogUtil.e("上传失败");
                        break;
                }
            }

            @Override
            public void onFailure(String errorMsg)
            {
                LogUtil.e("上传失败");
            }
        });
    }
}

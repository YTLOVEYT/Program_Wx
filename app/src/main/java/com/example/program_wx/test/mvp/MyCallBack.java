package com.example.program_wx.test.mvp;

/**
 * Created by YinTao on 2017/12/14.
 */

public interface MyCallBack
{
    void onSuccess(String content);

    void onFailed(String error);

    void onProgress(int progress);
}

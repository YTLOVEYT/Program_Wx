package com.example.program_wx.activity.register;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.program_wx.MyConst;
import com.example.program_wx.entity.Param;
import com.example.program_wx.utils.LogUtil;
import com.example.program_wx.utils.OkHttpUtil;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录的Presenter
 * Created by YinTao on 2017/12/6.
 */

public class RegisterPresenter implements RegisterManager.Presenter
{
    private RegisterManager.View registerView;
    private String cropImagePath = null;//头像路径

    public RegisterPresenter(RegisterManager.View registerView)
    {
        this.registerView = registerView;
        registerView.setPresenter(this);
    }

    @Override
    public void requestServer(String username, String password, String nickName)
    {
        registerView.showDialog();
        if (cropImagePath != null && new File(cropImagePath).exists())
        {
            uploadAvatar(nickName, password, username, new File(cropImagePath).getAbsolutePath());
        }
        else
        {
            register(nickName, password, username, null);
        }


    }

    /**
     * 上传头像注册
     * @param nickName     昵称
     * @param password     密码
     * @param username     用户名
     * @param absolutePath 照片绝对路径
     */
    private void uploadAvatar(String nickName, String password, String username, String absolutePath)
    {

    }

    /**
     * 无头像注册
     * @param nickName  昵称
     * @param password  密码
     * @param username  用户名
     * @param imageName 默认照片名
     */
    private void register(String nickName, final String password, final String username, String imageName)
    {
        List<Param> params = new ArrayList<>();
        params.add(new Param("usertel", username));
        params.add(new Param("password", password));
        params.add(new Param("usernick", nickName));
        if (!TextUtils.isEmpty(imageName))
        {
            params.add(new Param("avatar", imageName));
        }
        new OkHttpUtil(registerView.getBaseActivity()).post(MyConst.URL_REGISTER, params, new OkHttpUtil.HttpCallBack()
        {
            @Override
            public void onResponse(String response)
            {
                JSONObject obj = JSON.parseObject(response);
                LogUtil.e("obj=" + obj.toString());
                int code = obj.getInteger("code");//返回码023
                switch (code)
                {
                    case 1:
                        JSONObject userObj = obj.getJSONObject("user");
                        if (userObj != null)
                        {
                            try
                            {
                                EMClient.getInstance().createAccount(username, password);//注册
                                LogUtil.e("EM注册成功");
                            }
                            catch (HyphenateException e)
                            {
                                e.printStackTrace();
                                LogUtil.e("registerCode=" + e.getErrorCode());
                                switch (e.getErrorCode())
                                {
                                    case EMError.USER_ALREADY_EXIST:
                                        registerView.getBaseActivity().runOnUiThread(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                registerView.showToast("用户已经存在");
                                                registerView.cancelDialog();
                                            }
                                        });

                                        break;
                                    case 208:
                                        registerView.getBaseActivity().runOnUiThread(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                registerView.showToast("注册失败");
                                                registerView.cancelDialog();
                                            }
                                        });
                                        break;
                                    default:
                                        registerView.getBaseActivity().runOnUiThread(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                registerView.showToast("未知错误");
                                                registerView.cancelDialog();
//                                                13782551324
//                                                        123456
//                                                15398878961
                                            }
                                        });
                                        break;
                                }
                                return;
                            }
                            registerView.getBaseActivity().runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    registerView.cancelDialog();
                                    registerView.showToast("注册成功");
                                    registerView.getBaseActivity().finish();
                                }
                            });
                        }
                        break;
                    case -1:
                        registerView.showToast("账号已被注册");
                        registerView.cancelDialog();
                        break;
                    case -2:
                        registerView.showToast("账号不正确");
                        registerView.cancelDialog();
                        break;
                    default:
                        registerView.showToast("服务器繁忙");
                        registerView.cancelDialog();
                        break;
                }
            }

            @Override
            public void onFailure(String errorMsg)
            {
                registerView.cancelDialog();
            }
        });
    }

    @Override
    public void start()
    {

    }

    @Override
    public void sendSmsCode(String mobile)
    {
        // FIXME: 2017/12/11 发送验证码验证
    }
}

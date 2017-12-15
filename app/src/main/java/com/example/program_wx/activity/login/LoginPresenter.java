package com.example.program_wx.activity.login;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.program_wx.MyConst;
import com.example.program_wx.activity.main.MainActivity;
import com.example.program_wx.dao.ContactsManager;
import com.example.program_wx.entity.Param;
import com.example.program_wx.entity.User;
import com.example.program_wx.utils.CommonOkHttpUtil;
import com.example.program_wx.utils.LogUtil;
import com.example.program_wx.utils.OkHttpUtil;
import com.example.program_wx.utils.SpUtil;
import com.example.program_wx.utils.UserUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录的Presenter
 * Created by YinTao on 2017/12/6.
 */

public class LoginPresenter implements LoginManager.Presenter
{
    private LoginManager.View loginView;

    public LoginPresenter(LoginManager.View loginView)
    {
        this.loginView = loginView;
        loginView.setPresenter(this);
    }

    @Override
    public void requestServer(final String username, final String password, final boolean isAuth)
    {
        loginView.showDialog();
        List<Param> params = new ArrayList<>();
        params.add(new Param("usertel", username));
        params.add(new Param("password", password));
        new OkHttpUtil(loginView.getBaseActivity()).post(MyConst.URL_LOGIN, params, new OkHttpUtil.HttpCallBack()
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
                        JSONArray friends = userObj.getJSONArray("friend");
                        if (userObj.containsKey("friend"))
                        {
                            userObj.remove("friend");
                        }
                        SpUtil.saveString(MyConst.SHARED_KEY_USER_INFO, userObj.toJSONString());//存储自己的信息 排除朋友
                        LogUtil.e("MyConst.SESSION=" + userObj.getString("session"));
                        SpUtil.saveString(MyConst.SESSION, userObj.getString("session"));//保存session
                        Map<String, User> userList = new HashMap<String, User>();
                        if (friends != null)
                        {
                            for (int i = 0; i < friends.size(); i++)
                            {
                                JSONObject friend = friends.getJSONObject(i);
                                User user = UserUtil.json2User(friend);
                                userList.put(user.getUsername(), user);
                            }
                            List<User> users = new ArrayList<User>(userList.values());
                            ContactsManager.getInstance().saveContactList(users);//登录后，重新加载联系人，保存到数据库
                        }
                        loginEM(username, password, userObj, isAuth);
                        break;
                    case -1:
                        loginView.showToast("账号不存在");
                        loginView.cancelDialog();
                        break;
                    case -2:
                        loginView.showToast("密码不正确");
                        loginView.cancelDialog();
                        break;
                    case -3:
                        loginView.showToast("账号被禁用");
                        loginView.cancelDialog();
                        break;
                    default:
                        loginView.showToast("服务器繁忙");
                        loginView.cancelDialog();
                        break;
                }
            }

            @Override
            public void onFailure(String errorMsg)
            {
                loginView.cancelDialog();
            }
        });

    }

    /** 登录环信服务器 */
    private void loginEM(final String username, final String password, final JSONObject userObj, final boolean isAuth)
    {
        CommonOkHttpUtil.upLoadLoginTime(loginView.getBaseActivity());//上传登录时间
        LogUtil.e("登录EM");
        EMClient.getInstance().login(username, password, new EMCallBack()
        {
            @Override
            public void onSuccess()
            {
                LogUtil.e("登录HT成功");
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                loginView.getBaseActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        loginView.cancelDialog();
                        if (isAuth)
                        {
                            loginView.getBaseActivity().setResult(Activity.RESULT_OK);
                            loginView.getBaseActivity().finish();
                        }
                        else
                        {
                            loginView.showToast("登录成功");
                            Intent intent = new Intent(loginView.getBaseActivity(),
                                    MainActivity.class);
                            loginView.getBaseActivity().startActivity(intent);
                            loginView.getBaseActivity().finish();
                        }
                    }
                });
            }

            @Override
            public void onError(int code, String error)
            {
                LogUtil.e("code" + code);
                switch (code)
                {
                    case 204: //用户不存在
                        loginView.getBaseActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                loginView.showToast("用户不存在");
                            }
                        });
                        break;
                    default:
                        loginView.getBaseActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                loginView.showToast("未知错误");
                            }
                        });
                        break;
                }
                loginView.cancelDialog();
            }

            @Override
            public void onProgress(int progress, String status)
            {

            }
        });
    }

    @Override
    public void start()
    {

    }
}

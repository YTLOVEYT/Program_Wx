package com.example.program_wx.activity.main.contacts;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.program_wx.MyConst;
import com.example.program_wx.dao.ContactsManager;
import com.example.program_wx.dao.UserDao;
import com.example.program_wx.entity.Param;
import com.example.program_wx.entity.User;
import com.example.program_wx.utils.LogUtil;
import com.example.program_wx.utils.OkHttpUtil;
import com.example.program_wx.utils.UserUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 联系人列表的操纵者
 * Created by YinTao on 2018/1/8.
 */

public class ContactPresenter implements BaseContactPresenter
{
    private ContactView contactView;
    private List<User> userList;

    public ContactPresenter(ContactView ContactView)
    {
        this.contactView = ContactView;
        contactView.setPresenter(this);
        userList = new ArrayList<>(ContactsManager.getInstance().getContactList().values());//从数据库中获取联系人
        // FIXME: 2018/1/15 添加邀请信息

    }

    @Override
    public List<User> getContactsListInDb()
    {
        userList = sortList(userList);
        return userList;
    }

    @Override
    public void deleteContacts(String userId)
    {
        Map<String, User> userMap = ContactsManager.getInstance().getContactList();
        User user = userMap.get(userId);
        if (user != null)
        {
            userMap.remove(user.getUsername());
            userList.remove(user);
            deleteContact(user);
        }
        else
        {
            // FIXME: 2018/1/15 用户不存在，重新刷新列表
            LogUtil.e("用户不存在");
        }
    }

    /** 发送请求删除联系人 */
    private void deleteContact(final User user)
    {
        final ProgressDialog dialog = new ProgressDialog(contactView.getBaseActivity());
        dialog.setMessage("正在删除。。。");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        List<Param> params = new ArrayList<>();//先删除服务器上的好友
        params.add(new Param(MyConst.JSON_KEY_HXID, user.getUsername()));
        new OkHttpUtil(contactView.getBaseActivity()).post(MyConst.URL_DELETE_FRIEND, params, new OkHttpUtil.HttpCallBack()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                dialog.dismiss();
                switch (response.getIntValue("code"))
                {
                    case 1:
                        // FIXME: 2018/1/15 删除数据库邀请信息
                        UserDao userDao = new UserDao(contactView.getBaseActivity());
                        userDao.deleteContact(user.getUsername());//删除数据库的联系人
                        try
                        {
                            EMClient.getInstance().contactManager().deleteContact(user.getUsername()); //删除IM
                        }
                        catch (HyphenateException e)
                        {
                            Toast.makeText(contactView.getBaseActivity(), "删除异常", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        Toast.makeText(contactView.getBaseActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(contactView.getBaseActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(String errorMsg)
            {
                dialog.dismiss();
                Toast.makeText(contactView.getBaseActivity(), "删除异常", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void moveUserToBlack(String userId)
    {

    }

    @Override
    public List<User> sortList(List<User> users)
    {
        Collections.sort(users, new Comparator<User>()
        {
            @Override
            public int compare(User u1, User u2)
            {
                String u1Letter = u1.getInitialLetter();
                String u2Letter = u2.getInitialLetter();
                if (u1Letter.equals(u2Letter))
                {
                    return u1.getNick().compareTo(u2.getNick());
                }
                else
                {
                    if ("#".equals(u1Letter))
                    {
                        return 1;
                    }
                    else if ("#".equals(u2Letter))
                    {
                        return -1;
                    }
                    return u1Letter.compareTo(u2Letter);
                }
            }
        });
        return users;
    }

    @Override
    public int getContactsCount()
    {
        return userList.size();
    }

    @Override
    public void clearInvitationCount()
    {

    }

    @Override
    public void refreshContactsInLocal()
    {

    }

    @Override
    public void refreshContactsInServer()
    {
        List<Param> params = new ArrayList<>();
        new OkHttpUtil(contactView.getBaseActivity()).post(MyConst.URL_FriendList, params, new OkHttpUtil.HttpCallBack()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                switch (response.getIntValue("code"))
                {
                    case 1:
                        JSONArray friends = response.getJSONArray("user");
                        ArrayList<User> users = new ArrayList<User>();
                        if (friends != null && friends.size() != 0)
                        {
                            for (int i = 0; i < friends.size(); ++i)
                            {
                                JSONObject friend = friends.getJSONObject(i);
                                User user = UserUtil.json2User(friend);
                                users.add(user);
                            }
                            ContactsManager.getInstance().saveContactList(users);
                            userList.clear();
                            userList.addAll(users);
                            contactView.refresh();
                        }
                        break;
                }
            }

            @Override
            public void onFailure(String errorMsg)
            {

            }
        });
    }

    @Override
    public void start()
    {

    }
}

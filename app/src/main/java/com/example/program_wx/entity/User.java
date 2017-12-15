package com.example.program_wx.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.example.program_wx.MyConst;
import com.example.program_wx.utils.UserUtil;

/**
 * 用户
 * Created by YinTao on 2017/12/10.
 */
public class User implements Parcelable
{
    /** 昵称首字母 排序使用 */
    private String initialLetter;
    /** 头像 */
    private String avatar;
    private String username;
    private String userInfo;
    private String nick;

    public User()
    {
    }

    public User(String username)
    {
        this.username = username;
    }


    public String getInitialLetter()
    {
        if (initialLetter == null)
        {
            UserUtil.setUserInitialLetter(this);
        }
        return initialLetter;
    }

    public void setInitialLetter(String initialLetter)
    {
        this.initialLetter = initialLetter;
    }

    public String getAvatar()
    {
        if (!TextUtils.isEmpty(avatar))
        {
            if (!avatar.contains("http"))
            {
                avatar = MyConst.URL_AVATAR + avatar;
            }
        }
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUserInfo()
    {
        return userInfo;
    }

    public void setUserInfo(String userInfo)
    {
        this.userInfo = userInfo;
    }

    public String getNick()
    {
        return nick;
    }

    public void setNick(String nick)
    {
        this.nick = nick;
    }

    @Override
    public String toString()
    {
        return nick == null ? username : nick;
    }

    /**
     * 用户名全局唯一
     * @param obj instanceof User
     * @return true/false
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof User))
        {
            return false;
        }
        return getUsername().equals(((User) obj).getUsername());
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

    }


}

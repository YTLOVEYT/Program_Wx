package com.example.program_wx.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.example.program_wx.MyConst;
import com.example.program_wx.entity.User;
import com.github.promeg.pinyinhelper.Pinyin;


/**
 * 用户操作工具类
 * Created by YinTao on 2017/12/11.
 */

public class UserUtil
{
    /**
     * 将userJson转化为一个实体类user
     * @param userJson json字符串
     */
    public static User json2User(JSONObject userJson)
    {
        User user = new User(userJson.getString(MyConst.JSON_KEY_HXID));//id
        user.setNick(userJson.getString(MyConst.JSON_KEY_NICK));//昵称
        user.setAvatar(userJson.getString(MyConst.JSON_KEY_AVATAR));//头像
        user.setUserInfo(userJson.toJSONString());
        UserUtil.setUserInitialLetter(user);
        return user;
    }

    /** 设置用户的初始字母 */
    public static void setUserInitialLetter(User user)
    {
        final String defaultLetter = "#";
        String letter = defaultLetter;
        if (!TextUtils.isEmpty(user.getNick()))
        {
            letter = Pinyin.toPinyin(user.getNick().toCharArray()[0]);
        }
        else
        {
            letter = Pinyin.toPinyin(user.getUsername().toCharArray()[0]);
        }
        user.setInitialLetter(letter.toUpperCase().substring(0, 1));
        if (CommonUtil.isNumeric(letter) || CommonUtil.check(user.getInitialLetter()))
        {
            user.setInitialLetter(defaultLetter);
        }
    }
}

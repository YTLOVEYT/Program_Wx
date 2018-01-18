package com.example.program_wx.activity.main.contacts;

import com.example.program_wx.activity.BasePresenter;
import com.example.program_wx.entity.User;

import java.util.List;

/**
 * 基础Contact的接口
 * Created by YinTao on 2018/1/8.
 */

public interface BaseContactPresenter extends BasePresenter
{
    /** 从数据库中读取联系人 */
    List<User> getContactsListInDb();

    /** 删除联系人 */
    void deleteContacts(String userId);

    /** 拉黑 */
    void moveUserToBlack(String userId);

    /** 排序联系人 */
    List<User> sortList(List<User> users);

    /** 获取联系人的数量 */
    int getContactsCount();

    /** 清除邀请信息的数量 */
    void clearInvitationCount();

    /** 本地刷新联系人 */
    void refreshContactsInLocal();

    /** 远程刷新联系人 */
    void refreshContactsInServer();
}

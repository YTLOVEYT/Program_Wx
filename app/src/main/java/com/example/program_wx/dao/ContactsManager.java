package com.example.program_wx.dao;

import android.content.Context;

import com.example.program_wx.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 数据库操作者
 * Created by YinTao on 2017/12/11.
 */

public class ContactsManager
{
    private static ContactsManager contactsManager;
    private UserDao userDao;
    private Map<String, User> contacts = null;

    public ContactsManager(Context context)
    {
        userDao = new UserDao(context);
        initContacts();
    }

    public static ContactsManager getInstance()
    {
        if (contactsManager == null)
        {
            throw new RuntimeException("未初始化");
        }
        return contactsManager;
    }


    /** 初始化联系人 */
    private void initContacts()
    {
        contacts = userDao.getContactList();
    }

    /** 全局数据库操作 */
    public static void init(Context context)
    {
        if (contactsManager == null)
        {
            synchronized (ContactsManager.class)
            {
                if (contactsManager == null)
                {
                    contactsManager = new ContactsManager(context);
                }
            }
        }
    }

    /** 保存联系人 */
    public boolean saveContactList(List<User> contactList)
    {
        contacts.clear();
        for (User user : contactList)
        {
            contacts.put(user.getUsername(), user);
        }
        userDao.saveContactList(contactList);
        return true;
    }

    /** 获取联系人 */
    public Map<String, User> getContactList()
    {
        if (contacts == null)
        {
            contacts = userDao.getContactList();
        }
        return contacts;
    }

    /** 保存联系人 */
    public void saveContact(User user)
    {
        contacts.put(user.getUsername(), user);
        userDao.saveContact(user);
    }

    public void deleteContact(String username)
    {
        userDao.deleteContact(username);
    }


}

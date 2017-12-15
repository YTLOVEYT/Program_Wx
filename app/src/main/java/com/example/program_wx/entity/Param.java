package com.example.program_wx.entity;

import java.io.Serializable;

/**
 * 参数
 * Created by YinTao on 2017/12/8.
 */

public class Param implements Serializable
{
    private String key;
    private String value;

    public Param(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "Param{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

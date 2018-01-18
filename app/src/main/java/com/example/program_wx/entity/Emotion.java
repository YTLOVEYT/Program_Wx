package com.example.program_wx.entity;

import android.support.annotation.NonNull;

/**
 * 表情的entity
 * Created by YinTao on 2018/1/2.
 */

public class Emotion
{
    private String identifyCode;
    private int icon;
    private int bigIcon;
    private String emotionText;
    private String name;
    private EmotionType type;
    private String iconPath;
    private String bigIconPath;

    public Emotion()
    {
    }

    public Emotion(int icon, @NonNull String emotionText, EmotionType type)
    {
        this.icon = icon;
        this.emotionText = emotionText;
        this.type = type;
    }

    public String getIdentifyCode()
    {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode)
    {
        this.identifyCode = identifyCode;
    }

    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public int getBigIcon()
    {
        return bigIcon;
    }

    public void setBigIcon(int bigIcon)
    {
        this.bigIcon = bigIcon;
    }

    @NonNull
    public String getEmotionText()
    {
        return emotionText;
    }

    public void setEmotionText(String emotionText)
    {
        this.emotionText = emotionText;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public EmotionType getType()
    {
        return type;
    }

    public void setType(EmotionType type)
    {
        this.type = type;
    }

    public String getIconPath()
    {
        return iconPath;
    }

    public void setIconPath(String iconPath)
    {
        this.iconPath = iconPath;
    }

    public String getBigIconPath()
    {
        return bigIconPath;
    }

    public void setBigIconPath(String bigIconPath)
    {
        this.bigIconPath = bigIconPath;
    }

    public enum EmotionType
    {
        NORMAL,
        BIG_EXPRESSION
    }
}

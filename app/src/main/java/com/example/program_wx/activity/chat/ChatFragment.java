package com.example.program_wx.activity.chat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.program_wx.R;
import com.example.program_wx.activity.chat.widget.ChatInputView;
import com.liaoinstan.springview.widget.SpringView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment
{
    private ListView lvConversations;
    private SpringView springView;
    private LinearLayout llVoice, llKeyBroad, llEmotions, llMore;
    private Button btShowVoice, btPressVoice, btShowKeyBroad, btEmotion, btMore;
    private ViewPager vpEmotions, vpExtend;
    private TabLayout tabLayout;

    private ChatInputView chatInputView;

    private static int[] itemNamesSingle = {R.string.attach_take_pic, R.string.attach_picture, R.string.attach_location, R.string.attach_video, R.string.attach_video_call, R.string.attach_file, R.string.attach_red, R.string.attach_transfer};
    private static int[] itemIconsSingle = {R.drawable.chat_takepic_selector, R.drawable.chat_image_selector, R.drawable.chat_location_selector, R.drawable.chat_video_selector, R.drawable.chat_video_call_selector, R.drawable.chat_file_selector, R.drawable.type_redpacket, R.drawable.type_transfer};
    private static int[] itemNamesGroup = {R.string.attach_take_pic, R.string.attach_picture, R.string.attach_location, R.string.attach_video, R.string.attach_video_call, R.string.attach_file, R.string.attach_red};
    private static int[] itemIconsGroup = {R.drawable.chat_takepic_selector, R.drawable.chat_image_selector, R.drawable.chat_location_selector, R.drawable.chat_video_selector, R.drawable.chat_video_call_selector, R.drawable.chat_file_selector, R.drawable.type_redpacket};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        initViews(view);
        setListeners();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        String userId = arguments.getString("userId");
        int chatType = arguments.getInt("chatType", ChatManager.SINGLE);
        initViews(userId, chatType);

    }

    /** 初始化页面 */
    private void initViews(String userId, int chatType)
    {
        if (chatType == ChatManager.SINGLE)
        {
            chatInputView.initView(getActivity(), springView, itemNamesSingle, itemIconsSingle);
        }
        else
        {
            chatInputView.initView(getActivity(), springView, itemNamesGroup, itemIconsGroup);
        }

    }

    /** 初始化控件 */
    private void initViews(View view)
    {
        lvConversations = (ListView) view.findViewById(R.id.list);
        springView = (SpringView) view.findViewById(R.id.springView);
        chatInputView = (ChatInputView) view.findViewById(R.id.inputView);

        btShowVoice = (Button) chatInputView.findViewById(R.id.bt_voice);
        btPressVoice = (Button) view.findViewById(R.id.bt_input_voice);
        btShowKeyBroad = (Button) view.findViewById(R.id.bt_keyBroad);
        btEmotion = (Button) view.findViewById(R.id.bt_emotions);
        btMore = (Button) view.findViewById(R.id.bt_more);
        llVoice = (LinearLayout) view.findViewById(R.id.ll_voice);//语音版
        llKeyBroad = (LinearLayout) view.findViewById(R.id.ll_keyBroad);//输入版
        llEmotions = (LinearLayout) view.findViewById(R.id.ll_emotions);
        llMore = (LinearLayout) view.findViewById(R.id.ll_more);
        vpEmotions = (ViewPager) view.findViewById(R.id.vp_emotions);
        vpExtend = (ViewPager) view.findViewById(R.id.vp_extend);
        tabLayout = (TabLayout) view.findViewById(R.id.tabEmotion);
    }

    /** 设置监听 */
    private void setListeners()
    {

    }

}

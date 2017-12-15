package com.example.program_wx.activity.main.conversation;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.program_wx.R;
import com.example.program_wx.utils.LogUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.List;
import java.util.Map;

/**
 * FragmentConversation
 */
public class FragmentConversation extends Fragment implements ConversationView
{
    private ListView listView;
    public LinearLayout errorItem, tipItem;
    private TextView tvError, tvTip;
    private ConversationAdapter adapter;
    private ConversationPresenter conversationPresenter;

    /** activity与fragment关联时 */
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        LogUtil.e("1-onAttach");
        conversationPresenter = new ConversationPresenter(this);//新建Presenter

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LogUtil.e("3-onCreateView");
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        initView(view);
        setListener();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        LogUtil.e("4-onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        conversationPresenter = new ConversationPresenter(this);

        adapter = new ConversationAdapter(getActivity(), (List<EMConversation>) conversationPresenter.getAllConversations().values());
        listView.setAdapter(adapter);

        Map<String, EMConversation> allConversations = EMClient.getInstance().chatManager().getAllConversations();//加载所有的会话

    }

    @Override
    public void onResume()
    {
        super.onResume();
        LogUtil.e("5-onResume");
        refresh();//刷新
    }

    /** 初始化控件 */
    private void initView(View view)
    {
        listView = (ListView) view.findViewById(R.id.list);
        errorItem = (LinearLayout) view.findViewById(R.id.layout_error);
        tipItem = (LinearLayout) view.findViewById(R.id.layout_tip);
        tvError = (TextView) view.findViewById(R.id.error_content);
        tvTip = (TextView) view.findViewById(R.id.notice_content);
    }

    /** 设置监听 */
    private void setListener()
    {
        tipItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // FIXME: 2017/12/14 通知界面
            }
        });
        errorItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // FIXME: 2017/12/14 跳转到设置界面
            }
        });
    }

    @Override
    public void setPresenter(ConversationPresenter presenter)
    {

    }

    @Override
    public void showItemDialog(EMConversation conversation)
    {

    }

    @Override
    public void refresh()
    {

    }

    @Override
    public Activity getBaseContext()
    {
        return getActivity();
    }
}

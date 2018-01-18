package com.example.program_wx.activity.main.conversation;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.program_wx.MyApp;
import com.example.program_wx.MyConst;
import com.example.program_wx.R;
import com.example.program_wx.activity.chat.ChatActivity;
import com.example.program_wx.activity.chat.ChatManager;
import com.example.program_wx.utils.LogUtil;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

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
    private NewMessageListener newMessageListener;

    /** activity与fragment关联时 */
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        LogUtil.e("1-onAttach");
        conversationPresenter = new ConversationPresenter(this);//新建Presenter
        if (context instanceof NewMessageListener)
        {
            newMessageListener = (NewMessageListener) context;
            newMessageListener.onUnReadMessages(conversationPresenter.getUnreadMsgCount());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LogUtil.e("2-onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LogUtil.e("3-onCreateView");
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        initView(view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        LogUtil.e("4-onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        // FIXME: 2017/12/18 加载通知消息

        adapter = new ConversationAdapter(getActivity(), conversationPresenter.getAllConversations());
        listView.setAdapter(adapter);
        setListener();
        registerBroadCastAndMsgListener();
    }
    /**重新设置适配器*/
    private void setAdapter()
    {
        adapter = new ConversationAdapter(getActivity(), conversationPresenter.getAllConversations());
        listView.setAdapter(adapter);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        LogUtil.e("5-onResume");
        refresh();//刷新
    }

    @Override
    public void onDestroy()
    {
        unRegisterBroadCastAndMsgListener();
        super.onDestroy();
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                EMConversation conversation = adapter.getItem(position);
                conversationPresenter.markMessageRead(conversation);//标记为已读
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("userId", conversation.conversationId());
                if (conversation.isGroup())
                {
                    intent.putExtra("chatType", ChatManager.GROUP);
                }
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                EMConversation conversation = adapter.getItem(position);
                showItemDialog(conversation);
                return true;
            }
        });
    }

    @Override
    public void setPresenter(ConversationPresenter presenter)
    {
        this.conversationPresenter = presenter;
    }

    @Override
    public void showItemDialog(final EMConversation conversation)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.layout_dialog_items, null);
        LinearLayout layout1 = (LinearLayout) view.findViewById(R.id.ll_content1);
        LinearLayout layout2 = (LinearLayout) view.findViewById(R.id.ll_content2);
        LinearLayout layout3 = (LinearLayout) view.findViewById(R.id.ll_content3);
        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.VISIBLE);
        layout3.setVisibility(View.VISIBLE);
        TextView tv1 = (TextView) view.findViewById(R.id.tv_content1);
        TextView tv2 = (TextView) view.findViewById(R.id.tv_content2);
        TextView tv3 = (TextView) view.findViewById(R.id.tv_content3);
        tv1.setText("标记为未读");
        tv2.setText("置顶聊天");
        tv3.setText("删除该聊天");
        final AlertDialog dialog = builder.setView(view).show();
        layout1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getActivity(), "标记为未读", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        layout2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getActivity(), "置顶聊天", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        layout3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                conversationPresenter.deleteConversation(conversation);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void refresh()
    {
        conversationPresenter.refreshConversations();
        LogUtil.e("notifyDataSetChanged");
        // FIXME: 2017/12/25 使用adapter.notifyDataChanged()方法失效 ，也许是数据源引用不是同一个位置
        setAdapter();
//        adapter.notifyDataSetChanged();
        //更新主界面的数字显示
        newMessageListener.onUnReadMessages(conversationPresenter.getUnreadMsgCount());
    }

    @Override
    public Activity getBaseContext()
    {
        return getActivity();
    }

    /** FragmentConversation的消息监听接口 */
    public interface NewMessageListener
    {
        //返回多少条未读消息
        void onUnReadMessages(int count);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            LogUtil.e("收到消息");
            String action = intent.getAction();
            if (MyConst.RECEIVE_NEW_MESSAGE.equals(action))//新消息
            {
                LogUtil.e("收到新消息");
                refresh();
            }
            else if (MyConst.RECEIVE_CMD_MESSAGE.equals(action)) //透传消息
            {
                LogUtil.e("收到透传消息");
            }
            else
            {
                LogUtil.e("收到其他消息");
            }
        }
    };

    /** 注册广播 */
    private void registerBroadCastAndMsgListener()
    {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyConst.RECEIVE_NEW_MESSAGE);//新消息的广播
        LocalBroadcastManager.getInstance(MyApp.getAppContext()).registerReceiver(receiver, intentFilter);
    }

    /** 反注册广播 */
    private void unRegisterBroadCastAndMsgListener()
    {
        LocalBroadcastManager.getInstance(MyApp.getAppContext()).unregisterReceiver(receiver);
    }
}

package com.example.program_wx.activity.main.contacts;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.program_wx.R;
import com.example.program_wx.entity.User;
import com.liaoinstan.springview.widget.SpringView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentContacts extends Fragment implements ContactView, View.OnClickListener
{
    private ListView listView;
    private SpringView springView;
    private LinearLayout llLoading;
    private ContactPresenter contactPresenter;
    private contactsListener contactsListener;
    private ContactsAdapter contactsAdapter;
    private TextView tvFriendUnread, tvTotalContact;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        contactPresenter = new ContactPresenter(this);
        if (context instanceof contactsListener) //MainActivity实现该接口
        {
            contactsListener = (FragmentContacts.contactsListener) context;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        initView(view);
        return view;
    }

    /** 加载数据 */
    private void initData()
    {
        contactsAdapter = new ContactsAdapter(getActivity(), contactPresenter.getContactsListInDb());
        listView.setAdapter(contactsAdapter);
        contactPresenter.refreshContactsInServer();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initData();
        setListener();

    }

    private void setListener()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {

                return false;
            }
        });

    }

    private void initView(View view)
    {
        listView = (ListView) view.findViewById(R.id.list);
        springView = (SpringView) view.findViewById(R.id.contentFrame);
        llLoading = (LinearLayout) view.findViewById(R.id.ll_loading);
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.item_contact_list_header, null);
        View footer = LayoutInflater.from(getActivity()).inflate(R.layout.item_contact_list_footer, null);
        listView.addHeaderView(header);
        listView.addFooterView(footer);
        tvFriendUnread = (TextView) header.findViewById(R.id.tv_unread);
        tvTotalContact = (TextView) footer.findViewById(R.id.tv_total);
        showSideBar();
        header.findViewById(R.id.re_newfriends).setOnClickListener(this); //header的点击监听
        header.findViewById(R.id.re_tag).setOnClickListener(this);
        header.findViewById(R.id.re_chatroom).setOnClickListener(this);
        header.findViewById(R.id.re_public).setOnClickListener(this);
    }

    private void showSideBar()
    {

    }

    @Override
    public void setPresenter(ContactPresenter presenter)
    {

    }

    @Override
    public void refresh()
    {
        contactsAdapter.notifyDataSetChanged();
        showContactCount(contactPresenter.getContactsCount());
    }

    @Override
    public void showContactCount(int count)
    {
        tvTotalContact.setText(count + "个联系人");
    }

    @Override
    public void showInvitationCount()
    {

    }

    @Override
    public void showItemDialog(User user)
    {

    }

    @Override
    public Activity getBaseActivity()
    {
        return getActivity();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.re_newfriends:

                break;
            case R.id.re_chatroom:

                break;
            case R.id.re_tag:

                break;
            case R.id.re_public:

                break;
        }
    }

    public interface contactsListener
    {
        /** 显示邀请信息 */
        void showInvitation(int count);
    }

}

package com.example.program_wx.activity.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.program_wx.R;
import com.example.program_wx.activity.BaseActivity;
import com.example.program_wx.activity.main.contacts.FragmentContacts;
import com.example.program_wx.activity.main.conversation.FragmentConversation;
import com.example.program_wx.activity.main.find.FragmentFind;
import com.example.program_wx.activity.main.profile.FragmentProfile;
import com.example.program_wx.utils.LogUtil;
import com.example.program_wx.widget.NoAnimViewPager;

import java.lang.reflect.Method;

public class MainActivity extends BaseActivity implements MainView
{
    private Toolbar toolbar;
    private NoAnimViewPager viewPager;
    private TabLayout tabLayout;

    private Fragment[] fragments;//容器
    private TabLayout.Tab[] tabs;
    private String[] tabsStr = new String[]{"会话", "通讯录", "发现", "我"};
    private int[] tabDrawables = new int[]{R.drawable.tab_chat_bg, R.drawable.tab_contact_list_bg, R.drawable.tab_find_bg, R.drawable.tab_profile_bg};
    //新消息角标
    private TextView unreadLabel;
    // 新好友申请消息角标
    public TextView unreadInvitationLabel;
    //朋友圈通知
    public TextView unreadFriendLabel;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // FIXME: 2017/12/13 解决账号冲突问题
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Wx");
        initViews();
        requestPermissions();
        setListeners();
        mainPresenter = new MainPresenter(this);

    }

    /** 请求权限 */
    private void requestPermissions()
    {
        // FIXME: 2017/12/13
    }

    /** 设置页面监听 */
    private void setListeners()
    {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.groupChat:
                        Toast.makeText(MainActivity.this, "groupChat", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.addFriends:
                        Toast.makeText(MainActivity.this, "addFriends", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.scan:

                        break;
                    case R.id.money:

                        break;
                    case R.id.help:

                        break;
                }
                return true;
            }
        });
    }

    /** 初始化所有控件 */
    private void initViews()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (NoAnimViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Wx");
        fragments = new Fragment[]{new FragmentConversation(), new FragmentContacts(), new FragmentFind(), new FragmentProfile()};
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                return fragments[position];
            }

            @Override
            public int getCount()
            {
                return fragments.length;
            }
        });
        tabLayout.setupWithViewPager(viewPager);//关联
        tabs = new TabLayout.Tab[tabsStr.length];
        for (int i = 0; i < tabsStr.length; i++)
        {
            tabs[i] = tabLayout.getTabAt(i);//赋值 tabs=0 1 2 3
            tabs[i].setCustomView(customBottomView(i, tabDrawables[i]));
        }
    }

    /** 初始化tabView */
    private View customBottomView(final int index, int drawable)
    {
        View view = getLayoutInflater().inflate(R.layout.tab_bottom, null);
        Button button = (Button) view.findViewById(R.id.tab_button);
        button.setCompoundDrawablesWithIntrinsicBounds(0, drawable, 0, 0);
        button.setText(tabsStr[index]);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tabs[index].select();
            }
        });
        if (isCompatible(Build.VERSION_CODES.LOLLIPOP)) //21 5.0
        {
            button.setStateListAnimator(null);
        }
        TextView textView = (TextView) view.findViewById(R.id.unread_msg_number);
        if (index == 0)
        {
            unreadLabel = textView;
        }
        else if (index == 1)
        {
            unreadInvitationLabel = textView;
        }
        else if (index == 2)
        {
            unreadFriendLabel = textView;
        }
        return view;
    }

    /** 版本兼容 */
    private boolean isCompatible(int apiLevel)
    {
        return Build.VERSION.SDK_INT >= apiLevel;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        LogUtil.e("onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu)
    {
        LogUtil.e("onPrepareOptionsPanel");
        if (menu != null)
        {
            if (menu.getClass() == MenuBuilder.class)
            {
                try
                {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch (Exception e)
                {
                    LogUtil.e("optionMenu显示不出图标");
                    e.printStackTrace();
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        LogUtil.e("onOptionsItemSelected");
        switch (item.getItemId())
        {
            case R.id.groupChat:

                break;
            case R.id.addFriends:

                break;
            case R.id.scan:
                Toast.makeText(MainActivity.this, "scan", Toast.LENGTH_SHORT).show();
                break;
            case R.id.money:
                Toast.makeText(MainActivity.this, "money", Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:

                break;
        }
        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // FIXME: 2017/12/14 检查升级 不适合，太过频繁
        LogUtil.e("请求升级");

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }

    /**
     * 登出 注销环信，注销账号
     */
    private void logout()
    {
        // FIXME: 2017/12/13
    }


    @Override
    public void setPresenter(MainPresenter presenter)
    {
        this.mainPresenter = presenter;
    }

    @Override
    public void showUpdateDialog(String message, final String url, final String isForce)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = View.inflate(MainActivity.this, R.layout.layout_tipdialog, null);
        TextView tv_delete_people = (TextView) dialogView.findViewById(R.id.tv_delete_people);
        View view_line_dialog = dialogView.findViewById(R.id.view_line_dialog);
        TextView tv_delete_title = (TextView) dialogView.findViewById(R.id.tv_delete_title);
        TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
        TextView tv_ok = (TextView) dialogView.findViewById(R.id.tv_ok);
        tv_delete_title.setText("新版本");
        tv_delete_people.setText(message);
        tv_cancel.setText("稍后提醒我");
        tv_ok.setText("升级");
        builder.setView(dialogView);
        final AlertDialog dialog = builder.show();
        if ("1".equals(isForce))
        {
            view_line_dialog.setVisibility(View.GONE);
            tv_cancel.setVisibility(View.GONE);
            tv_ok.setText("强制升级");
            dialog.setCancelable(false);//点击屏幕外不取消  返回键也没用
            dialog.setCanceledOnTouchOutside(false); //点击屏幕外取消,返回键有用
        }
        else
        {
            dialog.setCancelable(true);//点击屏幕外取消  返回键也没用
            dialog.setCanceledOnTouchOutside(true); //点击屏幕外取消,返回键有用
        }
        tv_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                if (isForce.equals("1"))
                {
                    logout();
                }
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!isForce.equals("1"))
                {
                    dialog.dismiss();
                }
                //发送升级安装请求
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                MainActivity.this.startActivity(intent);
            }
        });


    }

    @Override
    public Activity getContext()
    {
        return this;
    }
}

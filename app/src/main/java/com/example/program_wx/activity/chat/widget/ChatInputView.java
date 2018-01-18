package com.example.program_wx.activity.chat.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.program_wx.R;
import com.example.program_wx.activity.chat.widget.emotion.EmotionFragment;
import com.example.program_wx.activity.chat.widget.extend.ChatExtend;
import com.example.program_wx.entity.Emotion;
import com.example.program_wx.entity.EmotionData;
import com.example.program_wx.utils.CommonUtil;
import com.example.program_wx.utils.LogUtil;
import com.example.program_wx.utils.SpUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 输入控件
 * Created by YinTao on 2018/1/1.
 */

public class ChatInputView extends LinearLayout implements View.OnClickListener
{
    private Context context;
    private int[] itemNames, itemIcons;
    private View contentView;//内容布局
    private ViewPager vpEmotion, vpExtend;
    private LinearLayout llMore, llEmotion, llExtend, llPressToInput, llPressToSpeak;
    private Button btVoice, btPressVoice, btKeyBoard, btEmotion, btEmotionChecked, btMore, btSend;
    private EditText etMsg;
    private TabLayout tabEmotion, tabExtends;
    private InputMethodManager inputMethodManager;
    private chatInputViewListener chatInputViewListener;


    public ChatInputView(Context context)
    {
        super(context);
        init(context);
    }

    public ChatInputView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public ChatInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.inputview, this);
    }

    /** 页面初始化 */
    public void initView(Activity activity, View freshView, int[] itemNames, int[] itemIcons)
    {
        this.itemNames = itemNames;
        this.itemIcons = itemIcons;
        contentView = freshView;
        btVoice = (Button) this.findViewById(R.id.bt_voice);
        btPressVoice = (Button) this.findViewById(R.id.bt_input_voice);
        btKeyBoard = (Button) this.findViewById(R.id.bt_keyBroad);
        etMsg = (EditText) this.findViewById(R.id.et_input_message);
        llPressToInput = (LinearLayout) this.findViewById(R.id.ll_keyBroad);
        llPressToSpeak = (LinearLayout) this.findViewById(R.id.ll_voice);
        btEmotion = (Button) this.findViewById(R.id.bt_emotions);
        btEmotionChecked = (Button) this.findViewById(R.id.bt_emotions_check);
        btMore = (Button) this.findViewById(R.id.bt_more);
        btSend = (Button) this.findViewById(R.id.btn_send);
        llMore = (LinearLayout) this.findViewById(R.id.ll_more);
        llEmotion = (LinearLayout) this.findViewById(R.id.ll_emotions);
        llExtend = (LinearLayout) this.findViewById(R.id.ll_extend);
        vpEmotion = (ViewPager) this.findViewById(R.id.vp_emotions);
        vpExtend = (ViewPager) this.findViewById(R.id.vp_extend);
        tabEmotion = (TabLayout) this.findViewById(R.id.tabEmotion);
        tabExtends = (TabLayout) this.findViewById(R.id.tb_extend);

        initEmotions(activity);
        initExtendView();
        setClickListener();
        initSoftInput(activity);
    }

    /** 初始化软键盘 */
    private void initSoftInput(Activity activity)
    {
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        hideSoftInput();
    }

    /** 设置页面监听 */
    private void setClickListener()
    {
        btVoice.setOnClickListener(this);
        btKeyBoard.setOnClickListener(this);
        btEmotion.setOnClickListener(this);
        btEmotionChecked.setOnClickListener(this);
        btMore.setOnClickListener(this);
        btSend.setOnClickListener(this);
        etMsg.requestFocus();
        etMsg.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_UP)//手抬起的时候
                {
                    hideMore();
                    showSoftInput();
                    return true;
                }
                return false;
            }
        });
        etMsg.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }


    /** 释放内容区域 */
    private void unlockContentHeightDelayed()
    {
        etMsg.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                ((LinearLayout.LayoutParams) contentView.getLayoutParams()).weight = 1.0F;
            }
        }, 200L);
    }

    /** 判断软键盘是否显示 */
    private boolean isSoftInputShown()
    {
        LogUtil.e("软键盘=" + inputMethodManager.isActive());
        return CommonUtil.getSoftInputHeight((Activity) getContext()) != 0;
    }

    /** 锁定内容区高度 */
    private void lockContentHeight()
    {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        params.height = contentView.getHeight();
        params.weight = 0.0F;
    }

    /** 显示软键盘 */
    private void showSoftInput()
    {
        etMsg.requestFocus();
        etMsg.post(new Runnable()
        {
            @Override
            public void run()
            {
                inputMethodManager.showSoftInput(etMsg, 0);
            }
        });
    }

    /** 手动隐藏软键盘 */
    private void hideSoftInput()
    {
        inputMethodManager.hideSoftInputFromWindow(etMsg.getWindowToken(), 0);
    }

    /** 初始化扩展区 */
    private void initExtendView()
    {
        ChatExtend.ExtendMenuItemClickListener menuItemClickListener = new ChatExtend.ExtendMenuItemClickListener()
        {
            @Override
            public void onItemClick(int id, View view)
            {
                switch (id)
                {
                    case 0:
                        Toast.makeText(context, "拍照", Toast.LENGTH_SHORT).show();
                        if (chatInputViewListener != null)
                        {
                            chatInputViewListener.onPhotoItemClicked();
                        }
                        break;
                    case 1:
                        if (chatInputViewListener != null)
                        {
                            chatInputViewListener.onAlbumItemClicked();
                        }
                        break;
                    case 2:
                        if (chatInputViewListener != null)
                        {
                            chatInputViewListener.onLocationItemClicked();
                        }
                        break;
                    case 3:
                        if (chatInputViewListener != null)
                        {
                            chatInputViewListener.onVideoItemClicked();
                        }
                        break;
                    case 4:
                        if (chatInputViewListener != null)
                        {
                            chatInputViewListener.onCallItemClicked();
                        }
                        break;
                    case 5:
                        if (chatInputViewListener != null)
                        {
                            chatInputViewListener.onFileItemClicked();
                        }
                        break;
                    case 6:
                        if (chatInputViewListener != null)
                        {
                            chatInputViewListener.onRedPackageItemClicked();
                        }
                        break;
                    case 7:
                        if (chatInputViewListener != null)
                        {
                            chatInputViewListener.onTransferItemClicked();
                        }
                        break;
                    default:

                        break;
                }
            }
        };
        ChatExtend chatExtend = new ChatExtend(context);
        for (int i = 0; i < itemNames.length; ++i) //更新数据
        {
            chatExtend.initChatExtend(itemNames[i], itemIcons[i], i, menuItemClickListener);
        }
        chatExtend.init();//设置适配器
        final ArrayList<View> views = new ArrayList<>();
        views.add(chatExtend);
        vpExtend.setAdapter(new PagerAdapter()
        {
            @Override
            public int getCount()
            {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object)
            {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {
                ((ViewPager) container).addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object)
            {
                ((ViewPager) container).removeView(views.get(position));
            }
        });
    }

    /** 初始化表情区 */
    private void initEmotions(Activity activity)
    {
        llEmotion.setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING | LinearLayout.SHOW_DIVIDER_MIDDLE);
        llEmotion.setDividerDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_horizontal));//设置分割线

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new EmotionFragment());
        vpEmotion.setAdapter(new FragmentPagerAdapter(((AppCompatActivity) activity).getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                EmotionFragment fragment = (EmotionFragment) fragments.get(position);
                fragment.initEmotionFragment(Arrays.asList(EmotionData.getDATA()), 7, 3, new EmotionFragment.OnEmotionClickListener()
                {
                    @Override
                    public void onDeleteImageClicked()
                    {
                        // FIXME: 2018/1/4 删除表情
                    }

                    @Override
                    public void onEmotionClicked(Emotion emotion)
                    {
                        // FIXME: 2018/1/4 在输入框中添加表情
                    }
                });
                return fragment;
            }

            @Override
            public int getCount()
            {
                return fragments.size();
            }
        });
        tabEmotion.setupWithViewPager(vpEmotion);
        TabLayout.Tab[] tabs = new TabLayout.Tab[fragments.size()];
        for (int i = 0; i < fragments.size(); ++i)
        {
            tabs[i] = tabEmotion.getTabAt(i);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.ee_0);
            tabs[i].setCustomView(imageView);
        }
        LinearLayout childLinearLayout = (LinearLayout) tabEmotion.getChildAt(0);
        childLinearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING | LinearLayout.SHOW_DIVIDER_MIDDLE);
        childLinearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_horizontal));//设置分割线
    }

    /** 显示表情包 */
    private void showEmotionLayout()
    {
        hideSoftInput();
        llMore.setVisibility(VISIBLE);
        llExtend.setVisibility(GONE);
        llEmotion.setVisibility(VISIBLE);
    }

    /** 隐藏表情包 */
    private void hideEmotionLayout()
    {
        btEmotion.setVisibility(VISIBLE);
        btEmotionChecked.setVisibility(GONE);

    }

    /** more显示时拦截后退键 */
    private boolean interceptBackPress()
    {
        if (llMore.isShown())
        {
            llEmotion.setVisibility(GONE);
            llExtend.setVisibility(GONE);
            btEmotion.setVisibility(VISIBLE);
            btEmotionChecked.setVisibility(GONE);
            llMore.setVisibility(GONE);
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_voice:
                showVoiceRecodingView();
                break;
            case R.id.bt_keyBroad:
                if (chatInputViewListener != null)
                {
                    chatInputViewListener.onEditTextUp();
                }
                showInputView();
                hideMore();
                showSoftInput();
                break;
            case R.id.bt_emotions:
                showInputView();
                if (chatInputViewListener != null)
                {
                    chatInputViewListener.onEditTextUp();
                }
                btEmotion.setVisibility(GONE);
                llExtend.setVisibility(GONE);
                btEmotionChecked.setVisibility(VISIBLE);
                if (isSoftInputShown()) //显示了输入法
                {
                    lockContentHeight();
                    llEmotion.setVisibility(VISIBLE);
                    llMore.setVisibility(VISIBLE);
                    llExtend.setVisibility(GONE);
                    hideSoftInput();
                    unlockContentHeightDelayed();
                }
                else
                {
                    llExtend.setVisibility(GONE);
                    llEmotion.setVisibility(VISIBLE);
                    llMore.setVisibility(VISIBLE);
                    int softInputHeight = CommonUtil.getSoftInputHeight((Activity) getContext());
                    if (softInputHeight == 0)
                    {
                        softInputHeight = SpUtil.getInt("KEYBOARD_HEIGHT");
                        LogUtil.e("没有获取到高度" + softInputHeight);
                    }
                    hideSoftInput();
                    llMore.getLayoutParams().height = softInputHeight;
                    llMore.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.bt_emotions_check:
                showInputView();
                btEmotionChecked.setVisibility(GONE);
                btEmotion.setVisibility(VISIBLE);
                hideMore();
                showSoftInput();
                break;
            case R.id.btn_send:
                // FIXME: 2018/1/8 发送数据
                break;
            case R.id.bt_more:
                if (isSoftInputShown())
                {
                    lockContentHeight();
                    showExtend();
                    unlockContentHeightDelayed();
                }
                else
                {
                    int softInputHeight = CommonUtil.getSoftInputHeight((Activity) getContext());
                    if (softInputHeight == 0)
                    {
                        softInputHeight = SpUtil.getInt("KEYBOARD_HEIGHT");
                        LogUtil.e("没有获取到高度" + softInputHeight);
                    }
                    hideSoftInput();
                    llMore.getLayoutParams().height = softInputHeight;
                    llMore.setVisibility(View.VISIBLE);
                    llEmotion.setVisibility(GONE);
                    llExtend.setVisibility(VISIBLE);
                }
                break;
        }
    }

    /** 显示扩展页面 */
    private void showExtend()
    {
        llMore.setVisibility(VISIBLE);
        llEmotion.setVisibility(GONE);
        btEmotion.setVisibility(VISIBLE);
        btEmotionChecked.setVisibility(GONE);
        llExtend.setVisibility(VISIBLE);
        hideSoftInput();
    }

    /** 显示输入页面 */
    private void showInputView()
    {
        llPressToSpeak.setVisibility(GONE);
        llPressToInput.setVisibility(VISIBLE);
    }

    /** 显示录音View */
    private void showVoiceRecodingView()
    {
        llPressToSpeak.setVisibility(VISIBLE);
        llPressToInput.setVisibility(GONE);
        hideSoftInput();
        hideMore();
    }

    /** 隐藏more */
    private void hideMore()
    {
        llEmotion.setVisibility(GONE);
        btEmotion.setVisibility(VISIBLE);
        btEmotionChecked.setVisibility(GONE);
        llExtend.setVisibility(GONE);
        llMore.setVisibility(GONE);
    }

    public interface chatInputViewListener
    {
        /** 按住说话 */
        boolean onPressToSpeak(View view, MotionEvent event);

        /** 表情被点击 */
        void onBigEmotionClicked(Emotion emotion);

        /** 发送 */
        void onSendButtonClicked(String content);

        boolean onEditTextLongClick();

        void onEditTextUp();

        void onAlbumItemClicked();

        void onPhotoItemClicked();

        void onLocationItemClicked();

        void onVideoItemClicked();

        void onCallItemClicked();

        void onFileItemClicked();

        void onRedPackageItemClicked();

        void onTransferItemClicked();
    }
}

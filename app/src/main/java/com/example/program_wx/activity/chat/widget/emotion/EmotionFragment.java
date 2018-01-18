package com.example.program_wx.activity.chat.widget.emotion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.program_wx.R;
import com.example.program_wx.activity.chat.adapter.EmotionAdapter;
import com.example.program_wx.entity.Emotion;
import com.example.program_wx.utils.EmotionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * EmotionFragment
 * Created by YinTao on 2018/1/1.
 */

public class EmotionFragment extends Fragment
{
    private ViewPager vpEmotion;
    private TabLayout tabEmotion;
    private int emotionCols = 7, emotionRows = 3;//emotion的行列
    private List<Emotion> emotions;
    private OnEmotionClickListener emotionClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_emotion, container, false);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        vpEmotion = (ViewPager) view.findViewById(R.id.vp_emotion);
        tabEmotion = (TabLayout) view.findViewById(R.id.tab_emotion);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        final List<View> views = getViews();
        vpEmotion.setAdapter(new PagerAdapter()
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

            /**预加载*/
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
        tabEmotion.setupWithViewPager(vpEmotion);//设置联动
        TabLayout.Tab[] tab = new TabLayout.Tab[views.size()];
        for (int i = 0; i < views.size(); i++)
        {
            tab[i] = tabEmotion.getTabAt(i);
            ImageView view = new ImageView(getContext());
            view.setImageResource(R.drawable.dot_emoji);
            tab[i].setCustomView(view);
        }
    }

    public void initEmotionFragment(List<Emotion> emotions, int emotionCols, int emotionRows, OnEmotionClickListener listener)
    {
        this.emotions = emotions;
        this.emotionCols = emotionCols;
        this.emotionRows = emotionRows;
        this.emotionClickListener = listener;
    }

    /** 获取需要生成的View */
    private List<View> getViews()
    {
        int pageSize = emotionCols * emotionRows - 1;//留个给删除
        int totalSize = emotions.size();//总数
        Emotion.EmotionType emotionType = Emotion.EmotionType.NORMAL;
        if (totalSize != 0)
        {
            emotionType = emotions.get(0).getType();
        }
        if (emotionType == Emotion.EmotionType.BIG_EXPRESSION)//大图不用删除，直接发送
        {
            pageSize = emotionCols * emotionRows;
        }
        int pages = totalSize % pageSize == 0 ? (totalSize / pageSize) : (totalSize / pageSize + 1);
        List<View> views = new ArrayList<>();
        for (int i = 0; i < pages; ++i)
        {
            View view = View.inflate(getContext(), R.layout.emotion_gridview, null);
            GridView gridView = (GridView) view.findViewById(R.id.gv_emotions);
            gridView.setNumColumns(emotionCols);
            List<Emotion> list = new ArrayList<>();
            if (i != pages - 1)
            {
                //0  1  2  3  4  5  6
                //7  8  9  10 11 12 13
                //14 15 16 17 18 19 20+
                list.addAll(emotions.subList(i * pageSize, (i + 1) * pageSize));//0-20
            }
            else
            {
                list.addAll(emotions.subList(i * pageSize, totalSize));//末尾剩余
            }
            if (emotionType != Emotion.EmotionType.BIG_EXPRESSION) //不是大图
            {
                Emotion deleteIcon = new Emotion();
                deleteIcon.setEmotionText(EmotionUtil.DELETE_KEY);
                list.add(deleteIcon);
            }
            final EmotionAdapter adapter = new EmotionAdapter(getContext(), 1, list, emotionType);
            gridView.setAdapter(adapter);//设置适配
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()//设置监听
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Emotion item = adapter.getItem(position);
                    if (emotionClickListener != null)
                    {
                        String emotionText = item.getEmotionText();
                        if (emotionText.equals(EmotionUtil.DELETE_KEY))
                        {
                            emotionClickListener.onDeleteImageClicked();
                        }
                        else
                        {
                            emotionClickListener.onEmotionClicked(item);
                        }
                    }
                }
            });
            views.add(view);
        }
        return views;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 开发的接口
    ///////////////////////////////////////////////////////////////////////////
    public interface OnEmotionClickListener
    {
        void onDeleteImageClicked();

        void onEmotionClicked(Emotion emotion);
    }
}

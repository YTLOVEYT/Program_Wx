package com.example.program_wx.activity.chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.program_wx.R;
import com.example.program_wx.entity.Emotion;
import com.example.program_wx.utils.EmotionUtil;

import java.util.List;

/**
 * emotion的适配器
 * Created by YinTao on 2018/1/2.
 */

public class EmotionAdapter extends ArrayAdapter<Emotion>
{
    private Emotion.EmotionType emotionType;

    public EmotionAdapter(Context context, int textViewId, List<Emotion> emotions, Emotion.EmotionType type)
    {
        super(context, textViewId, emotions);
        this.emotionType = type;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            if (emotionType == Emotion.EmotionType.NORMAL)
            {
                convertView = View.inflate(getContext(), R.layout.row_expression, null);
            }
            else
            {
                convertView = View.inflate(getContext(), R.layout.row_big_expression, null);
            }
        }
        ImageView view = (ImageView) convertView.findViewById(R.id.iv_expression);
        Emotion item = getItem(position);
        if (item.getEmotionText().equals(EmotionUtil.DELETE_KEY))
        {
            view.setImageResource(R.drawable.delete_expression);
        }
        else
        {
            if (item.getIcon() != 0)
            {
                view.setImageResource(item.getIcon());
            }
            else if (item.getIconPath() != null)
            {
                Glide.with(getContext()).load(item.getIconPath()).placeholder(R.drawable.default_expression).into(view);
            }
        }
        return convertView;
    }
}

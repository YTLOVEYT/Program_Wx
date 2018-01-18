package com.example.program_wx.activity.chat.widget.extend;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.program_wx.R;
import com.example.program_wx.utils.CommonUtil;
import com.example.program_wx.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩展页面布局
 * Created by YinTao on 2018/1/4.
 */

public class ChatExtend extends GridView
{
    private Context context;
    private List<ExtendModule> extendsMenus = new ArrayList<>();
    private int cols = 4;
    private ExtendMenuItemClickListener menuItemClickListener;

    public ChatExtend(Context context)
    {
        super(context);
        init(context, null);
    }

    public ChatExtend(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public ChatExtend(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void initChatExtend(int name, int drawable, int itemId, ExtendMenuItemClickListener listener)
    {
        extendsMenus.add(new ExtendModule(itemId, drawable, context.getString(name), listener));
    }

    private void init(Context context, AttributeSet attrs)
    {
        this.context = context;
        TypedArray array = null;
        try
        {
            array = context.obtainStyledAttributes(attrs, R.styleable.ChatExtend);
            cols = array.getInteger(R.styleable.ChatExtend_numCols, 4);
        }
        catch (Exception e)
        {
            LogUtil.e("读取ChatExtend参数失败");
            e.printStackTrace();
        }
        finally
        {
            if (array != null)
            {
                array.recycle();
            }
        }
        setNumColumns(cols);//设置gridView的显示栏数
        setStretchMode(STRETCH_COLUMN_WIDTH);
        setGravity(Gravity.CENTER_VERTICAL);
        setVerticalSpacing(CommonUtil.dp2px(context, 8));
    }

    public void init()
    {
        setAdapter(new ExtendMenuAdapter(context, extendsMenus));
    }

    private class ExtendMenuAdapter extends ArrayAdapter<ExtendModule>
    {
        private Context context;

        public ExtendMenuAdapter(@NonNull Context context, @NonNull List<ExtendModule> objects)
        {
            super(context, 1, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            ExtendMenu extendMenu = null;
            if (convertView == null)
            {
                convertView = new ExtendMenu(context);
            }
            extendMenu = (ExtendMenu) convertView;
            final ExtendModule item = getItem(position);
            if (item != null)
            {
                extendMenu.setImageView(item.image);
                extendMenu.setTextView(item.name);
                extendMenu.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (item.itemClickListener != null)
                        {
                            item.itemClickListener.onItemClick(item.id, v);
                        }
                    }
                });
            }
            return convertView;
        }
    }

    /** 扩展Menu的模板 */
    class ExtendModule
    {
        int id;
        int image;
        String name;
        ExtendMenuItemClickListener itemClickListener;

        public ExtendModule(int id, int image, String name, ExtendMenuItemClickListener itemClickListener)
        {
            this.id = id;
            this.image = image;
            this.name = name;
            this.itemClickListener = itemClickListener;
        }
    }

    class ExtendMenu extends LinearLayout
    {
        private ImageView imageView;
        private TextView textView;

        public ExtendMenu(Context context)
        {
            super(context);
            initMenu(context);
        }

        private void initMenu(Context context)
        {
            LayoutInflater.from(context).inflate(R.layout.chat_menu_item, this);
            imageView = (ImageView) this.findViewById(R.id.image);
            textView = (TextView) this.findViewById(R.id.text);
        }

        public void setImageView(int id)
        {
            imageView.setImageResource(id);
        }

        public void setTextView(String text)
        {
            textView.setText(text);
        }
    }

    /** Menu的点击接口 */
    public interface ExtendMenuItemClickListener
    {
        void onItemClick(int id, View view);
    }
}

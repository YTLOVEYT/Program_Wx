package com.example.program_wx.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.program_wx.R;
import com.example.program_wx.activity.main.contacts.ContactsAdapter;

/**
 * 侧边栏
 * Created by YinTao on 2018/1/8.
 */

public class SideBarView extends View
{
    private Context context;
    private ListView listView;
    private String[] sections = new String[]{"↑", "☆", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private Paint paint;
    private TextView header;

    public SideBarView(Context context)
    {
        super(context);
        init(context);
    }

    public SideBarView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public SideBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        this.context = context;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.DKGRAY);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sider_bar_textsize));
    }

    /** 设置关联的listView */
    public void setListView(ListView listView)
    {
        this.listView = listView;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int center = getWidth() / 2;
        int height = getHeight() / sections.length;
        for (int i = 0; i < sections.length - 1; ++i)
        {
            canvas.drawText(sections[i], center, height * (i + 1), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (header == null)
                {
                    header = (TextView) ((View) getParent()).findViewById(R.id.float_text);
                }
                setHeaderAndScroll(event);
                header.setVisibility(VISIBLE);
                setBackgroundResource(R.drawable.sidebar_background_pressed);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return super.onTouchEvent(event);
    }

    /** 显示header并且滑动listView */
    private void setHeaderAndScroll(MotionEvent event)
    {
        if (listView == null)
        {
            return;
        }
        String text = sections[selectTextFromPoint(event.getY())];
        header.setText(text);
        HeaderViewListAdapter adapter = (HeaderViewListAdapter) listView.getAdapter();
        ContactsAdapter contactsAdapter = (ContactsAdapter) adapter.getWrappedAdapter();

    }

    /** 根据坐标确定选择的文本 */
    private int selectTextFromPoint(float y)
    {

        return 0;
    }
}

package com.example.program_wx.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * surfaceView
 * Created by YinTao on 2017/12/10.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable
{
    private static final String TAG = "MySurfaceView";
    private Canvas canvas;
    private SurfaceHolder holder;
    private boolean isRunning = false;
    private Paint paint;
    private Path path;

    public MySurfaceView(Context context)
    {
        super(context);
        init(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        path = new Path();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        isRunning = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        isRunning = false;
    }

    @Override
    public void run()
    {
        int x = 0, y = 0;
        while (isRunning)
        {
            try
            {
                canvas = holder.lockCanvas();//获取画布
                canvas.drawColor(Color.WHITE);
                canvas.drawPath(path, paint);
                x += 1;
                y = (int) (100 * Math.sin(x * 2 * Math.PI / 180) + 400);
                path.lineTo(x, y);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (canvas != null)
                {
                    holder.unlockCanvasAndPost(canvas);//提交画布
                }
            }
        }
    }
}

package com.example.program_wx.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.program_wx.MyApp;
import com.example.program_wx.MyConst;
import com.example.program_wx.R;

import java.util.regex.Pattern;

/**
 * 判定使用工具类
 * Created by YinTao on 2017/12/7.
 */

public class CommonUtil
{
    /** 验证是否是手机号码 */
    public static boolean isMobile(String tel)
    {
        return isMatch(MyConst.REGEX_MOBILE_EXACT, tel);
    }

    private static boolean isMatch(String regex, String string)
    {
        return !TextUtils.isEmpty(string) && Pattern.matches(regex, string);
    }

    /** 创建全局dialog */
    public static Dialog createLoadingDialog(Context context, String msg)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_loadingdialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialogView);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_amimation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(true);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;
    }

    /** 检查网络连接 */
    public static boolean isNetWorkConnected()
    {
        ConnectivityManager manager = (ConnectivityManager) MyApp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    /** 判断字符是否是数字 */
    public static boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /** 判断字符串首字母是否是字母 */
    public static boolean check(String firstData)
    {
        char c = firstData.charAt(0);
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }

    /** dp转px */
    public static int dp2px(Context context, float dp)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }

    /** 返回屏幕宽度px */
    public static int getScreenWidth(Context context)
    {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /** 返回屏幕宽度px */
    public static int getScreenHeight(Context context)
    {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /** 获取软键盘的高度 */
    public static int getSoftInputHeight(Activity activity)
    {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);//400
        int screenHeight = activity.getWindow().getDecorView().getRootView().getHeight();//700
        int softInputHeight = screenHeight - rect.bottom;
        if (Build.VERSION.SDK_INT >= 20)
        {
            softInputHeight = softInputHeight - getSoftButtonBarsHeight(activity);
        }
        if (softInputHeight < 0)
        {
            LogUtil.e("获取软件盘高度=" + softInputHeight);
        }
        return softInputHeight;
    }

    /** 获取部分手机软键盘按钮的高度 */
    private static int getSoftButtonBarsHeight(Activity activity)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;//获取屏幕高度
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1)//17 4.2
        {
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        }
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight)
        {
            return realHeight - usableHeight;
        }
        return 0;
    }

    /** 监听键盘 */
    public static void observeSoftKeyBoard(final Activity activity, final OnSoftKeyboardChangeListener keyboardChangeListener)
    {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            int previousKeyboardHeight = -1;

            @Override
            public void onGlobalLayout()
            {
                LogUtil.e("监听getDecorView的绘制");
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHeight = rect.bottom - rect.top;//显示高度
                int screenHeight = decorView.getHeight();
                int keyBoardHeight = screenHeight - displayHeight;
                if (Build.VERSION.SDK_INT >= 20)
                {
                    keyBoardHeight = keyBoardHeight - getSoftButtonBarsHeight(activity);
                }
                if (previousKeyboardHeight != keyBoardHeight)
                {
                    boolean hide = (double) displayHeight / screenHeight > 0.8;
                    keyboardChangeListener.onSoftKeyBoardChange(keyBoardHeight, !hide, this);
                }
                previousKeyboardHeight = keyBoardHeight;
            }
        });
    }

    public interface OnSoftKeyboardChangeListener
    {
        void onSoftKeyBoardChange(int softKeybardHeight, boolean visible, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener);
    }
}

package com.example.program_wx.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.example.program_wx.R;
import com.example.program_wx.entity.Emotion;
import com.example.program_wx.entity.EmotionData;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表情工具栏
 * Created by YinTao on 2018/1/2.
 */

public class EmotionUtil
{
    public static final String DELETE_KEY = "em_delete_delete_expression";//删除

    public static final String emoji_001 = "[emoji_1]";
    public static final String emoji_002 = "[emoji_2]";
    public static final String emoji_003 = "[emoji_3]";
    public static final String emoji_004 = "[emoji_4]";
    public static final String emoji_005 = "[emoji_5]";
    public static final String emoji_006 = "[emoji_6]";
    public static final String emoji_007 = "[emoji_7]";
    public static final String emoji_008 = "[emoji_8]";
    public static final String emoji_009 = "[emoji_9]";
    public static final String emoji_010 = "[emoji_10]";
    public static final String emoji_11 = "[emoji_11]";
    public static final String emoji_12 = "[emoji_12]";
    public static final String emoji_13 = "[emoji_13]";
    public static final String emoji_14 = "[emoji_14]";
    public static final String emoji_15 = "[emoji_15]";
    public static final String emoji_16 = "[emoji_16]";
    public static final String emoji_17 = "[emoji_17]";
    public static final String emoji_18 = "[emoji_18]";
    public static final String emoji_19 = "[emoji_19]";
    public static final String emoji_20 = "[emoji_20]";
    public static final String emoji_21 = "[emoji_21]";
    public static final String emoji_22 = "[emoji_22]";
    public static final String emoji_23 = "[emoji_23]";
    public static final String emoji_24 = "[emoji_24]";
    public static final String emoji_25 = "[emoji_25]";
    public static final String emoji_26 = "[emoji_26]";
    public static final String emoji_27 = "[emoji_27]";
    public static final String emoji_28 = "[emoji_28]";
    public static final String emoji_29 = "[emoji_29]";
    public static final String emoji_30 = "[emoji_30]";
    public static final String emoji_31 = "[emoji_31]";
    public static final String emoji_32 = "[emoji_32]";
    public static final String emoji_33 = "[emoji_33]";
    public static final String emoji_34 = "[emoji_34]";
    public static final String emoji_35 = "[emoji_35]";
    public static final String emoji_36 = "[emoji_36]";
    public static final String emoji_37 = "[emoji_37]";
    public static final String emoji_38 = "[emoji_38]";
    public static final String emoji_39 = "[emoji_39]";
    public static final String emoji_40 = "[emoji_40]";
    public static final String emoji_41 = "[emoji_41]";
    public static final String emoji_42 = "[emoji_42]";
    public static final String emoji_43 = "[emoji_43]";

    public static final String emoji_45 = "[emoji_45]";
    public static final String emoji_46 = "[emoji_46]";
    public static final String emoji_47 = "[emoji_47]";
    public static final String emoji_48 = "[emoji_48]";
    public static final String emoji_49 = "[emoji_49]";
    public static final String emoji_50 = "[emoji_50]";
    public static final String emoji_51 = "[emoji_51]";
    public static final String emoji_52 = "[emoji_52]";
    public static final String emoji_53 = "[emoji_53]";
    public static final String emoji_54 = "[emoji_54]";
    public static final String emoji_55 = "[emoji_55]";
    public static final String emoji_56 = "[emoji_56]";
    public static final String emoji_57 = "[emoji_57]";

    public static final String emoji_58 = "[emoji_58]";
    public static final String emoji_59 = "[emoji_59]";
    public static final String emoji_60 = "[emoji_60]";
    public static final String emoji_61 = "[emoji_61]";
    public static final String emoji_62 = "[emoji_62]";
    public static final String emoji_63 = "[emoji_63]";
    public static final String emoji_64 = "[emoji_64]";
    public static final String emoji_65 = "[emoji_65]";
    public static final String emoji_66 = "[emoji_66]";
    public static final String emoji_67 = "[emoji_67]";
    public static final String emoji_68 = "[emoji_68]";
    public static final String emoji_69 = "[emoji_69]";
    public static final String emoji_70 = "[emoji_70]";
    public static final String emoji_71 = "[emoji_71]";

    public static final String emoji_72 = "[emoji_72]";
    public static final String emoji_73 = "[emoji_73]";
    public static final String emoji_74 = "[emoji_74]";
    public static final String emoji_75 = "[emoji_75]";
    public static final String emoji_76 = "[emoji_76]";
    public static final String emoji_77 = "[emoji_77]";
    public static final String emoji_78 = "[emoji_78]";
    public static final String emoji_79 = "[emoji_79]";
    public static final String emoji_80 = "[emoji_80]";
    public static final String emoji_81 = "[emoji_81]";
    public static final String emoji_82 = "[emoji_82]";
    public static final String emoji_83 = "[emoji_83]";
    public static final String emoji_84 = "[emoji_84]";
    public static final String emoji_85 = "[emoji_85]";

    public static final String emoji_86 = "[emoji_86]";
    public static final String emoji_87 = "[emoji_87]";
    public static final String emoji_88 = "[emoji_88]";
    public static final String emoji_89 = "[emoji_89]";
    public static final String emoji_90 = "[emoji_90]";
    public static final String emoji_91 = "[emoji_91]";
    public static final String emoji_92 = "[emoji_92]";
    public static final String emoji_93 = "[emoji_93]";
    public static final String emoji_94 = "[emoji_94]";
    public static final String emoji_95 = "[emoji_95]";
    public static final String emoji_96 = "[emoji_96]";
    public static final String emoji_97 = "[emoji_97]";
    public static final String emoji_98 = "[emoji_98]";
    public static final String emoji_99 = "[emoji_99]";

    public static final String emoji_100 = "[emoji_100]";
    public static final String emoji_101 = "[emoji_101]";
    public static final String emoji_102 = "[emoji_102]";
    public static final String emoji_103 = "[emoji_103]";
    public static final String emoji_104 = "[emoji_104]";
    public static final String emoji_105 = "[emoji_105]";
    public static final String emoji_106 = "[emoji_106]";
    public static final String emoji_107 = "[emoji_107]";
    public static final String emoji_108 = "[emoji_108]";
    public static final String emoji_109 = "[emoji_109]";
    public static final String emoji_110 = "[emoji_110]";
    public static final String emoji_111 = "[emoji_111]";
    public static final String emoji_112 = "[emoji_112]";
    public static final String emoji_113 = "[emoji_113]";

    public static final String emoji_114 = "[emoji_114]";
    public static final String emoji_115 = "[emoji_115]";
    public static final String emoji_116 = "[emoji_116]";
    public static final String emoji_117 = "[emoji_117]";
    public static final String emoji_118 = "[emoji_118]";
    public static final String emoji_119 = "[emoji_119]";
    public static final String emoji_120 = "[emoji_120]";
    public static final String emoji_121 = "[emoji_121]";
    public static final String emoji_122 = "[emoji_122]";
    public static final String emoji_123 = "[emoji_123]";
    public static final String emoji_124 = "[emoji_124]";
    public static final String emoji_125 = "[emoji_125]";
    public static final String emoji_126 = "[emoji_126]";
    public static final String emoji_127 = "[emoji_127]";
    public static final String emoji_128 = "[emoji_128]";
    public static final String emoji_129 = "[emoji_129]";
    public static final String emoji_130 = "[emoji_130]";
    public static final String emoji_131 = "[emoji_131]";
    public static final String emoji_132 = "[emoji_132]";
    public static final String emoji_133 = "[emoji_133]";
    public static final String emoji_134 = "[emoji_134]";
    public static final String emoji_135 = "[emoji_135]";
    public static final String emoji_136 = "[emoji_136]";
    public static final String emoji_137 = "[emoji_137]";
    public static final String emoji_138 = "[emoji_138]";
    public static final String emoji_139 = "[emoji_139]";


    private static final Spannable.Factory span_factory = Spannable.Factory.getInstance();//获取spannable的实例对象
    private static final HashMap<Pattern, Object> emotions = new HashMap<>();

    static
    {
        Emotion[] emotions = EmotionData.getDATA();
        for (Emotion emotion : emotions)
        {
            addPattern(emotion.getEmotionText(), emotion.getIcon());
        }
    }

    /** 给每个emotion添加正则规范 */
    private static void addPattern(String emotionText, Object icon)
    {
        emotions.put(Pattern.compile(Pattern.quote(emotionText)), icon);
    }

    /**
     * 替换文本为表情
     * @param context   上下文
     * @param spannable spannable
     * @return true/false
     */
    private static boolean addSmiles(Context context, Spannable spannable)
    {
        boolean hasChanged = false;
        for (Map.Entry<Pattern, Object> entry : emotions.entrySet())
        {
            Matcher matcher = entry.getKey().matcher(spannable);
            while (matcher.find()) //如果匹配
            {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(), matcher.end(), ImageSpan.class))
                {
                    if (spannable.getSpanStart(span) >= matcher.start() && spannable.getSpanEnd(span) <= matcher.end())
                    {
                        spannable.removeSpan(span);
                    }
                    else
                    {
                        set = false;
                        break;
                    }
                }
                if (set)
                {
                    hasChanged = true;
                    Object value = entry.getValue();
                    if (value instanceof String && !((String) value).startsWith("http"))//string且非http
                    {
                        LogUtil.e("图片value=" + value);
                        File file = new File((String) value);
                        if (!file.exists() || file.isDirectory())
                        {
                            return false;
                        }
                        ImageSpan imageSpan = new ImageSpan(context, Uri.fromFile(file));
                        Drawable drawable = imageSpan.getDrawable();
                        drawable.setBounds(0, 0, 25, 25);
                        imageSpan = new ImageSpan(drawable);
                        spannable.setSpan(imageSpan, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//不包含前后
                    }
                    else
                    {
                        ImageSpan imageSpan = new ImageSpan(context, (Integer) value);
                        Drawable drawable = imageSpan.getDrawable();
                        int size = R.dimen.span_size;
                        drawable.setBounds(0, 0, size, size);
                        imageSpan = new ImageSpan(drawable);
                        spannable.setSpan(imageSpan, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        }
        return hasChanged;
    }

    /** 获取spannable文本 */
    public static Spannable getSmileText(Context context, CharSequence text)
    {
        Spannable spannable = span_factory.newSpannable(text);
        addSmiles(context, spannable);
        return spannable;
    }

    /** 是否包含该字符串 */
    public static boolean containsKey(String key)
    {
        boolean b = false;
        for (Map.Entry<Pattern, Object> entry : emotions.entrySet())
        {
            Matcher matcher = entry.getKey().matcher(key);
            if (matcher.find())
            {
                b = true;
                break;
            }
        }
        return b;
    }

}

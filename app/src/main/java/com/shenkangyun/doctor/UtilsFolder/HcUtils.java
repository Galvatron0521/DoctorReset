package com.shenkangyun.doctor.UtilsFolder;


import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.Toast;

import com.shenkangyun.doctor.BaseFolder.MyApp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/11/25.
 */
public class HcUtils {

    public static void setEtFilter(EditText et) {
        if (et == null) {
            return;
        }
        //表情过滤器
        InputFilter emojiFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                Pattern emoji = Pattern.compile(
                        "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    Toast.makeText(MyApp.getContext(), "你不能输入表情", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        };
        et.setFilters(new InputFilter[]{emojiFilter});
    }

    public static void setEtFilter(final Context context, EditText et) {
        if (et == null) {
            return;
        }
        //表情过滤器
        InputFilter emojiFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                Pattern emoji = Pattern.compile(
                        "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    Toast.makeText(context, "你不能输入表情", Toast.LENGTH_SHORT).show();
                    return "";
                }
                return null;
            }
        };
        //特殊字符过滤器
        InputFilter specialCharFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String regexStr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(regexStr);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.matches()) {
                    Toast.makeText(context, "你不能输入特殊字符", Toast.LENGTH_SHORT).show();
                    return "";
                } else {
                    return null;
                }

            }
        };

        et.setFilters(new InputFilter[]{emojiFilter, specialCharFilter});
    }
}

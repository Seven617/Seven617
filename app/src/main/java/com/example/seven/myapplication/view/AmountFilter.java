package com.example.seven.myapplication.view;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Seven617 on 2017/10/27 .
 */

public class AmountFilter implements InputFilter {

    private final String TAG = "AmountFilter";

    Pattern mPattern;//正则匹配

    Pattern pointPattern;

    private int pointSize = 0;


    private static final int POINTER_AFTER_LENGTH = 2; //小数点后的位数

    private static final String POINTER = ".";

    private static final String ZERO = "0";
    /**
     * 最大数字
     */
    public static final int MAX_VALUE = 99999;

    public AmountFilter() {
        //正则表达式
        mPattern = Pattern.compile("([0-9]|\\.)*");
        pointPattern = Pattern.compile("\\.");
    }


    /**
     * @param source 新输入的字符串
     * @param start  新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为0
     * @param dend   原内容终点坐标，一般为dest长度-1
     * @return 输入内容
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {

        String sourceText = source.toString();
        String destText = dest.toString();

        //如果小数点超过1个则无视
        if(pointPattern.matcher(sourceText).matches()){

            if(dest.toString().contains(".")){
                sourceText="";
                source="";
            }



        }

        if(!mPattern.matcher(sourceText).matches()){
            sourceText="";
            source="";
        }

        //验证删除等按键
        if (TextUtils.isEmpty(sourceText)) {
            return "";
        }
        //如果第一位是.自动添加0
        if(source.toString().equals(".") && (destText.equals("")|| destText ==null)){
            sourceText="0.";
            source = "0.";
        }


        //验证输入金额的大小
        if (!source.toString().equals("")) {
            double dold = Double.parseDouble(destText + source.toString());
            if (dold > MAX_VALUE) {
                return dest.subSequence(dstart, dend);
            } else if (dold == MAX_VALUE) {
                if (source.toString().equals(".")) {
                    return dest.subSequence(dstart, dend);
                }
            }
        }

        //如果是0开头，则下一位必须是点
        if (destText.startsWith(ZERO)) {
            if (!TextUtils.equals(POINTER, source) && destText.length() == 1) {
                sourceText = "";
            }
            //0和点之间不能输入任何数字
            else if (destText.contains(POINTER)) {
                int point = destText.indexOf(POINTER);
                if (dstart <= point && dstart > 0) {
                    sourceText = "";
                }
            } else if (!destText.contains(POINTER)
                    && dstart != 0 && destText.length() != 1) {
                sourceText = "";
            }

        }

        //不能将0放在字符串的第一位
        if (!TextUtils.isEmpty(destText) && dstart == 0
                && TextUtils.equals(ZERO, sourceText)
                && !destText.startsWith(POINTER)) {
            sourceText = "";
        }

        //第一位不能输入“.”
        if (TextUtils.isEmpty(destText) && TextUtils.equals(sourceText, POINTER)) {
            sourceText = "";
        }

        //如果第一位为“.”,则点号后面不能继续输入
        if (destText.startsWith(POINTER) && dstart != 0) {
            sourceText = "";
        }

        //保留POINTER_AFTER_LENGTH位小数
        if (!destText.contains(POINTER) && TextUtils.equals(POINTER, sourceText)) {
            int decimal = destText.length() - dstart;
            if (decimal > 2) {
                sourceText = "";
            }
        } else if (destText.contains(POINTER)) {
            int point = destText.indexOf(POINTER);
            if (dstart > point && (destText.length() - point) >= 3) {
                sourceText = "";
            }
        }

        String result = dest.subSequence(dstart, dend) + sourceText;
        return result;

    }

}

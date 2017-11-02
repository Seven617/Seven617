package com.example.seven.myapplication.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import java.text.DecimalFormat;

/**
 * Created by Seven617 on 2017/10/27 .
 */

@SuppressLint("AppCompatCustomView")
public class AmountEditText extends EditText {

    private Context mContext;

    private int Multiple = 1;//倍数
    public static final int MAX_VALUE = 888888;
    public AmountEditText(Context context) {
        super(context);
        init(context);
    }

    public AmountEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AmountEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
    }

    @Override
    public void setFilters(InputFilter[] filters) {
       filters = new InputFilter[]{new AmountFilter()};
        super.setFilters(filters);
    }

    /**
     * 输入内容是否符合规则
     * @return
     */
    public boolean isConformRules(){
        String result = super.getText().toString();
        if (TextUtils.isEmpty(result)){
            return false;
        }else if (result.contains(".")){
            if (result.startsWith(".")){
                return false;
            }else if (result.startsWith("0")){
                int indexZero = result.indexOf("0");
                int indexPoint = result.indexOf(".");
                if (indexPoint - indexZero != 1){
                    return false;
                }else if (TextUtils.equals("0.",result)){
                    return false;
                }
            }
        }else if (!result.contains(".")){
            if (result.startsWith("0")){
                return false;
            }
        }
        return true;
    }

    /**
     * 获取设置倍数
     * @return
     */
    public int getMultiple() {
        return Multiple;
    }

    /**
     * 设置金额倍数
     * @param multiple
     */
    public void setMultiple(int multiple) {
        Multiple = multiple;
    }

    /**
     * 输出内容 * 倍数
     * @return
     */
    public String getContent(){
        String result = super.getText().toString();
        if (TextUtils.isEmpty(result)){
            return "";
        }
        double resultDouble = Double.parseDouble(result) * Multiple;
        DecimalFormat df   =   new   DecimalFormat("######0.00");
        String resultDo = df.format(resultDouble);//返回的是String类型的数据
        return resultDo;
    }

}

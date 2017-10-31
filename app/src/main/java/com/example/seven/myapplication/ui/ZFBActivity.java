package com.example.seven.myapplication.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.view.AmountEditText;
import com.example.seven.myapplication.view.TitleBar;

public class ZFBActivity extends BaseActivity {
    private TitleBar titleBar;
    private String title;
    private AmountEditText amountEditText;
    private Button btn_sure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zfb);
        getview();
        titleBar();
    }
    private void getview() {
        titleBar = (TitleBar) findViewById(R.id.zfbpay_bar);
        btn_sure= (Button) findViewById(R.id.btn_sure);
        amountEditText = (AmountEditText) findViewById(R.id.edit_amount);
//        amountEditText.setMultiple(100);
        btn_sure.setOnClickListener(OK);
    }
    View.OnClickListener OK=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (amountEditText.isConformRules()){
                ShowToast(amountEditText.getContent());
            }else {
                ShowToast("输入内容不符合规则！！！");
            }
        }
    };
    private void titleBar() {
        //左边返回按钮
//        titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftTextSize(15);
        title = "支付宝二维码/条码";
        titleBar.setTitle(title);
        titleBar.setTitleSize(20);
        titleBar.setTitleColor(Color.WHITE);
        //下滑分割线
        titleBar.setDividerColor(Color.GRAY);
        //设置titleBar背景颜色
        titleBar.setBackgroundResource(R.color.colorPrimaryDark);
        //左边返回按钮点击事件
        titleBar.setLeftClickListener(ck);
    }
    View.OnClickListener ck=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ZFBActivity.this.finish();
        }
    };
}

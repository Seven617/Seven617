package com.example.seven.myapplication.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.view.TitleBar;

public class ZFBActivity extends AppCompatActivity {
    private TitleBar titleBar;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zfb);
        getview();
        titleBar();
    }
    private void getview() {
        titleBar = (TitleBar) findViewById(R.id.zfbpay_bar);
    }
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

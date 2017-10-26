package com.example.seven.myapplication.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.view.TitleBar;

public class Main4Activity extends AppCompatActivity {
    private TitleBar titleBar;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
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
        title = "二维码/条码";
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
            Main4Activity.this.finish();
        }
    };
}

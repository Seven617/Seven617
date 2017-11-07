package com.example.seven.myapplication.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.view.TitleBar;

import java.util.Timer;
import java.util.TimerTask;

//主界面
public class MainActivity extends BaseActivity {
    private TitleBar titleBar;
    private Button zfbpay;
    private Button wechatpay;
    private Button refunds;
    private Button query;
    private Button print;
    private String title;
    private Intent intent;
    private LinearLayout show_main;
    private LinearLayout gone_main;
    private boolean quit = false; //设置退出的标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getview();
        titleBar();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        show_main.setVisibility(View.VISIBLE);
        gone_main.setVisibility(View.GONE);
    }

    @Override
    protected void onNetworkDisConnected() {
        show_main.setVisibility(View.GONE);
        gone_main.setVisibility(View.VISIBLE);
    }

    private void getview() {
        show_main = (LinearLayout) findViewById(R.id.show_main);
        gone_main = (LinearLayout) findViewById(R.id.gone_main);
        titleBar = (TitleBar) findViewById(R.id.main_bar);
        zfbpay = (Button) findViewById(R.id.zfbpay);
        wechatpay = (Button) findViewById(R.id.wechatpay);
        refunds = (Button) findViewById(R.id.refunds);
        query = (Button) findViewById(R.id.query);
        print = (Button) findViewById(R.id.print);
        zfbpay.setOnClickListener(next);
        wechatpay.setOnClickListener(next);
        refunds.setOnClickListener(next);
        query.setOnClickListener(next);
        print.setOnClickListener(next);
    }

    View.OnClickListener next = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.zfbpay) {
                intent = new Intent(MainActivity.this, AmountActivity.class);
            }
            if (v.getId() == R.id.wechatpay) {
                intent = new Intent(MainActivity.this, WeChatActivity.class);
            }
            if (v.getId() == R.id.refunds) {
                intent = new Intent(MainActivity.this, RefundableActivity.class);
            }
            if (v.getId() == R.id.query) {
                intent = new Intent(MainActivity.this, ChoseQueryActivity.class);
            }
            if (v.getId() == R.id.print) {
                intent = new Intent(MainActivity.this, PrintActivity.class);
            }
            startActivity(intent);
        }
    };

    private void titleBar() {
        title = "支付主界面";
        titleBar.setTitle(title);
        titleBar.setTitleSize(20);
        titleBar.setTitleColor(Color.WHITE);
        //下滑分割线
        titleBar.setDividerColor(Color.GRAY);
        //设置titleBar背景颜色
        titleBar.setBackgroundResource(R.color.colorPrimaryDark);
    }

    //    //双击退出
    @Override
    public void onBackPressed() {

        if (quit == false) {     //询问退出程序
            showToast("再按一次退出程序");
            new Timer(true).schedule(new TimerTask() {      //启动定时任务
                @Override
                public void run() {
                    quit = false;   //重置退出标识
                }
            }, 2000);  //延时２秒执行
            quit = true;
        } else {     //确认退出程序
            super.onBackPressed();
            finish();
        }
    }
}

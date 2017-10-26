package com.example.seven.myapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.view.TitleBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BsaeActivity {
    private TitleBar titleBar;
    private Button zfbpay;
    private String title;
    private boolean quit = false; //设置退出的标识
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getview();
        titleBar();
    }
    private void getview() {
        titleBar = (TitleBar) findViewById(R.id.main_bar);
        zfbpay= (Button) this.findViewById(R.id.zfbpay);
        zfbpay.setOnClickListener(next);
    }
    View.OnClickListener next=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.zfbpay){
                Intent intent = new Intent(MainActivity.this, ZFBActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
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
            ShowToast("再按一次退出程序");
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

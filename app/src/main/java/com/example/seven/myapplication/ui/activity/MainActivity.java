package com.example.seven.myapplication.ui.activity;

import android.Manifest;
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

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

//主界面
public class MainActivity extends BaseActivity {
    private TitleBar titleBar;
    private Button zfbpay;
    private Button wechatpay;
    private Button refunds;
    private Button query;
    private Button print;
    private Button userinfo;
    private Button about;
    private String title;
    private Intent intent;
    private Button todayOrder;
    private LinearLayout show_main;
    private LinearLayout gone_main;
    private boolean quit = false; //设置退出的标识
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();
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

    private void getView() {
        show_main = (LinearLayout) findViewById(R.id.show_main);
        gone_main = (LinearLayout) findViewById(R.id.gone_main);
        titleBar = (TitleBar) findViewById(R.id.main_bar);
        zfbpay = (Button) findViewById(R.id.zfbpay);
        refunds = (Button) findViewById(R.id.refunds);
        query = (Button) findViewById(R.id.query);
        about = (Button) findViewById(R.id.main_about);
        todayOrder = (Button) findViewById(R.id.today_order);
        userinfo = (Button) findViewById(R.id.user_info);
        zfbpay.setOnClickListener(next);
        about.setOnClickListener(next);
        refunds.setOnClickListener(next);
        query.setOnClickListener(next);
        todayOrder.setOnClickListener(next);
        userinfo.setOnClickListener(next);
//        print.setOnClickListener(next);
    }

    View.OnClickListener next = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.zfbpay) {
                intent = new Intent(MainActivity.this, AmountActivity.class);
            }
            if (v.getId() == R.id.refunds) {
                intent = new Intent(MainActivity.this, RefundActivity.class);
            }
            if (v.getId() == R.id.query) {
                intent = new Intent(MainActivity.this, ChoseQueryActivity.class);
            }
            if (v.getId() == R.id.main_about) {
                intent = new Intent(MainActivity.this, AboutActivity.class);
            }
            if (v.getId() == R.id.today_order) {
                intent = new Intent(MainActivity.this, TodayOrderActivity.class);
            }
            if (v.getId() == R.id.user_info) {
                intent = new Intent(MainActivity.this, UserInfoActivity.class);
            }
            startActivity(intent);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

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

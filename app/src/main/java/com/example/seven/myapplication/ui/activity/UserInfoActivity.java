package com.example.seven.myapplication.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.seven.myapplication.App;
import com.example.seven.myapplication.R;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.view.TitleBar;


public class UserInfoActivity extends BaseActivity {
    private App app;
    private Button userexit;
    private String tuserName;
    private String tuserTypeName;
    private String tuserlastLoginTime;
    private String tusershopSn;
    private String tusername;
    private TextView userName;
    private TextView userTypeName;
    private TextView userlastLoginTime;
    private TextView usershopSn;
    private TextView username;//操作员
    private TitleBar titleBar;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        getview();
        titleBar();
        getInfo();
    }

    //获取数据
    private void getInfo() {
        app = (App) getApplication();
        tuserName = app.getUserName();
        tuserTypeName = app.getUserTypeName();
        tuserlastLoginTime = app.getUserlastLoginTime();
        tusershopSn = app.getUsershopSn();
        tusername = app.getUsername();
        putinfo();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    private void getview() {
        titleBar = (TitleBar) findViewById(R.id.userinfo_pay_bar);
        userexit = (Button) findViewById(R.id.user_exit);
        userName = (TextView) findViewById(R.id.user_Name);
        userTypeName = (TextView) findViewById(R.id.user_TypeName);
        userlastLoginTime = (TextView) findViewById(R.id.user_lastLoginTime);
        usershopSn = (TextView) findViewById(R.id.user_shopSn);
        username = (TextView) findViewById(R.id.user_name);
        userexit.setOnClickListener(exit);
    }

    View.OnClickListener exit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            app = (App) getApplication();
            app.setUserName("");
            app.setUserTypeName("");
            app.setUserlastLoginTime("");
            app.setUsershopSn("");
            app.setUsername("");
            exit();
        }
    };

    private void putinfo() {
        userName.setText(tuserName);
        userTypeName.setText(tuserTypeName);
        userlastLoginTime.setText(tuserlastLoginTime);
        usershopSn.setText(tusershopSn);
        username.setText(tusername);
    }
    private void exit(){
        userName.setText("");
        userTypeName.setText("");
        userlastLoginTime.setText("");
        usershopSn.setText("");
        username.setText("");
    }

    //标题
    private void titleBar() {
        //左边返回按钮
        //titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftTextSize(15);
        title = "当前登录信息";
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

    //左边返回按钮点击事件
    View.OnClickListener ck = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            UserInfoActivity.this.finish();
        }
    };

}

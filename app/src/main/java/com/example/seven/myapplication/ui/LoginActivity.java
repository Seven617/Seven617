package com.example.seven.myapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.view.TitleBar;

public class LoginActivity extends BsaeActivity {
    private EditText edt1;
    private EditText edt2;
    private Button btn;
    private String name;
    private String psw;
    private String title;
    private TitleBar titleBar;
    private LinearLayout show_login;
    private LinearLayout gone_login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getview();
        titleBar();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
//        ShowToast("网络连接正常\n" + type.name());
        show_login.setVisibility(View.VISIBLE);
        gone_login.setVisibility(View.GONE);
    }

    @Override
    protected void onNetworkDisConnected() {
//        ShowToast("请检测网络连接");
//        this.finish();
        show_login.setVisibility(View.GONE);
        gone_login.setVisibility(View.VISIBLE);
    }


    private void getview() {
        show_login= (LinearLayout) findViewById(R.id.show_login);
        gone_login= (LinearLayout) findViewById(R.id.gone_login);
        edt1= (EditText) this.findViewById(R.id.name);
        edt2= (EditText) this.findViewById(R.id.psd);
        titleBar = (TitleBar) findViewById(R.id.login_bar);
        btn= (Button) this.findViewById(R.id.btn);
        btn.setOnClickListener(tonext);

        //测试使用  记得删除
        edt1.setText("617");
        edt2.setText("123");
    }
    private void titleBar() {
        title = "用户登录";
        titleBar.setTitle(title);
        titleBar.setTitleSize(20);
        titleBar.setTitleColor(Color.WHITE);
        //下滑分割线
        titleBar.setDividerColor(Color.GRAY);
        //设置titleBar背景颜色
        titleBar.setBackgroundResource(R.color.colorPrimaryDark);
    }
    View.OnClickListener tonext=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            name= edt1.getText().toString();
            psw=edt2.getText().toString();
            if(name.isEmpty()){
                ShowToast("账号不能为空");
            }
            else if(psw.isEmpty()){
                ShowToast("密码不能为空");
            }
            else if(name.equals("617") && psw.equals("123")){
                ShowToast("登录成功");
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                LoginActivity.this.finish();
            }
            else{
                ShowToast("账号或密码错误！");
            }
        }
    };
}

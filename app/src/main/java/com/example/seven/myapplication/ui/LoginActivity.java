package com.example.seven.myapplication.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.model.LoginResult;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.service.LoginService;
import com.example.seven.myapplication.view.TitleBar;



public class LoginActivity extends BaseActivity {
    private EditText edt1;
    private EditText edt2;
    private Button btn;
    private String name;
    private String psw;
    private String title;
    private TitleBar titleBar;
    private LinearLayout show_login;
    private LinearLayout gone_login;
    private String showresult;
    private LoginService loginService;
    private CheckBox ckb;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取控件
        getview();
        //标题
        titleBar();
    }

    //网络连接状态
    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        //ShowToast("网络连接正常\n" + type.name());
        show_login.setVisibility(View.VISIBLE);
        gone_login.setVisibility(View.GONE);
    }

    //网络断开状态
    @Override
    protected void onNetworkDisConnected() {
        //ShowToast("请检测网络连接");
        //this.finish();
        show_login.setVisibility(View.GONE);
        gone_login.setVisibility(View.VISIBLE);
    }

    //获取控件
    private void getview() {
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        ckb= (CheckBox) findViewById(R.id.ckb);
        show_login = (LinearLayout) findViewById(R.id.show_login);
        gone_login = (LinearLayout) findViewById(R.id.gone_login);
        edt1 = (EditText) this.findViewById(R.id.name);
        edt2 = (EditText) this.findViewById(R.id.psd);
        titleBar = (TitleBar) findViewById(R.id.login_bar);
        btn = (Button) this.findViewById(R.id.btn);
        btn.setOnClickListener(tonext);
        ckb.setOnCheckedChangeListener(ifck);
        //判断是否记住密码
        if(sp.getBoolean("ISCHECK", false))
        {
            //默认记住密码
            ckb.setChecked(true);
            edt1.setText(sp.getString("USER_NAME", ""));
            edt2.setText(sp.getString("PASSWORD", ""));
        }

    }
    CompoundButton.OnCheckedChangeListener ifck=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (ckb.isChecked()) {
                sp.edit().putBoolean("ISCHECK", true).commit();
            }else {
                sp.edit().putBoolean("ISCHECK", false).commit();
            }
        }
    };
    //btn按钮点击事件
    View.OnClickListener tonext = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onClick(View v) {
            name = edt1.getText().toString();
            psw = edt2.getText().toString();
            if (name.isEmpty()) {
                ShowToast("账号不能为空");
            } else if (psw.isEmpty()) {
                ShowToast("密码不能为空");
            } else {
                Login();
            }
        }
    };




    //登陆操作
    private void Login() {
        loginService = new LoginService();
        //进行登录操作
        loginService.login(name, psw, new CommonCallback<String>() {
            @Override
            public void onSuccess(String data) {
                LoginResult result = loginService.getLoginResult(data);
                showresult = result.getResult();
                if (result.isSuccess()) {
                    if(ckb.isChecked())
                    {
                        //用户记住账号密码
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", name);
                        editor.putString("PASSWORD",psw);
                        editor.commit();
                    }
                    ShowToast("登录成功");
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    LoginActivity.this.finish();
                } else {

                }
            }

            @Override
            public void onFailure(String err_code, String message) {
                ShowToast("登录失败");
            }
        });
    }

    //标题
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


}

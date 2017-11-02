package com.example.seven.myapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.model.LoginResult;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.service.LoginService;
import com.example.seven.myapplication.view.SelfDialog;
import com.example.seven.myapplication.view.TitleBar;

import java.util.HashMap;
import java.util.Map;


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
    private SelfDialog selfDialog;
    private String showresult;

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
        show_login = (LinearLayout) findViewById(R.id.show_login);
        gone_login = (LinearLayout) findViewById(R.id.gone_login);
        edt1 = (EditText) this.findViewById(R.id.name);
        edt2 = (EditText) this.findViewById(R.id.psd);
        titleBar = (TitleBar) findViewById(R.id.login_bar);
        btn = (Button) this.findViewById(R.id.btn);
        btn.setOnClickListener(tonext);

        //测试使用  记得删除
        edt1.setText("617");
        edt2.setText("123");
    }

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
//                Login();
                Yesshowdialog();
            }
        }
        //登陆操作
        private void Login() {
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("password", psw);
            Api.testPost("post", map, new CommonCallback<String>() {
                @Override
                public void onSuccess(String data) {
                    LoginService loginService = new LoginService();
                    LoginResult result = loginService.toLoginResult(data);
                    showresult = result.getResult();
                    if (result.getCode() == 0) {
                        Yesshowdialog();
                    } else {
                        Noshowdialog();
                    }
                }

                @Override
                public void onFailure(String err_code, String message) {

                }
            });
        }
    };

    private void Yesshowdialog() {
        selfDialog = new SelfDialog(LoginActivity.this);
        selfDialog.setMessage("消息消息消息消息消息消息消息消息消息消息消息")
                .setTitle("系统提示")
                .setSingle(true).setOnClickBottomListener(new SelfDialog.OnClickBottomListener() {
            @Override
            public void onPositiveClick() {
                ShowToast("登录成功");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                LoginActivity.this.finish();
                selfDialog.dismiss();
            }

            @Override
            public void onNegtiveClick() {

            }
        }).show();
    }

    private void Noshowdialog() {
        selfDialog = new SelfDialog(LoginActivity.this);
        selfDialog.setTitle("提示");
        selfDialog.setMessage(showresult);
        selfDialog.setSingle(true).setOnClickBottomListener(new SelfDialog.OnClickBottomListener() {
            @Override
            public void onPositiveClick() {
                selfDialog.dismiss();
            }

            @Override
            public void onNegtiveClick() {

            }
        }).show();
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

package com.example.seven.myapplication.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.LoginData;
import com.example.seven.myapplication.model.NetworkResult;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.service.LoginService;
import com.example.seven.myapplication.view.TitleBar;
import com.roger.catloadinglibrary.CatLoadingView;



public class LoginActivity extends AppCompatActivity {
    private EditText edt1;
    private EditText edt2;
    private Button btn;
    private String name;
    private String psw;
    private String title;
    private TitleBar titleBar;
    private LinearLayout showLogin;
    private LinearLayout goneLogin;
    private LoginService loginService;
    private CheckBox ckb;
    private SharedPreferences sp;
    private CatLoadingView mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = LoginActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //获取控件
        getview();
        //标题
        titleBar();
    }

    //获取控件
    private void getview() {
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        ckb= (CheckBox) findViewById(R.id.ckb);
        showLogin = (LinearLayout) findViewById(R.id.show_login);
        goneLogin = (LinearLayout) findViewById(R.id.gone_login);
        edt1 = (EditText) this.findViewById(R.id.name);
        edt2 = (EditText) this.findViewById(R.id.psd);
        titleBar = (TitleBar) findViewById(R.id.login_bar);
        btn = (Button) this.findViewById(R.id.btn);
        btn.setOnClickListener(tonext);
        ckb.setOnCheckedChangeListener(ifck);
        mView=new CatLoadingView();
        //判断是否记住密码
        if(sp.getBoolean("ISCHECK", false)) {
            //默认记住密码
            ckb.setChecked(true);
            edt1.setText(sp.getString("USER_NAME", ""));
            edt2.setText(sp.getString("PASSWORD", ""));
        }
    }

    CompoundButton.OnCheckedChangeListener ifck = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (ckb.isChecked()) {
                sp.edit().putBoolean("ISCHECK", true).apply();
            } else {
                sp.edit().putBoolean("ISCHECK", false).apply();
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
                showToast("账号不能为空");
            } else if (psw.isEmpty()) {
                showToast("密码不能为空");
            } else {
                try {
                    Login();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    //登陆操作
    private void Login() throws NoSuchFieldException, IllegalAccessException {
        loginService = new LoginService();
        showDialog();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mView.dismiss();
//                showToast("登陆超时");
            }
        }, 10000);
        //进行登录操作
        loginService.login(name, psw, new CommonCallback<NetworkResult<LoginData>>() {
            @Override
            public void onSuccess(NetworkResult<LoginData> data) {
                if (APIConstants.CODE_RESULT_SUCCESS.equals(data.getStatus())) {
                    mView.dismiss();
                    if(ckb.isChecked()) {

                        //用户记住账号密码
                        SharedPreferences.Editor editor = sp.edit();
                        //需要添加加密
                        editor.putString("USER_NAME", name);
                        editor.putString("PASSWORD", psw);
                        editor.commit();
                    }
                    showToast("最近登录时间："+data.getData().getLastLoginTime());
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    LoginActivity.this.finish();
                } else {

                    mView.dismiss();
                }
            }

            @Override
            public void onFailure(String err_code, String message) {

                mView.dismiss();
                showToast("登录失败");
            }
        });
    }

    public void showDialog() {
        mView.show(getSupportFragmentManager(), "");
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

    //Toast及时反应
    private Toast mToast;

    public void showToast(String text) {
        try {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

}

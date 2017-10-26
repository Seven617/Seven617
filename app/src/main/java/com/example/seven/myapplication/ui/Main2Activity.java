package com.example.seven.myapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.view.OwlView;
import com.example.seven.myapplication.view.TitleBar;

public class Main2Activity extends BsaeActivity {
    private EditText edt1;
    private EditText edt2;
    private Button btn;
    private String name;
    private String psw;
    private String title;
    private OwlView mOwlView;
    private TitleBar titleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getview();
        titleBar();
    }

    private void getview() {
        edt1= (EditText) this.findViewById(R.id.name);
        edt2= (EditText) this.findViewById(R.id.psd);
        titleBar = (TitleBar) findViewById(R.id.login_bar);
        mOwlView= (OwlView) findViewById(R.id.owl_view);
        btn= (Button) this.findViewById(R.id.btn);
        edt2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mOwlView.open();

                }else{
                    mOwlView.close();
                }
            }
        });
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
                Intent intent=new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Main2Activity.this.finish();
            }
            else{
                ShowToast("账号或密码错误！");
            }
        }
    };
}

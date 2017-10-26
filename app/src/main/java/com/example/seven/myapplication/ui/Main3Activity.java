package com.example.seven.myapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.view.TitleBar;

public class Main3Activity extends AppCompatActivity {
    private TitleBar titleBar;
    private Button zfbpay;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
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
                Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
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


}

package com.example.seven.myapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.view.TitleBar;

public class ChoseQueryActivity extends BaseActivity {
    private TitleBar titleBar;
    private String title;
    private Button one_query;
    private Button more_query;
    private Button all_query;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_query);
        //获取控件
        getview();
        //标题
        titleBar();
    }
    //获取控件
    private void getview() {
        titleBar = (TitleBar) findViewById(R.id.chose_query_bar);
        one_query= (Button) findViewById(R.id.one_query);
        more_query= (Button) findViewById(R.id.more_query);
        all_query= (Button) findViewById(R.id.all_query);

        one_query.setOnClickListener(todo);
        all_query.setOnClickListener(todo);
    }
    View.OnClickListener todo=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.one_query){
                intent = new Intent(ChoseQueryActivity.this, OneQueryActivity.class);
            }
            if(v.getId()==R.id.all_query){
                intent = new Intent(ChoseQueryActivity.this, AllQueryActivity.class);
            }
            startActivity(intent);
        }
    };
    //标题
    private void titleBar() {
        //左边返回按钮
        //titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftTextSize(15);
        title = "选择查询类型";
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
            ChoseQueryActivity.this.finish();
        }
    };
    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
}

package com.example.seven.myapplication.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.view.ClearEditText;
import com.example.seven.myapplication.view.TitleBar;

//单号查询
public class QueryActivity extends BsaeActivity {
    private TitleBar titleBar;
    private String title;
    private ClearEditText EditText;
    private Button btn_sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        getview();
        titleBar();
    }

    private void titleBar() {
        //左边返回按钮
        //titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftTextSize(15);
        title = "单号查询";
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

    private void getview() {
        titleBar = (TitleBar) findViewById(R.id.query_bar);
        btn_sure = (Button) findViewById(R.id.query_btn_sure);
        EditText = (ClearEditText) findViewById(R.id.query_edittext);
        btn_sure.setOnClickListener(OK);
    }

    View.OnClickListener OK = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    View.OnClickListener ck = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            QueryActivity.this.finish();
        }
    };
    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }


}

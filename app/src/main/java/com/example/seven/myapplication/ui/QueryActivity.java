package com.example.seven.myapplication.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.view.ClearEditText;
import com.example.seven.myapplication.view.TitleBar;

//单号查询
public class QueryActivity extends BaseActivity {
    private TitleBar titleBar;
    private String title;
    private ClearEditText EditText;
    private Button btn_sure;
    private LinearLayout show_query;
    private LinearLayout gone_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        //获取控件
        getview();
        //标题
        titleBar();
    }

    //网络连接状态（一切正常显示）
    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        show_query.setVisibility(View.VISIBLE);
        gone_query.setVisibility(View.GONE);
    }

    //网络断开状态（原本界面隐藏 显示"当前网络不可用"）
    @Override
    protected void onNetworkDisConnected() {
        show_query.setVisibility(View.GONE);
        gone_query.setVisibility(View.VISIBLE);
    }

    //获取控件
    private void getview() {
        titleBar = (TitleBar) findViewById(R.id.query_bar);
        btn_sure = (Button) findViewById(R.id.query_btn_sure);
        EditText = (ClearEditText) findViewById(R.id.query_edittext);
        show_query = (LinearLayout) findViewById(R.id.show_query);
        gone_query = (LinearLayout) findViewById(R.id.gone_query);
        btn_sure.setOnClickListener(OK);
    }

    //btn按钮点击事件
    View.OnClickListener OK = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    //标题
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


    //左边返回按钮点击事件
    View.OnClickListener ck = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            QueryActivity.this.finish();
        }
    };


}

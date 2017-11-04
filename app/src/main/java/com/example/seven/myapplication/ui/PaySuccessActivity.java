package com.example.seven.myapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.view.TitleBar;

/**
 * Created by daichen on 2017/11/4.
 */

public class PaySuccessActivity extends BaseActivity{

    private TitleBar titleBar;
    private String title;
    private Button button ;
    private String paySuccessAmount;
    private TextView paySuccessAmountTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);

        getview();
        titleBar();

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }


    private void getview() {
        titleBar = (TitleBar) findViewById(R.id.pay_success_bar);
        button= (Button) findViewById(R.id.pay_success_sure);

        paySuccessAmountTextView=(TextView)findViewById(R.id.pay_success_amount);
        Intent intent =getIntent();
        paySuccessAmount=intent.getStringExtra("amount");
        paySuccessAmountTextView.setText(paySuccessAmount);

        button.setOnClickListener(ck);
    }

    //标题
    private void titleBar() {
        //左边返回按钮
        //titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftTextSize(15);
        title = "支付结果";
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
            PaySuccessActivity.this.finish();
        }
    };
}

package com.example.seven.myapplication.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.seven.myapplication.R;
import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.QueryOrderData;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.service.PrintPosService;
import com.example.seven.myapplication.view.TitleBar;

/**
 * Created by daichen on 2017/11/4.
 */

public class PaySuccessActivity extends BaseActivity{

    private TitleBar titleBar;
    private String title;
    private Button buttonTrue ;
    private Button buttonPrintf;
    private String dataString;
    private String amount;
    private QueryOrderData queryOrderData;
    private TextView paySuccessAmountTextView;
    private PrintPosService printPosService ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        getView();
        titleBar();

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }


    private void getView() {
        titleBar = (TitleBar) findViewById(R.id.pay_success_bar);
        buttonTrue= (Button) findViewById(R.id.pay_success_sure);
        buttonPrintf = (Button) findViewById(R.id.pay_success_printf);
        paySuccessAmountTextView=(TextView)findViewById(R.id.pay_success_amount);
        Intent intent =getIntent();
        //获取上个页面传来的参数
        dataString=intent.getStringExtra("data");
        amount = intent.getStringExtra(APIConstants.STRING_AMOUNT);
        queryOrderData= JSONObject.parseObject(dataString, QueryOrderData.class);
        queryOrderData.setAmount(amount);
        paySuccessAmountTextView.setText(amount);
        buttonPrintf.setOnClickListener(printf);
        buttonTrue.setOnClickListener(ck);
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

    //左边返回按钮点击事件
    View.OnClickListener printf = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            printPosService.printQueryOrder(queryOrderData);

        }
    };




    @Override
    protected void onResume() {
        super.onResume();
        //绑定设备开启
        bindDeviceService();
        //实现printer方法
        printPosService  = new PrintPosService(this);
    }
}

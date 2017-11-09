package com.example.seven.myapplication.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.seven.myapplication.R;
import com.example.seven.myapplication.model.NetworkResult;
import com.example.seven.myapplication.model.TodayOrderData;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.service.PrintPosService;
import com.example.seven.myapplication.service.TodayOrderServer;
import com.example.seven.myapplication.view.TitleBar;

/**
 * Created by daichen on 2017/11/9.
 */

public class TodayOrderActivity extends BaseActivity {
    private String title;
    private TitleBar titleBar ;
    private TextView refundAmount;
    private TextView refundCount;
    private TextView successAmount;
    private TextView successCount;
    private PrintPosService printPosService;
    private TodayOrderServer todayOrderServer ;
    private Button buttonTrue;
    private Button buttonPrint;
    private TodayOrderData todayOrderData ;




    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_order);
        getView();
        //标题
        titleBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindDeviceService();
        printPosService = new PrintPosService(TodayOrderActivity.this);


    }

    private void getView() {
        titleBar = (TitleBar) findViewById(R.id.today_order_bar);
        refundAmount = (TextView)findViewById(R.id.today_order_refund_amount);
        refundCount = (TextView) findViewById(R.id.today_order_refund_count);
        successAmount = (TextView)findViewById(R.id.today_order_success_amount);
        successCount = (TextView)findViewById(R.id.today_order_success_count);
        buttonTrue = (Button)findViewById(R.id.today_order_sure);
        buttonPrint = (Button)findViewById(R.id.today_order_print);
        //访问接口获得数据
        todayOrderServer = new TodayOrderServer();
        buttonTrue.setOnClickListener(ck);
        buttonPrint.setOnClickListener(print);
        todayOrderServer.refund(new CommonCallback<NetworkResult<TodayOrderData>>() {
            @Override
            public void onSuccess(NetworkResult<TodayOrderData> data) {
                todayOrderData=data.getData();
                refundAmount.setText(todayOrderData.getRefundAmount());
                refundCount.setText(todayOrderData.getRefundCount());
                successAmount.setText(todayOrderData.getSuccessAmount());
                successCount.setText(todayOrderData.getSuccessCount());
//                showToast("返回成功的信息");
            }

            @Override
            public void onFailure(String error_code, String error_message) {
                showToast("返回失败的信息");
            }
        });


    }

    //标题
    private void titleBar() {
        //左边返回按钮
        //titleBar.setLeftImageResource(R.mipmap.back);
        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftTextSize(15);
        title = "今日结算";
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
            TodayOrderActivity.this.finish();
        }
    };


    View.OnClickListener print = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                printPosService.printTodayOrder(todayOrderData);

        }
    };
}

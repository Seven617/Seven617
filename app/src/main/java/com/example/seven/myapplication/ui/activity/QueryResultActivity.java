package com.example.seven.myapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.printservice.PrintService;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.seven.myapplication.Enums.OrderStatusEnum;
import com.example.seven.myapplication.R;
import com.example.seven.myapplication.model.QueryOrderData;
import com.example.seven.myapplication.network.NetUtils;
import com.example.seven.myapplication.service.PrintPosService;
import com.example.seven.myapplication.util.DateStyle;
import com.example.seven.myapplication.util.DateUtil;

import java.util.List;

/**
 * Created by daichen on 2017/11/16.
 */

public class QueryResultActivity extends BaseActivity {

   private TextView orderSnTextView;
   private TextView amountTextView;
   private TextView payTimeTextView;
   private TextView payTypeTextView;
   private TextView payStatusTextView;
//   private QueryOrderData[] queryOrderDataList;
   private QueryOrderData queryOrderData;
   private Button querypopbtnsure;
   private Button querypopprint;
   private PrintPosService printPosService ;


    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.querypopupwindow);
        getView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindDeviceService();
        printPosService  = new PrintPosService(this);
    }

    private void getView() {
        orderSnTextView = (TextView)findViewById(R.id.order_sn_text);
        amountTextView = (TextView)findViewById(R.id.pop_amount);
        payTimeTextView = (TextView)findViewById(R.id.pay_time_text);
        payTypeTextView = (TextView)findViewById(R.id.pay_type_text);
        payStatusTextView = (TextView)findViewById(R.id.pay_status_text);
        Intent intent = getIntent();
        String data  = intent.getStringExtra("data");
        queryOrderData = JSONObject.parseObject(data,QueryOrderData.class);
        orderSnTextView.setText(queryOrderData.getOrderSn());
        amountTextView.setText(queryOrderData.getRMBAmount());
        payTimeTextView.setText(DateUtil.TimestampToString(Long.valueOf( queryOrderData.getModifyDate()), DateStyle.YYYY_MM_DD_HH_MM_SS_EN));
        payStatusTextView.setText(queryOrderData.getStringStatus());
        payTypeTextView.setText(queryOrderData.getPayTypeTxt());

        querypopbtnsure = (Button)findViewById(R.id.query_popsure);//左边确定按钮
        querypopprint = (Button)findViewById(R.id.query_popprint);//右边打印按钮
        querypopprint.setOnClickListener(ck);
        querypopbtnsure.setOnClickListener(ck);
    }

    View.OnClickListener ck = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.query_popsure){
                QueryResultActivity.this.finish();
            }
            else {
                printPosService.printQueryOrder(queryOrderData);
            }
        }
    };



}

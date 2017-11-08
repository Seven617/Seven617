package com.example.seven.myapplication.service;

import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.RefundData;
import com.example.seven.myapplication.model.RefundRequest;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;
import com.jady.retrofitclient.callback.HttpCallback;

/**
 * Created by daichen on 2017/11/2.
 */

public class RefundService {

    public void refund(String orderSn,String  confirmPassword,CommonCallback commonCallback){
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderSn(orderSn);
        refundRequest.setConfirmPassword(confirmPassword);
        System.out.println("发送了退款请求");
        Api.post(APIConstants.URL_REFUND_ORDER,refundRequest.getMap(), commonCallback);

    }


}

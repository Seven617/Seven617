package com.example.seven.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.RefundData;
import com.example.seven.myapplication.model.RefundRequest;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;
import com.jady.retrofitclient.callback.HttpCallback;

/**
 * Created by daichen on 2017/11/2.
 */

public class RefundService extends Service {

    public void refund(String orderSn,String  confirmPassword,CommonCallback commonCallback){
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderSn(orderSn);
        refundRequest.setConfirmPassword(confirmPassword);
        Api.post(APIConstants.URL_REFUND_ORDER,refundRequest.getMap(), commonCallback);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

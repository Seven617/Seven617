package com.example.seven.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.ScannerPayRequest;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;

import java.math.BigDecimal;

/**
 * Created by daichen on 2017/11/2.
 */

public class PayService  extends Service{

    public void pay(String amount,String  barCode,CommonCallback commonCallback){

        ScannerPayRequest scannerPayRequest = new ScannerPayRequest();
        amount = String.valueOf(new BigDecimal(amount).multiply(new BigDecimal(100)).intValue());

        scannerPayRequest.setAmount(amount);
        scannerPayRequest.setBarCode(barCode);

        Api.post(APIConstants.URL_SCAN_PAY, scannerPayRequest.getMap(),commonCallback);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


//    public PayResult getPayResult(String data){
//        NetworkResult result = JSONObject.parseObject(data,NetworkResult.class);
//        PayResult payResult = new PayResult();
//        if( APIConstants.CODE_RESULT_SUCCESS.equals(result.getStatus())){
//            payResult.setSuccess(true);
//            payResult.setResult(result.getData());
//        }else {
//            payResult.setMsg(result.getMsg());
//        }
//
//        return payResult;
//    }
}

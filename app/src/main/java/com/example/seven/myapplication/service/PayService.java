package com.example.seven.myapplication.service;

import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.PayRequest;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;

import java.math.BigDecimal;

/**
 * Created by daichen on 2017/11/2.
 */

public class PayService {

    public void pay(String amount,String  barCode,CommonCallback commonCallback){

        PayRequest payRequest = new PayRequest();
        BigDecimal bigDecimal = new BigDecimal(amount);
        amount = String.valueOf(bigDecimal.multiply(new BigDecimal(100)).intValue());

        payRequest.setAmount(amount);
        payRequest.setBarCode(barCode);

        Api.post(APIConstants.URL_SCAN_PAY,payRequest.getMap(),commonCallback);
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

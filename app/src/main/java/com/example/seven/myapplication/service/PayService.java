package com.example.seven.myapplication.service;

import com.alibaba.fastjson.JSONObject;
import com.example.seven.myapplication.R;
import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.NetworkResult;
import com.example.seven.myapplication.model.PayRequest;
import com.example.seven.myapplication.model.PayResult;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daichen on 2017/11/2.
 */

public class PayService {

    public void pay(String amount,String  payCode,CommonCallback commonCallback){

        PayRequest payRequest = new PayRequest();
        payRequest.setAmount(amount);
        payRequest.setPayCode(payCode);

        Api.post(APIConstants.URL_SCAN_PAY,payRequest.getMap(),commonCallback);
    }


    public PayResult getPayResult(String data){
        NetworkResult result = JSONObject.parseObject(data,NetworkResult.class);
        PayResult payResult = new PayResult();
        if( APIConstants.CODE_RESULT_SUCCESS.equals(result.getStatus())){
            payResult.setSuccess(true);
            payResult.setResult(result.getData());
        }else {
            payResult.setMsg(result.getMsg());
        }

        return payResult;
    }
}

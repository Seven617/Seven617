package com.example.seven.myapplication.service;

import com.alibaba.fastjson.JSONObject;
import com.example.seven.myapplication.R;
import com.example.seven.myapplication.model.NetworkResult;
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

        Map map = new HashMap();
        Api.post("post",map,commonCallback);
    }


    public PayResult getPayResult(String data){
        NetworkResult result = JSONObject.parseObject(data,NetworkResult.class);
        PayResult payResult = new PayResult();

        return payResult;
    }
}

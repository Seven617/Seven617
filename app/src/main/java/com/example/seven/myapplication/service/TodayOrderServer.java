package com.example.seven.myapplication.service;

import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.RefundRequest;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;

/**
 * Created by daichen on 2017/11/9.
 */

public class TodayOrderServer {
    public void refund(CommonCallback commonCallback){
        Api.get(APIConstants.URL_TODAY_ORDER,null, commonCallback);
    }
}

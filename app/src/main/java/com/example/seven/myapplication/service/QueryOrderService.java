package com.example.seven.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.QueryOrderRequest;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;

/**
 * Created by daichen on 2017/11/2.
 */

public class QueryOrderService extends Service {

    public void queryByOrderNo(String orderNo, CommonCallback commonCallback){
        QueryOrderRequest queryOrderRequest = new QueryOrderRequest();
        queryOrderRequest.setOrderSn(orderNo);
        Api.get(APIConstants.URL_QUERY_ORDER, queryOrderRequest.getMapByFilter(),commonCallback);

    }


    public void queryOrder(int pageSize, int pageNumber, CommonCallback commonCallback){
        QueryOrderRequest queryOrderRequest = new QueryOrderRequest();
        queryOrderRequest.setPageSize(pageSize);
        queryOrderRequest.setCurrent(pageNumber);
        Api.get(APIConstants.URL_QUERY_ORDER, queryOrderRequest.getMapByFilter(),commonCallback);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

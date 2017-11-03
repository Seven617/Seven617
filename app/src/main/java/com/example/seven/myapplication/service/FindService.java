package com.example.seven.myapplication.service;

import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.FindRequest;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;

/**
 * Created by daichen on 2017/11/2.
 */

public class FindService {

    public void findByOrderNo(String orderNo, CommonCallback commonCallback){
        FindRequest findRequest = new FindRequest();
        findRequest.setOrderNo(orderNo);
        Api.post(APIConstants.URL_ORDER_NO_FIND,findRequest.getMap(),commonCallback);

    }


    public void findAllOrder(CommonCallback commonCallback){

    }


}

package com.example.seven.myapplication.model;

import com.example.seven.myapplication.constants.APIConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daichen on 2017/11/3.
 */

public class FindRequest {
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Map getMap(){
        Map<String,String > map = new HashMap<>();
        map.put(APIConstants.STRING_ORDER_NO,this.orderNo);

        return map;
    }


}

package com.example.seven.myapplication.model;

import com.example.seven.myapplication.constants.APIConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daichen on 2017/11/8.
 */

public class RefundRequest {
    private String orderSn;
    private String confirmPassword;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Map getMap(){
        Map<String,String > map = new HashMap<>();
        map.put(APIConstants.STRING_ORDER_NO,this.orderSn);
        map.put(APIConstants.STRING_CONFIRM_PASSWORD,this.confirmPassword);
        return map;
    }
}

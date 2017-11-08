package com.example.seven.myapplication.model;

import com.example.seven.myapplication.constants.APIConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daichen on 2017/11/2.
 */

public class ScannerPayRequest {
    private String amount;
    private String barCode;
    private String sign;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    public Map getMap(){
        Map map = new HashMap();
        map.put(APIConstants.STRING_AMOUNT,this.amount);
        map.put(APIConstants.STRING_BAR_CODE,this.barCode);
        map.put(APIConstants.STRING_SIGN,this.sign);
        return map;
    }
}

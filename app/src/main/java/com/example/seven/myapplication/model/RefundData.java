package com.example.seven.myapplication.model;

import java.math.BigDecimal;

/**
 * Created by daichen on 2017/11/2.
 */

public class RefundData {
    private String orderSn;

    private String shopName;

    private String payType;

    private String buyerId;

    private String operatorName;

    private String merName;
    private String amount;

    private String payTypeTxt;

    private String modifyDate;

    public String getPayTypeTxt() {
        return payTypeTxt;
    }

    public void setPayTypeTxt(String payTypeTxt) {
        this.payTypeTxt = payTypeTxt;
    }

    public String getAmount() {

        if(null != amount){
            BigDecimal bigDecimal = new BigDecimal(amount);
            return bigDecimal.multiply(new BigDecimal(100)).toString();

        }

        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }
}

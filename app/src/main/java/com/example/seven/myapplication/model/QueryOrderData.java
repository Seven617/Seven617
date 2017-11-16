package com.example.seven.myapplication.model;

import com.example.seven.myapplication.Enums.OrderStatusEnum;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by seven617 on 2017/11/3.
 */

public class QueryOrderData {

    private String orderSn;
    private String payType;
    private String payTypeTxt;
    private String shopName;
    private String buyerId;
    private String operatorName;
    private String merName;
    private String address;
    private String modifyDate;
    private String amount;
    private Integer status;

    public Integer getStatus() {

        return status;
    }



    public String getStringStatus(){
        if(null != status){
            return OrderStatusEnum.getOrderStatusEnum(this.status).getDesc();
        }

        return null;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }



    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayTypeTxt() {
        return payTypeTxt;
    }

    public void setPayTypeTxt(String payTypeTxt) {
        this.payTypeTxt = payTypeTxt;
    }



    public String getAmount() {
       return amount;
    }

    public String getRMBAmount(){
        if(null != amount){
            BigDecimal bigDecimal = new BigDecimal(amount);
            return bigDecimal.divide(new BigDecimal(100)).toString();

        }
        return amount;
    }


    public void setAmount(String amount) {
        this.amount = amount;
    }


}

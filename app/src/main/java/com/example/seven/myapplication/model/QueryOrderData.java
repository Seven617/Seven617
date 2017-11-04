package com.example.seven.myapplication.model;

import java.util.ArrayList;

/**
 * Created by seven617 on 2017/11/3.
 */

public class QueryOrderData {

  private String id;
  private String merSn;
  private String createDate;
  private String modifyDate;
  private String orderSn;
  private String tradeNo;
  private String description;
  private String payType;
  private String payTypeTxt;
  private String channelSn;
  private String channelText;
  private String amount;
  private Integer status;
  private String remark;
  private String createUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerSn() {
        return merSn;
    }

    public void setMerSn(String merSn) {
        this.merSn = merSn;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getChannelSn() {
        return channelSn;
    }

    public void setChannelSn(String channelSn) {
        this.channelSn = channelSn;
    }

    public String getChannelText() {
        return channelText;
    }

    public void setChannelText(String channelText) {
        this.channelText = channelText;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "QueryOrderData{" +
                "id='" + id + '\'' +
                ", merSn='" + merSn + '\'' +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", orderSn='" + orderSn + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", description='" + description + '\'' +
                ", payType='" + payType + '\'' +
                ", payTypeTxt='" + payTypeTxt + '\'' +
                ", channelSn='" + channelSn + '\'' +
                ", channelText='" + channelText + '\'' +
                ", amount='" + amount + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", createUser='" + createUser + '\'' +
                '}';
    }
}

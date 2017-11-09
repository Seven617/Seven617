package com.example.seven.myapplication.model;

import com.alibaba.fastjson.JSONObject;
import com.example.seven.myapplication.constants.APIConstants;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by daichen on 2017/11/3.
 */

public class QueryOrderRequest {

    private Integer current;
    private Integer pageSize;
    private String orderSn;
    private String startDate;
    private String endDate;
    private String channelSn;
    private String type;
    private String status;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getChannelSn() {
        return channelSn;
    }

    public void setChannelSn(String channelSn) {
        this.channelSn = channelSn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String  getJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("current",current);
        jsonObject.put("pageSize",pageSize);
        jsonObject.put("orderSn",orderSn);
        jsonObject.put("startDate",startDate);
        jsonObject.put("endDate",endDate);
        jsonObject.put("channelSn",channelSn);
        jsonObject.put("type",type);
        jsonObject.put("status",status);
        return jsonObject.toJSONString();
    }



    public Map getMapByFilter(){
        Map<String,String > map = new HashMap<>();
        map.put(APIConstants.STRING_FILTER, getJson());
        return map;
    }


}

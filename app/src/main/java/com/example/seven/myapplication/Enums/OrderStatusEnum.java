package com.example.seven.myapplication.Enums;

/**
 * Created by daichen on 2017/11/5.
 */

public enum OrderStatusEnum {

    NEW(0,"新建"),SENDING(1,"处理中"),SUCCESS(2,"成功"),FAIL(3,"交易失败"),CANCEL(4,"交易取消"),ERROR(5,"异常出错"),CALLING(6,"等待处理结果"),UNKNOWN(99,"未知");
    private Integer status;
    private String desc;


    public static OrderStatusEnum getOrderStatusEnum(Integer status){

        for(OrderStatusEnum orderStatusEnum: OrderStatusEnum.values()){
            if(orderStatusEnum.status.equals(status) ){
                return orderStatusEnum;
            }

        }


        return UNKNOWN;
    }


    OrderStatusEnum(int status,String desc){
        this.status=status;
        this.desc=desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



}

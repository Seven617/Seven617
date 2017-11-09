package com.example.seven.myapplication.model;

import java.math.BigDecimal;

/**
 * Created by daichen on 2017/11/9.
 */

public class TodayOrderData {

    private String successAmount;
    private String successCount;
    private String refundCount;
    private String refundAmount;

    public String getSuccessAmount() {
        if(null != successAmount){
            BigDecimal bigDecimal = new BigDecimal(successAmount);
            return bigDecimal.divide(new BigDecimal(100)).toString();

        }
        return successAmount;
    }

    public void setSuccessAmount(String successAmount) {
        this.successAmount = successAmount;
    }

    public String getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(String successCount) {
        this.successCount = successCount;
    }

    public String getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(String refundCount) {
        this.refundCount = refundCount;
    }

    public String getRefundAmount() {

        if(null != refundAmount){
            BigDecimal bigDecimal = new BigDecimal(refundAmount);
            return bigDecimal.divide(new BigDecimal(100)).toString();

        }
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }
}

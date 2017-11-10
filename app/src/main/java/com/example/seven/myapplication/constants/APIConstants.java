package com.example.seven.myapplication.constants;

import com.example.seven.myapplication.model.QueryOrderData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by daichen on 2017/11/2.
 */


public  interface APIConstants {

     String URL_USER_LOGIN="pos/signon/dologin";
     String URL_SCAN_PAY = "pos/pay";
     String URL_QUERY_ORDER ="pos/order/list";
     String URL_REFUND_ORDER="/pos/refund";
     String URL_TODAY_ORDER="/pos/order/today";
     String URL_USER_LOGOUT="/pos/signon/dologout";

     String STRING_USERNAME="username";
     String STRING_PASSWORD="password";
     String STRING_DEVICE_NO="deviceNo";
     String STRING_AMOUNT="amount";
     String STRING_BAR_CODE="barCode";
     String STRING_SIGN="sign";
     String STRING_ORDER_SN="orderSn";
     String STRING_FILTER="filter";
     String STRING_USER_INFO="userInfo";
     String STRING_CONFIRM_PASSWORD="confirmPassword";

     String STRING_IS_CHECK="ISCHECK";



     String MSG_RESULT_NO_LOGIN_ERROR="请先登陆后再进行操作!";
     String MSG_RESULT_SESSION_TIMEOUT_ERROR="用户登陆已失效,请重新登陆!";


     String CODE_RESULT_NO_LOGIN_ERROR="4";
     String CODE_RESULT_SESSION_TIMEOUT_ERROR="5";
     String CODE_RESULT_SUCCESS ="0";
}

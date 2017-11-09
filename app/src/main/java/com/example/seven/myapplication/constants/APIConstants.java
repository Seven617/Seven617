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





     String CODE_RESULT_SUCCESS ="0";
     //http://gank.io/api/data/福利/5/1
     @GET("api/data/福利/{pageCount}/{pageIndex}")
     Call<QueryOrderData> getData(@Path("pageCount") int pageCount,
                                  @Path("pageIndex") int pageIndex);
}

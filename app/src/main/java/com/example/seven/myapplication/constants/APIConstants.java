package com.example.seven.myapplication.constants;

import com.example.seven.myapplication.model.QueryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by daichen on 2017/11/2.
 */


public  interface APIConstants {

     String URL_USER_LOGIN="post";
     String URL_SCAN_PAY = "postAliPay";
     String STRING_NAME="name";
     String STRING_PASSWORD="password";
     String STRING_POS_SN="posSn";
     String STRING_AMOUNT="amount";
     String STRING_PAY_CODE="payCode";
     String STRING_SIGN="sign";

     String CODE_RESULT_SUCCESS ="0";
     //http://gank.io/api/data/福利/5/1
     @GET("api/data/福利/{pageCount}/{pageIndex}")
     Call<QueryResult> getData(@Path("pageCount") int pageCount,
                               @Path("pageIndex") int pageIndex);
}

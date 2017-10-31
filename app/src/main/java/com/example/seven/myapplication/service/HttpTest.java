package com.example.seven.myapplication.service;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by daichen on 2017/10/31.
 */

public class HttpTest {

    public static void main(String[] args) throws IOException {
        HttpTest httpTest = new HttpTest();
        System.out.println(httpTest.requset());

    }




    public String requset() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.9.145:8880/")
                .build();

        MyRetrofit2Service service = retrofit.create(MyRetrofit2Service.class);


        Call<ResponseBody> repos = service.requestTest(2);



//        Call<ResponseBody> responseBodyCall = repos.clone();


        Response<ResponseBody> bodyResponse= repos.execute();

        return bodyResponse.body().string();

    }



}

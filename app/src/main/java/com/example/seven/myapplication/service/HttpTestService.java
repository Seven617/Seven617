package com.example.seven.myapplication.service;

import android.app.Activity;
import android.app.job.JobInfo;
import android.util.Log;
import android.webkit.CookieManager;

import com.example.seven.myapplication.Utils.AppClient;
import com.example.seven.myapplication.Utils.CookieUtil;
import com.example.seven.myapplication.ui.LoginActivity;

import java.io.IOException;
import java.net.CookieHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by daichen on 2017/10/30.
 */

public class HttpTestService {


    private static HttpTestService httpTestService ;

    public static HttpTestService getService(){
        if(httpTestService == null){
            return new HttpTestService();
        }
        return httpTestService;
    }


    public static void main(String[] args) throws InterruptedException {
            HttpTestService httpTestService =HttpTestService.getService();
//            Thread.sleep(1000l);
            httpTestService.testHttp();


    }

    public void testHttp() {


        //1.创建Retrofit实例
        Retrofit retrofit = AppClient.retrofit();

        //2.创建代理对象
        MyRetrofit2Service service = retrofit.create(MyRetrofit2Service.class);
        //3.调用方法
//        Call<ResponseBody> call = service.get();

        Call<ResponseBody> call1 = service.requestTest(100);


        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("掉用了失败的方法啊");
            }
        });

        // 用法和OkHttp的call如出一辙,
        // 不同的是如果是Android系统回调方法执行在主线程
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String cookie = response.headers().get("Set-Cookie");
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

    }
}

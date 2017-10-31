package com.example.seven.myapplication.Utils;

import com.example.seven.myapplication.contants.ApiStores;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by daichen on 2017/10/30.
 */

public class AppClient {

    public static Retrofit retrofit = null;

    public static Retrofit retrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            //公共参数
//            Interceptor addQueryParameterInterceptor = new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request originalRequest = chain.request();
//                    Request request;
//                    String method = originalRequest.method();
//                    Headers headers = originalRequest.headers();
//                    HttpUrl modifiedUrl = originalRequest.url().newBuilder()
//                            // Provide your custom parameter here
//                            .addQueryParameter("platform", "android")
//                            .addQueryParameter("version", "1.0.0")
//                            .build();
//                    request = originalRequest.newBuilder().url(modifiedUrl).build();
//                    return chain.proceed(request);
//                }
//            };
//            builder.addInterceptor(addQueryParameterInterceptor);

            //设置头
//            Interceptor headerInterceptor = new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request originalRequest = chain.request();
//                    Request.Builder requestBuilder = originalRequest.newBuilder()
//                            .header("AppType", "TPOS")
//                            .header("Content-Type", "application/json")
//                            .header("Accept", "application/json")
//                            .method(originalRequest.method(), originalRequest.body());
//                    Request request = requestBuilder.build();
//                    return chain.proceed(request);
//                }
//            };
//
//            builder.addInterceptor(headerInterceptor );

            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            builder.cookieJar(new JavaNetCookieJar(cookieManager));

            //设置超时
            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);

            //错误重连
            builder.retryOnConnectionFailure(true);

            //以上设置结束，才能build(),不然设置白搭
            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiStores.API_SERVER_URL)
                    //设置 Json 转换器
//                    .addConverterFactory(GsonConverterFactory.create())
                    //RxJava 适配器
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

    }
}


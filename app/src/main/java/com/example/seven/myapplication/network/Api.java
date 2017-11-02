package com.example.seven.myapplication.network;

import com.jady.retrofitclient.HttpManager;
import com.jady.retrofitclient.callback.HttpCallback;

import java.util.Map;

/**
 * Created by seven617 on 2017/11/1.
 */

public class Api {
    public static void testPost(String url, Map map, HttpCallback callback) {
        HttpManager.postByBody(url, map, callback);
    }
}

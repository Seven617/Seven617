package com.example.seven.myapplication.util;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by daichen on 2017/11/8.
 */

public class main {
    public static void main(String[] args) {

        //FastJson生成json数据
        String jsonData = "戴晨是大傻逼";
//
//        Log.e("MainActivity", "AES加密前json数据 ---->" + jsonData);
//        Log.e("MainActivity", "AES加密前json数据长度 ---->" + jsonData.length());

        //生成一个动态key
        String secretKey = AesUtils.generateKey();
        System.out.println(secretKey);
//        Log.e("MainActivity", "AES动态secretKey ---->" + secretKey);

        //AES加密
        long start = System.currentTimeMillis();
        String encryStr = AesUtils.encrypt(secretKey, jsonData);
        System.out.println(encryStr);
        long end = System.currentTimeMillis();
//        Log.e("MainActivity", "AES加密耗时 cost time---->" + (end - start));
//        Log.e("MainActivity", "AES加密后json数据 ---->" + encryStr);
//        Log.e("MainActivity", "AES加密后json数据长度 ---->" + encryStr.length());

        //AES解密
        start = System.currentTimeMillis();
        String decryStr = AesUtils.decrypt(secretKey, encryStr);
        System.out.println(decryStr);
        end = System.currentTimeMillis();
//        Log.e("MainActivity", "AES解密耗时 cost time---->" + (end - start));
//        Log.e("MainActivity", "AES解密后json数据 ---->" + decryStr);
    }
}

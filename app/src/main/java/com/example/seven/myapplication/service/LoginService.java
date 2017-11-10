package com.example.seven.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.LoginRequest;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.util.AndroidPosUtil;
import com.example.seven.myapplication.util.Md5Util;

/**
 * Created by seven617 on 2017/11/1.
 */

public class LoginService  extends Service{

    public void login(String name, String password, CommonCallback commonCallback){

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setUsername(name);
        loginRequest.setPassword(Md5Util.md5(password));
        //联迪POS专用
//        loginRequest.setDeviceNo("86226955");
        loginRequest.setDeviceNo(AndroidPosUtil.getAndroidPosSn());
        Api.post(APIConstants.URL_USER_LOGIN,loginRequest.getMap(),commonCallback);
    }

    public void logout(CommonCallback commonCallback){

        Api.post(APIConstants.URL_USER_LOGOUT,null,commonCallback);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

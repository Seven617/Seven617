package com.example.seven.myapplication.service;

import com.alibaba.fastjson.JSONObject;
import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.LoginRequest;
import com.example.seven.myapplication.model.LoginResult;
import com.example.seven.myapplication.model.NetworkResult;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.util.AndroidPosUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seven617 on 2017/11/1.
 */

public class LoginService {
    public LoginResult getLoginResult(String  data){
        NetworkResult result = JSONObject.parseObject(data,NetworkResult.class);

        LoginResult loginResult = new LoginResult();
        if(APIConstants.CODE_RESULT_SUCCESS.equals(result.getStatus())){
            loginResult.setSuccess(true);
            loginResult.setResult(result.getData());
        }

        return loginResult;
    }


    public void login(String name, String password, CommonCallback commonCallback){

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setName(name);
        loginRequest.setPassword(password);
        //联迪POS专用
//        loginRequest.setPosSn(AndroidPosUtil.getAndroidPosSn());

        Api.post(APIConstants.URL_USER_LOGIN,loginRequest.getMap(),commonCallback);
    }
}

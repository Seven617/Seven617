package com.example.seven.myapplication.service;

import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.LoginRequest;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.util.Md5Util;

/**
 * Created by seven617 on 2017/11/1.
 */

public class LoginService {
//    public LoginResult getLoginResult(String  data){
//        NetworkResult result = JSONObject.parseObject(data,NetworkResult.class);
//        LoginResult loginResult = new LoginResult();
//        if(APIConstants.CODE_RESULT_SUCCESS.equals(result.getStatus())){
//            loginResult.setSuccess(true);
////            loginResult.setResult(JSONObject.parseObject(result.getData(), LoginData.class));
//        }else {
//            loginResult.setMsg(result.getMsg());
//        }
//
//        return loginResult;
//    }


    public void login(String name, String password, CommonCallback commonCallback){

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setUsername(name);
        loginRequest.setPassword(Md5Util.md5(password));
        //联迪POS专用
//        loginRequest.setPosSn(AndroidPosUtil.getAndroidPosSn());
        loginRequest.setDeviceNo("123");
        Api.post(APIConstants.URL_USER_LOGIN,loginRequest.getMap(),commonCallback);
    }
}

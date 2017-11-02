package com.example.seven.myapplication.service;

import com.alibaba.fastjson.JSONObject;
import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.LoginResult;
import com.example.seven.myapplication.model.Result;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;
import com.example.seven.myapplication.util.AndroidPosUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seven617 on 2017/11/1.
 */

public class LoginService {
    public LoginResult toLoginResult(String  data){
        Result result = JSONObject.parseObject(data,Result.class);

        LoginResult loginResult = new LoginResult();
        if(APIConstants.RESULT_SUCCESS_CODE.equals(result.getStatus())){
            loginResult.setSuccess(true);
            loginResult.setResult(result.getData());
        }

        return loginResult;
    }


    public void login(String name, String password, CommonCallback commonCallback){


        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("password", password);
//        map.put("posSn",AndroidPosUtil.getAndroidPosSn());

//        map.put()
        Api.post("post",map,commonCallback);
    }
}

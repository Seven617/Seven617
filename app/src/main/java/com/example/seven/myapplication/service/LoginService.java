package com.example.seven.myapplication.service;

import com.example.seven.myapplication.model.LoginResult;

/**
 * Created by seven617 on 2017/11/1.
 */

public class LoginService {
    public LoginResult toLoginResult(String data){
        LoginResult loginResult = new LoginResult();
        loginResult.setCode(0);
        loginResult.setResult("2017-11-01");
        return loginResult;
    }
}

package com.example.seven.myapplication.model;

import com.example.seven.myapplication.constants.APIConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daichen on 2017/11/2.
 */

public class LoginRequest {
    private String username;
    private String password;
    private String deviceNo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Map getMap(){
        Map<String ,String > map = new HashMap<>();
        map.put(APIConstants.STRING_USERNAME,this.username);
        map.put(APIConstants.STRING_PASSWORD,this.password);
        map.put(APIConstants.STRING_DEVICE_NO,this.deviceNo);
        return map;

    }
}

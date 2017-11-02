package com.example.seven.myapplication.model;

import com.example.seven.myapplication.constants.APIConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daichen on 2017/11/2.
 */

public class LoginRequest {
    private String name;
    private String password;
    private String posSn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosSn() {
        return posSn;
    }

    public void setPosSn(String posSn) {
        this.posSn = posSn;
    }


    public Map getMap(){
        Map<String ,String > map = new HashMap<>();
        map.put(APIConstants.STRING_NAME,this.name);
        map.put(APIConstants.STRING_PASSWORD,this.password);
        map.put(APIConstants.STRING_POS_SN,this.posSn);
        return map;

    }
}

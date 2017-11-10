package com.example.seven.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.seven.myapplication.constants.APIConstants;
import com.example.seven.myapplication.model.RefundRequest;
import com.example.seven.myapplication.network.Api;
import com.example.seven.myapplication.network.CommonCallback;

/**
 * Created by daichen on 2017/11/9.
 */

public class TodayOrderServer extends Service {
    public void refund(CommonCallback commonCallback){
        Api.get(APIConstants.URL_TODAY_ORDER,null, commonCallback);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

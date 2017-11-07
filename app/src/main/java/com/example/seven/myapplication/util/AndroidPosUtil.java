package com.example.seven.myapplication.util;

import com.landicorp.android.eptapi.utils.SystemInfomation;

/**
 * Created by daichen on 2017/11/2.
 */

public class AndroidPosUtil {

    public static String getAndroidPosSn(){
        SystemInfomation.DeviceInfo deviceInfo= SystemInfomation.getDeviceInfo();

        return deviceInfo.getSerialNo();
    }


}

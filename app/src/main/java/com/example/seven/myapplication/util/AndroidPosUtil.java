package com.example.seven.myapplication.util;

import com.landicorp.android.eptapi.utils.SystemInfomation;

/**
 * Created by daichen on 2017/11/2.
 */

public class AndroidPosUtil {

    public static String getAndroidPosSn(){
        SystemInfomation.DeviceInfo deviceInfo= SystemInfomation.getDeviceInfo();
        System.out.println("硬件序列号" + deviceInfo.getHardWareSn());
        System.out.println("序列号" + deviceInfo.getSerialNo());
        System.out.println("PSAM号" + deviceInfo.getSpecialPSamId());
        System.out.println("预设SN" + deviceInfo.getSpecialSerialNo());

        return "硬件序列号" +deviceInfo.getHardWareSn()+"序列号" + deviceInfo.getSerialNo() + "PSAM号" + deviceInfo.getSpecialPSamId() +"预设SN" + deviceInfo.getSpecialSerialNo();
    }


}

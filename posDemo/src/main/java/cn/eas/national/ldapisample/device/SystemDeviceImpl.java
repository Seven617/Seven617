package cn.eas.national.ldapisample.device;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.tms.TMS;
import com.landicorp.android.eptapi.tms.TermInfo;
import com.landicorp.android.eptapi.utils.SystemInfomation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 支持具备蜂鸣器设备的终端，如A8/P990/W280PV3，C10/P960/W280P/W280PV2不支持该模块。
 */

public abstract class SystemDeviceImpl extends BaseDevice {

    private Context context;
    private TermInfo termInfo;
    private SystemInfomation.DeviceInfo deviceInfo;

    public SystemDeviceImpl(Context context) {
        super(context);
        this.context = context;
        TMS tms = new TMS();
        termInfo = new TermInfo();
        try {
            tms.getTermInfo(termInfo);
        } catch (RequestException e) {
            e.printStackTrace();
        }
        deviceInfo = SystemInfomation.getDeviceInfo();
    }

    public boolean updateDatetime(String datetime) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return SystemInfomation.setCurrentDateTime(date);
    }

    public void reboot() {
        try {
            new TMS().reboot();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    public String getSerialNo() {
        return deviceInfo.getSerialNo();
    }

    public String getSpecialSerialNo() {
        return deviceInfo.getSpecialSerialNo();
    }

    public String getPsamId() {
        return deviceInfo.getSpecialPSamId();
    }

    public String getDeviceModel() {
        return Build.MODEL;
    }

    public String getManufacture() {
        return termInfo.getEquipmentManufacturers();
    }

    public String getIMSI() {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getSubscriberId();
    }

    public String getIMEI() {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getDeviceId();
    }

    public String getICCID() {
        String iccid = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
        return iccid;
    }

    public String getAndroidOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
}

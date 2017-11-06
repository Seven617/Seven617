package cn.eas.national.ldapisample.presenter.impl;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.device.SystemDeviceImpl;
import cn.eas.national.ldapisample.presenter.ISystemDevicePresenter;

/**
 * Created by Czl on 2017/7/23.
 */

public class SystemDevicePresenterImpl implements ISystemDevicePresenter {
    private BaseActivity view;
    private SystemDeviceImpl systemDevice;

    public SystemDevicePresenterImpl(BaseActivity activity) {
        this.view = activity;
        this.systemDevice = new SystemDeviceImpl(activity) {
            @Override
            protected void onDeviceServiceCrash() {
                SystemDevicePresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                SystemDevicePresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void updateTime() {
        boolean result = systemDevice.updateDatetime("20170101121212");
        if (result) {
            view.displayInfo("update datetime success");
        } else {
            view.displayInfo("update datetime fail");
        }
    }

    @Override
    public void reboot() {
        systemDevice.reboot();
    }

    @Override
    public void getDeviceInfo() {
        String serialNo = systemDevice.getSerialNo();
        view.displayInfo("serial no = " + serialNo);
        String specialSerialNo = systemDevice.getSpecialSerialNo();
        view.displayInfo("special serial no = " + specialSerialNo);
        String psamId = systemDevice.getPsamId();
        view.displayInfo("psam id = " + psamId);
        String model = systemDevice.getDeviceModel();
        view.displayInfo("device model = " + model);
        String manufacture = systemDevice.getManufacture();
        view.displayInfo("manufacture = " + manufacture);
        String imsi = systemDevice.getIMSI();
        view.displayInfo("IMSI = " + imsi);
        String imei = systemDevice.getIMEI();
        view.displayInfo("IMEI = " + imei);
        String iccid = systemDevice.getICCID();
        view.displayInfo("iccid = " + iccid);
        String androidOSVersion = systemDevice.getAndroidOSVersion();
        view.displayInfo("android os version = " + androidOSVersion);
    }
}

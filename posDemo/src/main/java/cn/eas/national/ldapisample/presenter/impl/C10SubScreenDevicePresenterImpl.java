package cn.eas.national.ldapisample.presenter.impl;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.device.C10SubScreenDeivceImpl;
import cn.eas.national.ldapisample.presenter.IC10SubScreenDevicePresenter;

/**
 * Created by Czl on 2017/7/23.
 */

public class C10SubScreenDevicePresenterImpl implements IC10SubScreenDevicePresenter {
    private BaseActivity view;
    private C10SubScreenDeivceImpl subscreenDeivce;

    public C10SubScreenDevicePresenterImpl(BaseActivity activity) {
        this.view = activity;
        this.subscreenDeivce = new C10SubScreenDeivceImpl(activity) {
            @Override
            protected void onDeviceServiceCrash() {
                C10SubScreenDevicePresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                C10SubScreenDevicePresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void connect() {
        subscreenDeivce.connect();
    }

    @Override
    public void startAppOnSubScreen() {
        subscreenDeivce.startAppOnSubScreen();
    }

    @Override
    public void startActivityOnSubScreen() {
        subscreenDeivce.startActivityOnSubScreen();
    }

    @Override
    public void setSubScreenApp() {
        subscreenDeivce.setSubScreenApp();
    }

    @Override
    public void removeSubScreenApp() {
        subscreenDeivce.removeSubScreenApp();
    }

    @Override
    public void getSubScreenInfo() {
        subscreenDeivce.getSubScreenInfo();
    }

    @Override
    public void sendDataToSubScreen() {
        int ret = subscreenDeivce.sendData();
        if (ret != 0) {
            view.displayInfo("send data fail[ret = " + ret + "]");
        } else {
            view.displayInfo("send data success");
        }
    }

    @Override
    public void disconnect() {
        subscreenDeivce.disconnect();
    }
}

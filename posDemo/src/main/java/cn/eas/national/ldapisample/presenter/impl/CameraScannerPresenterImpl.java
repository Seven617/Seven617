package cn.eas.national.ldapisample.presenter.impl;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.device.CameraScannerImpl;
import cn.eas.national.ldapisample.presenter.ICameraScannerPresenter;

/**
 * Created by Czl on 2017/7/23.
 */

public class CameraScannerPresenterImpl implements ICameraScannerPresenter {
    private BaseActivity view;
    private CameraScannerImpl scanner;

    public CameraScannerPresenterImpl(BaseActivity activity) {
        this.view = activity;
        this.scanner = new CameraScannerImpl(activity) {
            @Override
            protected void onDeviceServiceCrash() {
                CameraScannerPresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                CameraScannerPresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void startScan(int cameraId) {
        scanner.startScan(view, cameraId);
    }
}

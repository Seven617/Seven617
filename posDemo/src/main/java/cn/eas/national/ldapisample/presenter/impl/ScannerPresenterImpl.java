package cn.eas.national.ldapisample.presenter.impl;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.data.ScannerError;
import cn.eas.national.ldapisample.device.ScannerImpl;
import cn.eas.national.ldapisample.presenter.IScannerPresenter;

/**
 * Created by Czl on 2017/7/23.
 */

public class ScannerPresenterImpl implements IScannerPresenter {
    private BaseActivity view;
    private ScannerImpl scanner;

    public ScannerPresenterImpl(BaseActivity activity) {
        this.view = activity;
        this.scanner = new ScannerImpl(activity) {
            @Override
            protected void onDeviceServiceCrash() {
                ScannerPresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                ScannerPresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void startBrScan() {
        scanner.startBrScan();
    }

    @Override
    public void stopBrScan() {
        scanner.stopBrScan();
    }

    @Override
    public void startScan() {
        int ret = scanner.open();
        if (ret != ScannerError.SUCCESS) {
            view.displayInfo("scanner open fail[" + ScannerImpl.getDescription(ret) + "]");
            return;
        }
        scanner.startScan();
    }
}

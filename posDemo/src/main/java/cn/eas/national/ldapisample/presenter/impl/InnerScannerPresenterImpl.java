package cn.eas.national.ldapisample.presenter.impl;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.device.InnerScannerImpl;
import cn.eas.national.ldapisample.presenter.IInnerScannerPresenter;

/**
 * Created by Czl on 2017/7/23.
 */

public class InnerScannerPresenterImpl implements IInnerScannerPresenter{
    private BaseActivity view;
    private InnerScannerImpl scanner;

    public InnerScannerPresenterImpl(BaseActivity activity) {
        this.view = activity;
        this.scanner = new InnerScannerImpl(activity) {
            @Override
            protected void onDeviceServiceCrash() {
                InnerScannerPresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                InnerScannerPresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void startScan(int timeout) {
        scanner.startScan(timeout);
    }

    @Override
    public void stopScan() {
        scanner.stopScan();
    }
}

package cn.eas.national.ldapisample.presenter.impl;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.device.CashBoxImpl;
import cn.eas.national.ldapisample.presenter.ICashBoxPresenter;

/**
 * Created by Czl on 2017/7/23.
 */

public class CashBoxPresenterImpl implements ICashBoxPresenter {
    private BaseActivity view;
    private CashBoxImpl cashBox;

    public CashBoxPresenterImpl(BaseActivity activity) {
        this.view = activity;
        this.cashBox = new CashBoxImpl(activity) {
            @Override
            protected void onDeviceServiceCrash() {
                CashBoxPresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                CashBoxPresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void open() {
        cashBox.open();
    }
}

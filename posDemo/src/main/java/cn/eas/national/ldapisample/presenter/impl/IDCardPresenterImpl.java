package cn.eas.national.ldapisample.presenter.impl;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.device.IDCardReaderImpl;
import cn.eas.national.ldapisample.presenter.IIDCardPresenter;

/**
 * Created by Czl on 2017/7/23.
 */

public class IDCardPresenterImpl implements IIDCardPresenter{
    private BaseActivity view;
    private IDCardReaderImpl reader;

    public IDCardPresenterImpl(BaseActivity activity) {
        this.view = view;
        this.reader = new IDCardReaderImpl(activity) {
            @Override
            protected void onDeviceServiceCrash() {
                IDCardPresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                IDCardPresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void searchCard() {
        reader.searchCard();
    }

    @Override
    public void stopSearch() {
        reader.stopSearch();
    }
}

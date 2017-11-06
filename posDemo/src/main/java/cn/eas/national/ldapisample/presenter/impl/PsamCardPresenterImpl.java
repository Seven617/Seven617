package cn.eas.national.ldapisample.presenter.impl;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.device.PsamCardReaderImpl;
import cn.eas.national.ldapisample.presenter.IPsamCardPresenter;

/**
 * Created by Czl on 2017/7/23.
 */

public class PsamCardPresenterImpl implements IPsamCardPresenter {
    private BaseActivity view;
    private PsamCardReaderImpl psamCardReader;

    public PsamCardPresenterImpl(BaseActivity activity) {
        this.view = activity;
        this.psamCardReader = new PsamCardReaderImpl(activity, 1) {
            @Override
            protected void onDeviceServiceCrash() {
                PsamCardPresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                PsamCardPresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void cardPower(int slot) {
        view.displayInfo("card power");
        this.psamCardReader = new PsamCardReaderImpl(view, slot) {
            @Override
            protected void onDeviceServiceCrash() {
                PsamCardPresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                PsamCardPresenterImpl.this.view.displayInfo(info);
            }
        };
        psamCardReader.cardPower();
    }

    @Override
    public void cardHalt() {
        psamCardReader.cardHalt();
        view.displayInfo("card halt");
    }

    @Override
    public void exist() {
        boolean exist = psamCardReader.exist();
        if (exist) {
            view.displayInfo("card exist");
        } else {
            view.displayInfo("card not exist");
        }
    }

    @Override
    public void exchangeApdu() {
        byte[] apdu = new byte[] { 0x00, (byte) 0xA4, 0x00, 0x00, 0x02, 0x3F, 0x00 };
        psamCardReader.exchangeApdu(apdu);
    }
}

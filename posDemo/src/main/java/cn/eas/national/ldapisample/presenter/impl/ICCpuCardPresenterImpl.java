package cn.eas.national.ldapisample.presenter.impl;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.device.ICCpuCardReaderImpl;
import cn.eas.national.ldapisample.presenter.IICCpuCardPresenter;
import cn.eas.national.ldapisample.util.ByteUtil;

/**
 * Created by Czl on 2017/7/23.
 */

public class ICCpuCardPresenterImpl implements IICCpuCardPresenter {
    private BaseActivity view;
    private ICCpuCardReaderImpl icCardReader;

    public ICCpuCardPresenterImpl(BaseActivity activity) {
        this.view = activity;
        this.view = view;
        this.icCardReader = new ICCpuCardReaderImpl(activity) {
            @Override
            protected void onDeviceServiceCrash() {
                ICCpuCardPresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                ICCpuCardPresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void cardPower() {
        icCardReader.cardPower();
    }

    @Override
    public void cardHalt() {
        icCardReader.cardHalt();
        view.displayInfo("card halt");
    }

    @Override
    public void exist() {
        boolean exist = icCardReader.exist();
        if (exist) {
            view.displayInfo("card exist");
        } else {
            view.displayInfo("card not exist");
        }
    }

    @Override
    public void exchangeApdu() {
        byte[] apdu = ByteUtil.hexString2Bytes("00A4040008A000000333010101");
        icCardReader.exchangeApdu(apdu);
    }
}

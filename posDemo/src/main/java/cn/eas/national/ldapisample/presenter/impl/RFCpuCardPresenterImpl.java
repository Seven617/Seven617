package cn.eas.national.ldapisample.presenter.impl;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.device.RFCpuCardReaderImpl;
import cn.eas.national.ldapisample.presenter.IRFCpuCardPresenter;
import cn.eas.national.ldapisample.util.ByteUtil;

/**
 * Created by Czl on 2017/7/23.
 */

public class RFCpuCardPresenterImpl implements IRFCpuCardPresenter {
    private BaseActivity view;
    private RFCpuCardReaderImpl rfCardReader;

    public RFCpuCardPresenterImpl(BaseActivity activity, String deviceName) {
        this.view = activity;
        this.rfCardReader = new RFCpuCardReaderImpl(activity, deviceName) {
            @Override
            protected void onDeviceServiceCrash() {
                RFCpuCardPresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                RFCpuCardPresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void cardPower() {
        rfCardReader.cardPower();
    }

    @Override
    public void cardHalt() {
        rfCardReader.cardHalt();
    }

    @Override
    public void exchangeApdu() {
        byte[] apdu = ByteUtil.hexString2Bytes("00A4040008A000000333010101");
        rfCardReader.exchangeApdu(apdu);
    }

    @Override
    public void exist() {
        boolean exist = rfCardReader.exist();
        if (exist) {
            view.displayInfo("card exist");
        } else {
            view.displayInfo("card not exist");
        }
    }
}

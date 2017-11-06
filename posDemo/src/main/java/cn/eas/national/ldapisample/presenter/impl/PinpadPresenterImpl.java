package cn.eas.national.ldapisample.presenter.impl;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.data.Constants;
import cn.eas.national.ldapisample.device.PinpadImpl;
import cn.eas.national.ldapisample.presenter.IPinpadPresenter;
import cn.eas.national.ldapisample.util.ByteUtil;

/**
 * Created by Czl on 2017/7/27.
 */

public class PinpadPresenterImpl implements IPinpadPresenter {
    private BaseActivity view;
    private PinpadImpl pinpad;

    public PinpadPresenterImpl(BaseActivity activity, String deviceName) {
        this.view = activity;
        this.pinpad = new PinpadImpl(activity, deviceName) {
            @Override
            protected void onDeviceServiceCrash() {
                PinpadPresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                PinpadPresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void loadKey() {
        // 下装明文主密钥
        boolean result = pinpad.loadPlainTextKey(Constants.Pinpad.KEYTYPE_MAIN_KEY,
                Constants.Pinpad.MAIN_KEY_INDEX, ByteUtil.hexString2Bytes("31313131313131313131313131313131"));
        if (result) {
            view.displayInfo("load main key[31313131313131313131313131313131] success");
        } else {
            view.displayInfo("load main key[31313131313131313131313131313131] fail");
        }
        // 下装工作密钥
        result = pinpad.loadWorkKey(Constants.Pinpad.KEYTYPE_MAC_KEY,
                Constants.Pinpad.MAIN_KEY_INDEX, Constants.Pinpad.MAC_KEY_INDEX,
                ByteUtil.hexString2Bytes("4BF6E91B1E3A9D814BF6E91B1E3A9D81"),
                ByteUtil.hexString2Bytes("ADC67D8473BF2F06"));
        if (result) {
            view.displayInfo("load mac key[4BF6E91B1E3A9D814BF6E91B1E3A9D81] success");
        } else {
            view.displayInfo("load mac key[4BF6E91B1E3A9D814BF6E91B1E3A9D81] fail");
        }
        result = pinpad.loadWorkKey(Constants.Pinpad.KEYTYPE_PIN_KEY,
                Constants.Pinpad.MAIN_KEY_INDEX, Constants.Pinpad.PIN_KEY_INDEX,
                ByteUtil.hexString2Bytes("D0FB24EA73F599C1D0FB24EA73F599C1"),
                ByteUtil.hexString2Bytes("D2DB51F1D2013A63"));
        if (result) {
            view.displayInfo("load pin key[4BF6E91B1E3A9D814BF6E91B1E3A9D81] success");
        } else {
            view.displayInfo("load pin key[4BF6E91B1E3A9D814BF6E91B1E3A9D81] fail");
        }
        result = pinpad.loadWorkKey(Constants.Pinpad.KEYTYPE_TD_KEY,
                Constants.Pinpad.MAIN_KEY_INDEX, Constants.Pinpad.TD_KEY_INDEX,
                ByteUtil.hexString2Bytes("16B2CCB944DA2CE916B2CCB944DA2CE9"),
                ByteUtil.hexString2Bytes("3AA3EA2D72ECF9FA"));
        if (result) {
            view.displayInfo("load td key[4BF6E91B1E3A9D814BF6E91B1E3A9D81] success");
        } else {
            view.displayInfo("load td key[4BF6E91B1E3A9D814BF6E91B1E3A9D81] fail");
        }
        result = pinpad.loadWorkKey(Constants.Pinpad.KEYTYPE_ENC_DEC_KEY,
                Constants.Pinpad.MAIN_KEY_INDEX, Constants.Pinpad.ENC_DEC_KEY_INDEX,
                ByteUtil.hexString2Bytes("ADBF8135A642B58AADBF8135A642B58A"),
                ByteUtil.hexString2Bytes("74D669C708972B1A"));
        if (result) {
            view.displayInfo("load enc_dec key[ADBF8135A642B58AADBF8135A642B58A] success");
        } else {
            view.displayInfo("load enc_dec key[ADBF8135A642B58AADBF8135A642B58A] fail");
        }
    }

    @Override
    public void startOnlinePinEntry(String cardNo) {
        pinpad.startOnlinePinEntry(cardNo);
    }

    @Override
    public void calcMac(byte[] data) {
        byte[] result = pinpad.calcMac(Constants.Pinpad.MAC_KEY_INDEX, data);
        view.displayInfo("calcMac result = " + ByteUtil.bytes2HexString(result));
    }

    @Override
    public void encryptMagTrack(byte[] data) {
        byte[] result = pinpad.encryptMagTrack(Constants.Pinpad.TD_KEY_INDEX, data);
        view.displayInfo("encryptMagTrack result = " + ByteUtil.bytes2HexString(result));
    }

    @Override
    public void encryptData(byte[] data) {
        byte[] result = pinpad.encryptData(Constants.Pinpad.ENC_DEC_KEY_INDEX, data);
        view.displayInfo("encrpty data[" + ByteUtil.bytes2HexString(data) + "] result = " + ByteUtil.bytes2HexString(result));
    }

    @Override
    public void decryptData(byte[] data) {
        byte[] result = pinpad.decryptData(Constants.Pinpad.ENC_DEC_KEY_INDEX, data);
        view.displayInfo("decrpty data[" + ByteUtil.bytes2HexString(data) + "] result = " + ByteUtil.bytes2HexString(result));
    }
}

package cn.eas.national.ldapisample.presenter.impl;

import com.landicorp.android.eptapi.utils.BytesBuffer;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.data.RFCardError;
import cn.eas.national.ldapisample.device.MifareCardReaderImpl;
import cn.eas.national.ldapisample.presenter.IMifareCardPresenter;
import cn.eas.national.ldapisample.util.ByteUtil;

/**
 * Created by Czl on 2017/7/23.
 */

public class MifareCardPresenterImpl implements IMifareCardPresenter {
    private BaseActivity view;
    private MifareCardReaderImpl mifareCardReader;

    public MifareCardPresenterImpl(BaseActivity activity, String deviceName) {
        this.view = activity;
        this.mifareCardReader = new MifareCardReaderImpl(activity, deviceName) {
            @Override
            protected void onDeviceServiceCrash() {
                MifareCardPresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                MifareCardPresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void cardPower() {
        mifareCardReader.cardPower();
    }

    @Override
    public void cardHalt() {
        mifareCardReader.cardHalt();
    }

    @Override
    public void authBlock(int blockNo, int keyType, byte[] key) {
        view.displayInfo("auth card[blockNo = " + blockNo + ", keyType = " + keyType
                + ", key = " + ByteUtil.bytes2HexString(key) + "]");
        mifareCardReader.auth(blockNo, keyType, key);
    }

    @Override
    public void read(int blockNo, BytesBuffer buffer) {
        view.displayInfo("read card[blockNo = " + blockNo + "]");
        int ret = mifareCardReader.read(blockNo, buffer);
        if (ret == RFCardError.SUCCESS) {
            byte[] data = buffer.getData();
            view.displayInfo("read result[" + ByteUtil.bytes2HexString(data) + "]");
        } else {
            view.displayInfo("read fail, error = " + ret);
        }
    }

    @Override
    public void write(int blockNo, byte[] data) {
        view.displayInfo("read card[blockNo = " + blockNo + ", data = " + ByteUtil.bytes2HexString(data) + "]");
        int ret = mifareCardReader.write(blockNo, data);
        if (ret == RFCardError.SUCCESS) {
            view.displayInfo("write success");
        } else {
            view.displayInfo("write fail, error = " + ret);
        }
    }

    @Override
    public void addValue(int blockNo, int value) {
        view.displayInfo("card addValue [blockNo = " + blockNo + ", value = " + value + "]");
        int ret = mifareCardReader.increase(blockNo, value);
        if (ret == RFCardError.SUCCESS) {
            ret = mifareCardReader.transfer(blockNo);
            if (ret == RFCardError.SUCCESS) {
                view.displayInfo("addValue success");
            } else {
                view.displayInfo("transfer fail, error = " + ret);
            }
        } else {
            view.displayInfo("increase fail, error = " + ret);
        }
    }

    @Override
    public void reduceValue(int blockNo, int value) {
        view.displayInfo("card decrease [blockNo = " + blockNo + ", value = " + value + "]");
        int ret = mifareCardReader.decrease(blockNo, value);
        if (ret == RFCardError.SUCCESS) {
            ret = mifareCardReader.transfer(blockNo);
            if (ret == RFCardError.SUCCESS) {
                view.displayInfo("reduceValue success");
            } else {
                view.displayInfo("transfer fail, error = " + ret);
            }
        } else {
            view.displayInfo("decrease fail, error = " + ret);
        }
    }

    @Override
    public void exist() {
        boolean exist = mifareCardReader.exist();
        if (exist) {
            view.displayInfo("card exist");
        } else {
            view.displayInfo("card not exist");
        }
    }
}

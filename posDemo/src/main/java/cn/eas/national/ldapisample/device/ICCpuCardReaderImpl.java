package cn.eas.national.ldapisample.device;

import android.content.Context;

import com.landicorp.android.eptapi.card.InsertCpuCardDriver;
import com.landicorp.android.eptapi.device.InsertCardReader;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesBuffer;
import com.landicorp.android.eptapi.utils.IntegerBuffer;

import cn.eas.national.ldapisample.data.ICCardError;
import cn.eas.national.ldapisample.util.ByteUtil;

/**
 * Created by Czl on 2017/7/26.
 */

public abstract class ICCpuCardReaderImpl extends BaseDevice {

    private InsertCardReader reader = InsertCardReader.getInstance();
    private InsertCpuCardDriver driver = new InsertCpuCardDriver();

    public ICCpuCardReaderImpl(Context context) {
        super(context);
    }

    public boolean exist() {
        try {
            return reader.isCardExists();
        } catch (RequestException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cardPower() {
        searchCard();
    }

    public void cardHalt() {
        powerDown();
        halt();
    }

    private void searchCard() {
        try {
            reader.searchCard(new InsertCardReader.OnSearchListener() {
                @Override
                public void onCrash() {
                    onDeviceServiceCrash();
                }

                @Override
                public void onFail(int error) {
                    displayInfo("search error, error = " + error);
                }

                @Override
                public void onCardInsert() {
                    displayInfo("card insert");
                    runOnUI(new Runnable() {
                        @Override
                        public void run() {
                            powerUp(InsertCpuCardDriver.MODE_EMV, InsertCpuCardDriver.VOL_5);
                        }
                    });
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    private void stopSearch() {
        try {
            reader.stopSearch();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    private int powerUp(int mode, int voltage) {
        int ret =ICCardError.FAIL;
        try {
            driver.setPowerupMode(mode);
            driver.setPowerupVoltage(voltage);
            BytesBuffer atr = new BytesBuffer();
            IntegerBuffer protocol = new IntegerBuffer();
            ret = driver.powerup(atr, protocol);
            if (ret != ICCardError.SUCCESS) {
                displayInfo("power up fail, error = " + ret);
            } else {
                displayInfo("power up success, atr = " + ByteUtil.bytes2HexString(atr.getData())
                        + ", protocol = " + protocol.getData());
            }
        } catch (RequestException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private void powerDown() {
        try {
            driver.powerDown();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    public void exchangeApdu(byte[] apdu) {
        BytesBuffer response = new BytesBuffer();
        try {
            int ret = driver.exchangeApdu(apdu, response);
            if (ret != ICCardError.SUCCESS) {
                displayInfo("exchange apdu fail, error = " + ret);
            } else {
                displayInfo("exchange apdu success, response = " + ByteUtil.bytes2HexString(response.getData()));
            }
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    private void halt() {
        try {
            driver.halt();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    private void runOnUI(Runnable runnable) {
        uiHandler.post(runnable);
    }
}

package cn.eas.national.ldapisample.device;

import android.content.Context;

import com.landicorp.android.eptapi.card.RFCpuCardDriver;
import com.landicorp.android.eptapi.card.RFDriver;
import com.landicorp.android.eptapi.device.RFCardReader;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesBuffer;

import cn.eas.national.ldapisample.data.Constants;
import cn.eas.national.ldapisample.data.RFCardError;
import cn.eas.national.ldapisample.util.ByteUtil;

/**
 * 针对终端上已经非接模块可直接使用，否则需外接设备进行调用。
 * 内置非接读卡器，RFCardReader初始化调用getInstance()接口，寻卡回调需使用RFCardReader.OnSearchListener，
 * 外置非接读卡器，RFCardReader初始化调用getOtherInstance("EXTRFCARD")接口，寻卡回调需使用RFCardReader.OnSearchListenerEx。
 */
public abstract class RFCpuCardReaderImpl extends BaseDevice {

    private RFCardReader reader;
    private RFCpuCardDriver driver;
    private String deviceName;
    private RFCardReader.OnSearchListener searchListener = new RFCardReader.OnSearchListener() {
        @Override
        public void onCrash() {
            onDeviceServiceCrash();
        }

        @Override
        public void onFail(int error) {
            displayInfo("search error, error = " + error);
        }

        @Override
        public void onCardPass(int cardType) {
            displayInfo("card pass, card type = " + cardType);
            runOnUI(new Runnable() {
                @Override
                public void run() {
                    activeCard();
                }
            });
        }
    };
    private RFCardReader.OnSearchListenerEx searchListenerEx = new RFCardReader.OnSearchListenerEx() {
        @Override
        public void onCrash() {
            onDeviceServiceCrash();
        }

        @Override
        public void onFail(int error) {
            displayInfo("search error, error = " + error);
        }

        @Override
        public void onCardPass(int cardType) {
            displayInfo("card pass, card type = " + cardType);
            runOnUI(new Runnable() {
                @Override
                public void run() {
                    activeCard();
                }
            });
        }
    };

    public RFCpuCardReaderImpl(Context context, String deviceName) {
        super(context);
        this.deviceName = deviceName;
        if (deviceName.equals(Constants.RFCard.DEVICE_INNER)) {
            reader = RFCardReader.getInstance();
        } else {
            reader = RFCardReader.getOtherInstance("EXTRFCARD");
        }
    }

    public void cardPower() {
        searchCard();
    }

    public void cardHalt() {
        halt();
    }

    public boolean exist() {
        try {
            return reader.exists();
        } catch (RequestException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void searchCard() {
        try {
            if (deviceName.equals(Constants.RFCard.DEVICE_INNER)) {
                reader.searchCard(searchListener);
            } else {
                reader.searchCard(searchListenerEx);
            }
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

    private void activeCard() {
        try {
            reader.activate(Constants.RFCard.DRIVER_NAME_PRO, new RFCardReader.OnActiveListener() {
                @Override
                public void onCardActivate(RFDriver rfDriver) {
                    driver = (RFCpuCardDriver) rfDriver;
                    displayInfo("activate success");
                }

                @Override
                public void onActivateError(int error) {
                    displayInfo("activate error, error = " + error);
                }

                @Override
                public void onUnsupport(String s) {
                    displayInfo("unsupport driverName = " + s);
                }

                @Override
                public void onCrash() {
                    onDeviceServiceCrash();
                }
            });
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    public void exchangeApdu(byte[] apdu) {
        if (driver == null) {
            displayInfo("please power card first");
            return;
        }
        BytesBuffer response = new BytesBuffer();
        try {
            int ret = driver.exchangeApdu(apdu, response);
            if (ret != RFCardError.SUCCESS) {
                displayInfo("exchange apdu fail, error = " + ret);
            } else {
                displayInfo("exchange apdu success, response = " + ByteUtil.bytes2HexString(response.getData()));
            }
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    private void halt() {
        if (driver == null) {
            displayInfo("please power card first");
            return;
        }
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

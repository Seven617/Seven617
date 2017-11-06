package cn.eas.national.ldapisample.device;

import android.content.Context;

import com.landicorp.android.eptapi.card.MifareDriver;
import com.landicorp.android.eptapi.card.RFDriver;
import com.landicorp.android.eptapi.device.RFCardReader;
import com.landicorp.android.eptapi.device.RFCardReader.OnSearchListener;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesBuffer;

import cn.eas.national.ldapisample.data.Constants;
import cn.eas.national.ldapisample.data.RFCardError;

/**
 * 针对终端上已经非接模块可直接使用，否则需外接设备进行调用。使用外接设备时，
 * RFCardReader实例化时需使用getOtherInstance()，传入设备名EXTRFCARD。
 * 且寻卡监听回调需使用OnSearchListenerEx。
 */

public abstract class MifareCardReaderImpl extends BaseDevice {

    private Context context;
    private RFCardReader reader;
    private MifareDriver driver;
    private String deviceName;
    private String driverName;
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
            setDriverName(cardType);
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
            setDriverName(cardType);
            runOnUI(new Runnable() {
                @Override
                public void run() {
                    activeCard();
                }
            });
        }
    };

    public MifareCardReaderImpl(Context context, String deviceName) {
        super(context);
        this.context = context;
        this.deviceName = deviceName;
        if (deviceName.equals(Constants.RFCard.DEVICE_INNER)) {
            reader = RFCardReader.getInstance();
        } else {
            reader = RFCardReader.getOtherInstance("EXTRFCARD");
        }
    }
    public boolean exist() {
        try {
            return reader.exists();
        } catch (RequestException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cardPower() {
        searchCard();
    }

    public void cardHalt() {
        halt();
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
            if (driverName == null) {
                displayInfo("you need searchCard first");
                return;
            }
            if (!Constants.RFCard.DRIVER_NAME_S50.equals(driverName) && !Constants.RFCard.DRIVER_NAME_S70.equals(driverName)) {
                displayInfo("this card is not s50 or s70, not support");
                return;
            }
            reader.activate(driverName, new RFCardReader.OnActiveListener() {
                @Override
                public void onCardActivate(RFDriver rfDriver) {
                    driver = (MifareDriver) rfDriver;
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

    public int auth(int blockNo, int keyType, byte[] key) {
        int result = RFCardError.FAIL;
        if (driver == null) {
            displayInfo("please power card first");
            return result;
        }
        try {
            result = driver.authBlock(blockNo, keyType, key);
        } catch (RequestException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int read(int blockNo, BytesBuffer buffer) {
        int result = RFCardError.FAIL;
        if (driver == null) {
            displayInfo("please power card first");
            return result;
        }
        BytesBuffer data = new BytesBuffer();
        try {
            result = driver.readBlock(blockNo, data);
            if (result == RFCardError.SUCCESS) {
                if (buffer == null) {
                    result = RFCardError.FAIL;
                } else {
                    buffer.setData(data.getData());
                }
            }
        } catch (RequestException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int write(int blockNo, byte[] data) {
        int result = RFCardError.FAIL;
        if (driver == null) {
            displayInfo("please power card first");
            return result;
        }
        try {
            result = driver.writeBlock(blockNo, data);
        } catch (RequestException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int increase(int blockNo, int value) {
        int result = RFCardError.FAIL;
        if (driver == null) {
            displayInfo("please power card first");
            return result;
        }
        try {
            result = driver.increase(blockNo, value);
        } catch (RequestException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int decrease(int blockNo, int value) {
        int result = RFCardError.FAIL;
        if (driver == null) {
            displayInfo("please power card first");
            return result;
        }
        try {
            result = driver.decrease(blockNo, value);
        } catch (RequestException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int restore(int blockNo) {
        int result = RFCardError.FAIL;
        if (driver == null) {
            displayInfo("please power card first");
            return result;
        }
        try {
            result = driver.restoreRAM(blockNo);
        } catch (RequestException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int transfer(int blockNo) {
        int result = RFCardError.FAIL;
        if (driver == null) {
            displayInfo("please power card first");
            return result;
        }
        try {
            result = driver.transferRAM(blockNo);
        } catch (RequestException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void halt() {
        if (driver == null) {
            return;
        }
        try {
            driver.halt();
            driverName = null;
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    private void setDriverName(int cardType) {
        switch (cardType) {
            case OnSearchListener.S50_CARD:
            case OnSearchListener.S50_PRO_CARD:
                driverName = Constants.RFCard.DRIVER_NAME_S50;
                break;
            case OnSearchListener.S70_CARD:
            case OnSearchListener.S70_PRO_CARD:
                driverName = Constants.RFCard.DRIVER_NAME_S70;
                break;
            case OnSearchListener.CPU_CARD:
                driverName = Constants.RFCard.DRIVER_NAME_CPU;
                break;
            case OnSearchListener.PRO_CARD:
                driverName = Constants.RFCard.DRIVER_NAME_PRO;
                break;
            default:
                driverName = Constants.RFCard.DRIVER_NAME_PRO;
                break;
        }
    }

    private void runOnUI(Runnable runnable) {
        uiHandler.post(runnable);
    }
}

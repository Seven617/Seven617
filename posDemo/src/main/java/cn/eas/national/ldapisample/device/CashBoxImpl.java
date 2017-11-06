package cn.eas.national.ldapisample.device;

import android.content.Context;

import com.landicorp.android.eptapi.device.CashBox;
import com.landicorp.android.eptapi.exception.RequestException;

/**
 * 针对不同型号终端，接入钱箱有所区分。C10有专用钱箱端口，而A8等终端需通过底座或者转换线的方式进行接入。
 */

public abstract class CashBoxImpl extends BaseDevice {

    private CashBox cashBox = new CashBox("");
    private CashBox.OnBoxOpenListener listener = new CashBox.OnBoxOpenListener() {
        @Override
        public void onBoxOpened() {
            displayInfo("cash box opened");
        }

        @Override
        public void onBoxOpenFail(int error) {
            displayInfo("cash box open fail[" + getErrorDescription(error) + "]");
        }

        @Override
        public void onCrash() {
            onDeviceServiceCrash();
        }
    };

    public CashBoxImpl(Context context) {
        super(context);
    }

    public void open() {
        try {
            cashBox.setOnBoxOpenListener(listener);
            cashBox.openBox();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    private String getErrorDescription(int error) {
        switch(error) {
            case  CashBox.ERROR_DEVICE_NOT_EXIST:
                return "device is not exist or disabled";
            case  CashBox.ERROR_IS_ALEADY_OPEN:
                return "device is aleady opened";
            case  CashBox.ERROR_FAIL:
                return "open error";
        }
        return "unknown error ["+error+"]";
    }
}

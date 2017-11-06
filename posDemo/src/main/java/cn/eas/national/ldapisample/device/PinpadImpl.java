package cn.eas.national.ldapisample.device;

import android.content.Context;
import android.content.Intent;

import com.landicorp.android.eptapi.device.Beeper;
import com.landicorp.android.eptapi.pinpad.Pinpad;
import com.landicorp.pinpad.PinEntryCfg;

import java.util.Arrays;

import cn.eas.national.ldapisample.data.Constants;
import cn.eas.national.ldapisample.util.ByteUtil;

/**
 * 针对终端上已经Pinpad模块可直接使用，否则需外接设备进行调用。
 * 使用内置键盘时，Pinpad实例化时需传入设备名IPP，使用外接设备时，Pinpad实例化时需传入设备名COM_EPP。
 */

public abstract class PinpadImpl extends BaseDevice {

    private Context context;
    private com.landicorp.android.eptapi.pinpad.Pinpad pinpad;
    private String deviceName;

    public PinpadImpl(Context context, String deviceName) {
        super(context);
        this.context = context;
        this.deviceName = deviceName;
        if (deviceName.equals(Constants.Pinpad.DEVICE_INNER)) {
            pinpad = new Pinpad(Constants.Pinpad.REGION_ID, Constants.Pinpad.KAP_ID,
                    Constants.Pinpad.DEVICE_INNER);
        } else {
            pinpad = new Pinpad(Constants.Pinpad.REGION_ID, Constants.Pinpad.KAP_ID,
                    Constants.Pinpad.DEVICE_EXTERNAL);
        }
    }

    public boolean loadPlainTextKey(final int keyType, final int keyId, final byte[] key) {
        return new PinpadExecutor() {
            @Override
            protected boolean onExecute() {
                boolean ret = pinpad.format();
                if (!ret) {
                    displayInfo("pinpad format fail");
                    return false;
                }
                return pinpad.loadPlainTextKey(keyId, keyType, key);
            }
        }.execute();
    }

    public boolean loadWorkKey(final int keyType, final int mainKeyId, final int keyId, final byte[] key, final byte[] checkValue) {
        return new PinpadExecutor() {
            @Override
            protected boolean onExecute() {
                boolean ret = pinpad.loadWorkKey(mainKeyId, keyType, keyId, key);
                if (!ret) {
                    displayInfo("loadWorkKey fail");
                    return false;
                }
                if (checkValue == null || checkValue.length == 0) {
                    return true;
                }
                byte[] kcv = pinpad.calcKCV(keyId);
                ret = checkEquals(checkValue, kcv);
                if (!ret) {
                    displayInfo("kcv check fail");
                }
                return ret;
            }
        }.execute();
    }

    public void startOnlinePinEntry(String cardNo) {
        if (!pinpad.open()) {
            displayInfo("pinpad open fail");
            return;
        }
        setPinpadSkin();
        final byte[] lenLimit = new byte[]{ 0, 6 };
        final int timeout = 60;
        pinpad.setPinLengthLimit(lenLimit);
        pinpad.setTimeout(timeout);
        pinpad.setOnPinInputListener(new com.landicorp.android.eptapi.pinpad.Pinpad.OnPinInputListener() {
            @Override
            public void onConfirm(final byte[] pin, final boolean isNonePin) {
                runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        displayInfo(isNonePin ? "input null key" : "pinblock = " + ByteUtil.bytes2HexString(pin));
                        if (deviceName.equals(Constants.Pinpad.DEVICE_EXTERNAL)) {
                            pinpad.reset();
                        }
                    }
                });
            }

            @Override
            public void onInput(final int len, final int key) {
                Beeper.startBeep(100);
                runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        displayInfo("input key[len = " + len + ", key = " + key + "]");
                        if (deviceName.equals(Constants.Pinpad.DEVICE_EXTERNAL)) {
                            pinpad.reset();
                        }
                    }
                });
            }

            @Override
            public void onCancel() {
                runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        displayInfo("cancel input");
                        if (deviceName.equals(Constants.Pinpad.DEVICE_EXTERNAL)) {
                            pinpad.reset();
                        }
                    }
                });
            }

            @Override
            public void onError(final int errorCode) {
                runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        displayInfo("error = " + errorCode);
                        if (deviceName.equals(Constants.Pinpad.DEVICE_EXTERNAL)) {
                            pinpad.reset();
                        }
                    }
                });
            }
        });
        pinpad.startPinEntryNew(Constants.Pinpad.PIN_KEY_INDEX, cardNo, PinEntryCfg.BLOCK_FORMAT_0);
    }

    public boolean cancelInputPin() {
        return new PinpadExecutor() {
            @Override
            protected boolean onExecute() {
                boolean success = pinpad.cancelInput();
                success &= pinpad.close();
                return success;
            }
        }.execute();
    }

    public byte[] calcMac(final int keyId, final byte[] data) {
        return new PinpadInvoker() {
            @Override
            protected byte[] onInvoke() {
                int algorithm = pinpad.isSM4Enabled() ? com.landicorp.android.eptapi.pinpad.Pinpad.MM_ALG_SM4 : com.landicorp.android.eptapi.pinpad.Pinpad.MM_ALG_ISO9797;
                return pinpad.calcMAC(algorithm << 8 | com.landicorp.android.eptapi.pinpad.Pinpad.MAC_PADDING_MODE_1, keyId, data);
            }
        }.invoke();
    }

    public byte[] encryptMagTrack(final int keyId, final byte[] trkData) {
        return new PinpadInvoker() {
            @Override
            protected byte[] onInvoke() {
                return pinpad.encryptMagTrack(com.landicorp.android.eptapi.pinpad.Pinpad.MM_ALG_AES, keyId, trkData);
            }
        }.invoke();
    }

    public byte[] encryptData(final int keyId, final byte[] data) {
        return new PinpadInvoker() {
            @Override
            protected byte[] onInvoke() {
                return pinpad.encryptData(keyId, data);
            }
        }.invoke();
    }

    public byte[] decryptData(final int keyId, final byte[] data) {
        return new PinpadInvoker() {
            @Override
            protected byte[] onInvoke() {
                return pinpad.decryptData(keyId, data);
            }
        }.invoke();
    }

    private boolean checkEquals(byte[] checkValue, byte[] kcv) {
        if (kcv == null || kcv.length == 0) {
            return false;
        }

        if (kcv.length == checkValue.length) {
            return Arrays.equals(kcv, checkValue);
        } else {
            int cmpLen = Math.min(checkValue.length, kcv.length);
            if (kcv.length < checkValue.length) {
                return Arrays.equals(kcv, Arrays.copyOfRange(checkValue, 0, cmpLen));
            } else {
                return Arrays.equals(checkValue, Arrays.copyOfRange(kcv, 0, cmpLen));
            }
        }
    }

    private void setPinpadSkin(){
        Intent intent = new Intent("com.landicorp.pinpad.pinentry.server.SET_SKIN");
        intent.putExtra("show_input", true);//设置是否显示密码框
        intent.putExtra("disorder", true);
        context.sendBroadcast(intent);
    }

    private abstract class PinpadExecutor {
        public boolean execute() {
            if (pinpad == null || !pinpad.open()) {
                return false;
            }
            boolean result = onExecute();
            result &= pinpad.close();
            return result;
        }
        protected abstract boolean onExecute();
    }

    private abstract class PinpadInvoker {
        public byte[] invoke() {
            if (pinpad == null || !pinpad.open()) {
                return null;
            }
            try {
                byte[] result = onInvoke();
                return result;
            } finally {
                pinpad.close();
            }
        }
        protected abstract byte[] onInvoke();
    }

    private void runOnUI(Runnable runnable) {
        uiHandler.post(runnable);
    }
}

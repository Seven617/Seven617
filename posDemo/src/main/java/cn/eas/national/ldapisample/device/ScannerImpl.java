package cn.eas.national.ldapisample.device;

import android.content.Context;

import com.landicorp.android.eptapi.device.BRScanner;
import com.landicorp.android.eptapi.device.Scanner;

/**
 * 该模块针对外接扫码枪设备。
 */

public abstract class ScannerImpl extends BaseDevice {

    private Context context;
    private BRScanner brScanner = BRScanner.getInstance();
    private BRScanner.OnScanListener brScanListener = new BRScanner.OnScanListener() {
        @Override
        public void onScanSuccess(String code) {
            displayInfo("scan result[" + code + "]");
        }

        @Override
        public void onScanFail(int error) {
            displayInfo("scan fail[error = " + error + "]");
        }

        @Override
        public void onCrash() {
            onDeviceServiceCrash();
        }
    };
    private Scanner scanner = new Scanner("");
    private Scanner.OnScanListener scanListener = new Scanner.OnScanListener() {
        @Override
        public void onScanSuccess(String code) {
            displayInfo("scan success[" + code + "]");
            runOnUi(new Runnable() {
                @Override
                public void run() {
                    close();
                }
            });
        }

        @Override
        public void onScanFail(int error) {
            displayInfo("scan fail[error = " + error + "]");
            runOnUi(new Runnable() {
                @Override
                public void run() {
                    close();
                }
            });
        }

        @Override
        public void onCrash() {
            onDeviceServiceCrash();
        }
    };

    public ScannerImpl(Context context) {
        super(context);
        this.context = context;
    }

    public void startBrScan() {
        brScanner.startScan(brScanListener);
    }

    public void stopBrScan() {
        brScanner.stopScan();
    }

    public int open() {
        scanner.setOnScanListener(scanListener);
        return scanner.open();
    }

    public void startScan() {
        scanner.scan();
    }

    private void close() {
        scanner.close();
    }

    public static String getDescription(int error) {
        switch (error) {
            case Scanner.ERROR_DEVICE_NOT_EXIST:
                return "设备不存在";
            case Scanner.ERROR_IS_ALEADY_OPEN:
                return "设备已经打开";
            case Scanner.ERROR_NONE:
                return "成功";
            case Scanner.ERROR_FAIL:
            default:
                return "其他错误";
        }
    }

    private void runOnUi(Runnable runnable) {
        uiHandler.post(runnable);
    }
}

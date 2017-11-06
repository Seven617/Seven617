package cn.eas.national.ldapisample.device;

import android.content.Context;

import com.landicorp.android.eptapi.device.InnerScanner;

/**
 * 可用于带内置扫码硬件的终端，如P990/P960等设备。
 */

public abstract class InnerScannerImpl extends BaseDevice {

    private Context context;
    private InnerScanner scanner;
    private boolean started = false;
    private InnerScanner.OnScanListener listener = new InnerScanner.OnScanListener() {
        @Override
        public void onScanSuccess(String result) {
            started = false;
            displayInfo("scan result = " + result);
        }

        @Override
        public void onScanFail(int error) {
            started = false;
            displayInfo("scan fail, error = " + getDescription(error));
        }

        @Override
        public void onCrash() {
            started = false;
            onDeviceServiceCrash();
        }

        private String getDescription(int error) {
            switch (error) {
                case ERROR_TIMEOUT:
                    return "timeout";
                case ERROR_FAIL:
                    return "scan fail";
                default:
                    return "unknown error";
            }
        }
    };

    public InnerScannerImpl(Context context) {
        super(context);
        this.context = context;
        scanner = InnerScanner.getInstance();
    }

    public void startScan(int timeout) {
        if (started) {
            return;
        }
        started = true;
        scanner.setOnScanListener(listener);
        scanner.start(timeout);
    }

    public void stopScan() {
        started = false;
        scanner.stopListen();
        scanner.stop();
    }
}

package cn.eas.national.ldapisample.presenter.impl;

import com.landicorp.android.eptapi.device.Printer;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.device.PrinterImpl;
import cn.eas.national.ldapisample.presenter.IPrinterPresenter;

/**
 * Created by Czl on 2017/7/27.
 */

public class PrinterPresenterImpl implements IPrinterPresenter {
    private BaseActivity view;
    private PrinterImpl printer;

    public PrinterPresenterImpl(BaseActivity activity) {
        this.view = activity;
        this.printer = new PrinterImpl(activity) {
            @Override
            protected void onDeviceServiceCrash() {
                PrinterPresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                PrinterPresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void start() {
        int ret = printer.getPrinterStatus();
        if (ret != Printer.ERROR_NONE) {
            view.displayInfo(printer.getDescribe(ret));
            return;
        }
        printer.init();
        if(!printer.addBitmap()) {
            view.displayInfo("add bitmap fail");
            return;
        }
        if (!printer.addText()) {
            view.displayInfo("add text fail");
            return;
        }
        if (!printer.addBarcode()) {
            view.displayInfo("add barcode fail");
            return;
        }
        if (!printer.addQRcode()) {
            view.displayInfo("add qrcode fail");
            return;
        }
        if (!printer.feedLine(3)) {
            view.displayInfo("feed line fail");
            return;
        }
        if (!printer.cutPage()) {
            view.displayInfo("cut page fail");
            return;
        }
        printer.startPrint();
    }
}

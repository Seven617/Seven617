package com.example.seven.myapplication.service;

import android.content.Context;

import com.example.seven.myapplication.device.PrinterImpl;
import com.example.seven.myapplication.model.QueryOrderData;
import com.example.seven.myapplication.model.TodayOrderData;
import com.example.seven.myapplication.util.DateStyle;
import com.example.seven.myapplication.util.DateUtil;
import com.landicorp.android.eptapi.device.Printer;

/**
 * Created by daichen on 2017/11/9.
 */

public class PrintPosService {
    public PrinterImpl printer ;

    public PrintPosService(Context context){
        printer= new PrinterImpl(context) {
            @Override
            protected void onDeviceServiceCrash() {

            }

            @Override
            protected void displayInfo(String info) {

            }
        };

    }


    public String printQueryOrder(QueryOrderData queryOrderData) {
        int ret = printer.getPrinterStatus();
        if (ret != Printer.ERROR_NONE) {
            return  printer.getDescribe(ret);
        }
        printer.init();
        if(!printer.addBitmap()) {
            return "add bitmap fail";

        }
        if (!printer.addText(queryOrderData.getMerName(),queryOrderData.getShopName(),queryOrderData.getAddress(),
                queryOrderData.getOperatorName(), DateUtil.TimestampToString(Long.valueOf(queryOrderData.getModifyDate()), DateStyle.YYYY_MM_DD_HH_MM_SS_EN),queryOrderData.getAmount(),queryOrderData.getPayTypeTxt())) {
            return "add text fail";
        }
        if (!printer.addBarcode(queryOrderData.getOrderSn())) {
            return "add barcode fail" ;
        }

        if (!printer.addText(queryOrderData.getOrderSn())) {
            return "add text fail";
        }
//        if (!printer.addQRcode()) {
//            showToast("add qrcode fail");
//            return;
//        }
        if (!printer.feedLine(3)) {
            return "feed line fail";

        }
        if (!printer.cutPage()) {
            return "cut page fail";
        }
        printer.startPrint();
        return null;
    }

    public String printTodayOrder(TodayOrderData todayOrderData) {
        int ret = printer.getPrinterStatus();
        if (ret != Printer.ERROR_NONE) {
            return  printer.getDescribe(ret);
        }
        printer.init();
        if(!printer.addBitmap()) {
            return "add bitmap fail";

        }
        if (!printer.addText(todayOrderData)){
            return "add text fail";
        }

//        if (!printer.addQRcode()) {
//            showToast("add qrcode fail");
//            return;
//        }
        if (!printer.feedLine(3)) {
            return "feed line fail";

        }
        if (!printer.cutPage()) {
            return "cut page fail";
        }
        printer.startPrint();
        return null;
    }

}

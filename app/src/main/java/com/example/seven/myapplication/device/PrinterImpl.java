package com.example.seven.myapplication.device;

import android.content.Context;

import com.example.seven.myapplication.Enums.OrderStatusEnum;
import com.example.seven.myapplication.model.TodayOrderData;
import com.landicorp.android.eptapi.device.Printer;
import com.landicorp.android.eptapi.device.Printer.Format;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.QrCode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import static com.landicorp.android.eptapi.device.Printer.Format.HZ_DOT24x24;
import static com.landicorp.android.eptapi.utils.QrCode.ECLEVEL_Q;

/**
 * 针对无打印机终端需使用外接打印机，如蓝牙打印机等。该示例针对内置打印机。
 */

public abstract class PrinterImpl extends BaseDevice {

    private com.landicorp.android.eptapi.device.Printer.Progress progress;
    private List<Printer.Step> stepList;

    public PrinterImpl(Context context) {
        super(context);
    }

    public int getPrinterStatus() {
        try {
            int status = Printer.getInstance().getStatus();
            return status;
        } catch (RequestException e) {
            e.printStackTrace();
        }
        return PrinterError.FAIL;
    }

    public void init() {
        stepList = new ArrayList<Printer.Step>();
    }

    public boolean addText(final String merName,final String shopName,final  String address,final String operatorName,final String modifyDate,final String amount,final String payTypeText) {
        if (stepList == null) {
            displayInfo("printer has not inited!");
            return false;
        }
        stepList.add(new com.landicorp.android.eptapi.device.Printer.Step() {
            @Override
            public void doPrint(com.landicorp.android.eptapi.device.Printer printer) throws Exception {
                printer.setAutoTrunc(false);
                Format format = new Format();
                format.setAscScale(Format.ASC_SC2x2);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC2x2);
                format.setHzSize(Format.HZ_DOT24x24);
                printer.setFormat(format);
                Printer.Alignment alignment = Printer.Alignment.CENTER;
                printer.printText(alignment,merName+"\n");//公司名
                //门店信息
                format.setAscScale(Format.ASC_SC1x1);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC1x1);
                format.setHzSize(HZ_DOT24x24);
                printer.setFormat(format);
                alignment = Printer.Alignment.LEFT;
                printer.printText(alignment, "店铺名:"+shopName+"\n");

                format.setAscScale(Format.ASC_SC1x1);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC1x1);
                format.setHzSize(HZ_DOT24x24);
                printer.setFormat(format);
                alignment = Printer.Alignment.LEFT;
                printer.printText(alignment, "地址:"+address+"\n");

                format.setAscScale(Format.ASC_SC1x1);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC1x1);
                format.setHzSize(HZ_DOT24x24);
                printer.setFormat(format);
                alignment = Printer.Alignment.LEFT;
                printer.printText(alignment, "操作员:"+operatorName+"\n");

                format.setAscScale(Format.ASC_SC1x1);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC1x1);
                format.setHzSize(HZ_DOT24x24);
                printer.setFormat(format);
                alignment = Printer.Alignment.LEFT;
                printer.printText(alignment, "时间:"+modifyDate+"\n");
                printer.printText(" \n");

                format.setAscScale(Format.ASC_SC1x1);
                format.setAscSize(Format.ASC_DOT32x12);
                format.setHzScale(Format.HZ_SC1x1);
                format.setHzSize(HZ_DOT24x24);
                printer.setFormat(format);
                alignment = Printer.Alignment.CENTER;
                if("退款".equals(payTypeText)){
                    printer.printText(alignment,"退款:"+"      "+amount+"元\n");
                }else {
                    printer.printText(alignment,"实付:"+"      "+amount+"元\n");
                }
                printer.printText(" \n");

//                printer.printText(alignment, amount+"元\n");
//                //付款金额
//                format.setAscScale(Format.ASC_SC2x2);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC2x2);
//                format.setHzSize(Format.HZ_DOT32x24);
//                printer.setFormat(format);
//                alignment = Printer.Alignment.LEFT;
//                printer.printText(alignment, "付款金额");
//                format.setAscScale(Format.ASC_SC2x2);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC2x2);
//                format.setHzSize(Format.HZ_DOT24x24);
//                printer.setFormat(format);
//                alignment = Printer.Alignment.RIGHT;
//                printer.printText(alignment, amount+"元\n");
//                //下面的广告
//
//                format.setAscScale(Format.ASC_SC1x1);
//                format.setAscSize(Format.ASC_DOT16x8);
//                format.setHzScale(Format.HZ_SC1x1);
//                format.setHzSize(Format.HZ_DOT16x16);
//                printer.setFormat(format);
//                 alignment = Printer.Alignment.LEFT;
//                printer.printText(alignment, "第二行\n");
//                printer.printText(alignment, "www.landicorp.com\n");
//                format.setAscScale(Format.ASC_SC1x1);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC1x1);
//                format.setHzSize(HZ_DOT24x24);
//                printer.setFormat(format);
//                alignment = Printer.Alignment.LEFT;
//                printer.printText(alignment, "第三行\n");
//                printer.printText(alignment, "www.landicorp.com\n");
//                format.setAscScale(Format.ASC_SC2x2);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC2x2);
//                format.setHzSize(Format.HZ_DOT24x24);
//                printer.setFormat(format);
//                alignment = Printer.Alignment.RIGHT;
//                printer.printText(alignment, "福建联迪\n");
//                printer.printText(alignment, "landicorp\n");
//                format.setAscScale(Format.ASC_SC1x1);
//                format.setAscSize(Format.ASC_DOT16x8);
//                format.setHzScale(Format.HZ_SC1x1);
//                format.setHzSize(Format.HZ_DOT16x16);
//                printer.printMixText(format, "有电子支付的");
//                format.setAscScale(Format.ASC_SC1x1);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC1x1);
//                format.setHzSize(HZ_DOT24x24);
//                printer.printMixText(format, "地方就有");
//                format.setAscScale(Format.ASC_SC2x2);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC2x2);
//                format.setHzSize(HZ_DOT24x24);
//                printer.printMixText(format, "快便付\n");
            }
        });
        return true;
    }

    public boolean addText(final TodayOrderData todayOrderData) {
        if (stepList == null) {
            displayInfo("printer has not inited!");
            return false;
        }
        stepList.add(new com.landicorp.android.eptapi.device.Printer.Step() {
            @Override
            public void doPrint(com.landicorp.android.eptapi.device.Printer printer) throws Exception {
                printer.setAutoTrunc(false);
                Format format = new Format();
                format.setAscScale(Format.ASC_SC2x2);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC2x2);
                format.setHzSize(Format.HZ_DOT24x24);
                printer.setFormat(format);
                Printer.Alignment alignment = Printer.Alignment.CENTER;
                printer.printText(alignment,"今日结算\n");

                format.setAscScale(Format.ASC_SC1x1);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC1x1);
                format.setHzSize(HZ_DOT24x24);
                printer.setFormat(format);
                alignment = Printer.Alignment.LEFT;
                printer.printText(alignment, "交易金额:"+todayOrderData.getSuccessAmount()+"元\n");

                format.setAscScale(Format.ASC_SC1x1);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC1x1);
                format.setHzSize(HZ_DOT24x24);
                printer.setFormat(format);
                alignment = Printer.Alignment.LEFT;
                printer.printText(alignment, "交易笔数:"+todayOrderData.getSuccessCount()+"\n");
                format.setAscScale(Format.ASC_SC1x1);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC1x1);
                format.setHzSize(HZ_DOT24x24);
                printer.setFormat(format);
                alignment = Printer.Alignment.LEFT;
                printer.printText(alignment, "退款金额:"+todayOrderData.getRefundAmount()+"元\n");
                format.setAscScale(Format.ASC_SC1x1);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC1x1);
                format.setHzSize(HZ_DOT24x24);
                printer.setFormat(format);
                alignment = Printer.Alignment.LEFT;
                printer.printText(alignment, "退款笔数:"+todayOrderData.getRefundCount()+"\n");



//                printer.printText(alignment, amount+"元\n");


//                //付款金额
//                format.setAscScale(Format.ASC_SC2x2);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC2x2);
//                format.setHzSize(Format.HZ_DOT32x24);
//                printer.setFormat(format);
//                alignment = Printer.Alignment.LEFT;
//                printer.printText(alignment, "付款金额");
//                format.setAscScale(Format.ASC_SC2x2);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC2x2);
//                format.setHzSize(Format.HZ_DOT24x24);
//                printer.setFormat(format);
//                alignment = Printer.Alignment.RIGHT;
//                printer.printText(alignment, amount+"元\n");
//                //下面的广告
//
//                format.setAscScale(Format.ASC_SC1x1);
//                format.setAscSize(Format.ASC_DOT16x8);
//                format.setHzScale(Format.HZ_SC1x1);
//                format.setHzSize(Format.HZ_DOT16x16);
//                printer.setFormat(format);
//                 alignment = Printer.Alignment.LEFT;
//                printer.printText(alignment, "第二行\n");
//                printer.printText(alignment, "www.landicorp.com\n");
//                format.setAscScale(Format.ASC_SC1x1);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC1x1);
//                format.setHzSize(HZ_DOT24x24);
//                printer.setFormat(format);
//                alignment = Printer.Alignment.LEFT;
//                printer.printText(alignment, "第三行\n");
//                printer.printText(alignment, "www.landicorp.com\n");
//                format.setAscScale(Format.ASC_SC2x2);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC2x2);
//                format.setHzSize(Format.HZ_DOT24x24);
//                printer.setFormat(format);
//                alignment = Printer.Alignment.RIGHT;
//                printer.printText(alignment, "福建联迪\n");
//                printer.printText(alignment, "landicorp\n");
//                format.setAscScale(Format.ASC_SC1x1);
//                format.setAscSize(Format.ASC_DOT16x8);
//                format.setHzScale(Format.HZ_SC1x1);
//                format.setHzSize(Format.HZ_DOT16x16);
                printer.printMixText(format, "有电子支付的");
                format.setAscScale(Format.ASC_SC1x1);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC1x1);
                format.setHzSize(HZ_DOT24x24);
                printer.printMixText(format, "地方就有");
                format.setAscScale(Format.ASC_SC2x2);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC2x2);
                format.setHzSize(HZ_DOT24x24);
                printer.printMixText(format, "快便付\n");
                printer.printText("\n");
            }
        });
        return true;
    }








    public boolean addText(final String orderNo) {
        if (stepList == null) {
            displayInfo("printer has not inited!");
            return false;
        }
        stepList.add(new com.landicorp.android.eptapi.device.Printer.Step() {
            @Override
            public void doPrint(com.landicorp.android.eptapi.device.Printer printer) throws Exception {
                printer.setAutoTrunc(false);
                Format format = new Format();

                Printer.Alignment alignment = Printer.Alignment.CENTER;
                format.setAscScale(Format.ASC_SC1x1);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC1x1);
                format.setHzSize(HZ_DOT24x24);
                printer.setFormat(format);
                printer.printText(alignment, orderNo+"\n");
                printer.printText("\n");

//                printer.printText(alignment, amount+"元\n");


//                //付款金额
//                format.setAscScale(Format.ASC_SC2x2);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC2x2);
//                format.setHzSize(Format.HZ_DOT32x24);
//                printer.setFormat(format);
//                alignment = Printer.Alignment.LEFT;
//                printer.printText(alignment, "付款金额");
//                format.setAscScale(Format.ASC_SC2x2);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC2x2);
//                format.setHzSize(Format.HZ_DOT24x24);
//                printer.setFormat(format);
//                alignment = Printer.Alignment.RIGHT;
//                printer.printText(alignment, amount+"元\n");
//                //下面的广告
//
//                format.setAscScale(Format.ASC_SC1x1);
//                format.setAscSize(Format.ASC_DOT16x8);
//                format.setHzScale(Format.HZ_SC1x1);
//                format.setHzSize(Format.HZ_DOT16x16);
//                printer.setFormat(format);
//                 alignment = Printer.Alignment.LEFT;
//                printer.printText(alignment, "第二行\n");
//                printer.printText(alignment, "www.landicorp.com\n");
//                format.setAscScale(Format.ASC_SC1x1);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC1x1);
//                format.setHzSize(HZ_DOT24x24);
//                printer.setFormat(format);
//                alignment = Printer.Alignment.LEFT;
//                printer.printText(alignment, "第三行\n");
//                printer.printText(alignment, "www.landicorp.com\n");
//                format.setAscScale(Format.ASC_SC2x2);
//                format.setAscSize(Format.ASC_DOT24x12);
//                format.setHzScale(Format.HZ_SC2x2);
//                format.setHzSize(Format.HZ_DOT24x24);
//                printer.setFormat(format);
//                alignment = Printer.Alignment.RIGHT;
//                printer.printText(alignment, "福建联迪\n");
//                printer.printText(alignment, "landicorp\n");
//                format.setAscScale(Format.ASC_SC1x1);
//                format.setAscSize(Format.ASC_DOT16x8);
//                format.setHzScale(Format.HZ_SC1x1);
//                format.setHzSize(Format.HZ_DOT16x16);
                printer.printMixText(format, "有电子支付的");
                format.setAscScale(Format.ASC_SC1x1);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC1x1);
                format.setHzSize(HZ_DOT24x24);
                printer.printMixText(format, "地方就有");
                format.setAscScale(Format.ASC_SC2x2);
                format.setAscSize(Format.ASC_DOT24x12);
                format.setHzScale(Format.HZ_SC2x2);
                format.setHzSize(HZ_DOT24x24);
                printer.printMixText(format, "快便付\n");
                printer.printText("\n");
            }
        });
        return true;
    }

    public boolean addBitmap() {
        if (stepList == null) {
            displayInfo("printer has not inited!");
            return false;
        }
        stepList.add(new com.landicorp.android.eptapi.device.Printer.Step() {
            @Override
            public void doPrint(com.landicorp.android.eptapi.device.Printer printer) throws Exception {
                InputStream inputStream = context.getAssets().open("pay.bmp");
                printer.printImage(com.landicorp.android.eptapi.device.Printer.Alignment.LEFT, inputStream);
            }
        });
        return true;
    }

    public boolean addBarcode(final String orderNo) {
        if (stepList == null) {
            displayInfo("printer has not inited!");
            return false;
        }
        stepList.add(new com.landicorp.android.eptapi.device.Printer.Step() {
            @Override
            public void doPrint(com.landicorp.android.eptapi.device.Printer printer) throws Exception {
                Printer.Alignment alignment =  Printer.Alignment.CENTER;
                printer.printBarCode(alignment,orderNo);
            }
        });
        return true;
    }

    public boolean addQRcode() {
        if (stepList == null) {
            displayInfo("printer has not inited!");
            return false;
        }
        stepList.add(new com.landicorp.android.eptapi.device.Printer.Step() {
            @Override
            public void doPrint(com.landicorp.android.eptapi.device.Printer printer) throws Exception {
                printer.printQrCode(com.landicorp.android.eptapi.device.Printer.Alignment.CENTER,
                        new QrCode("福建联迪商用设备有限公司", ECLEVEL_Q),
                        200);
            }
        });
        return true;
    }

    public boolean feedLine(final int line) {
        if (stepList == null) {
            displayInfo("printer has not inited!");
            return false;
        }
        stepList.add(new com.landicorp.android.eptapi.device.Printer.Step() {
            @Override
            public void doPrint(com.landicorp.android.eptapi.device.Printer printer) throws Exception {
                printer.feedLine(line);
            }
        });
        return true;
    }

    public boolean cutPage() {
        if (stepList == null) {
            displayInfo("printer has not inited!");
            return false;
        }
        stepList.add(new com.landicorp.android.eptapi.device.Printer.Step() {
            @Override
            public void doPrint(com.landicorp.android.eptapi.device.Printer printer) throws Exception {
                printer.cutPaper();
            }
        });
        return true;
    }

    public void startPrint() {
        if (stepList == null) {
            displayInfo("printer has not inited!");
            return;
        }
        progress = new com.landicorp.android.eptapi.device.Printer.Progress() {
            @Override
            public void doPrint(com.landicorp.android.eptapi.device.Printer printer) throws Exception {
                // never call
            }

            @Override
            public void onFinish(int error) {
                stepList.clear();
                if (error == com.landicorp.android.eptapi.device.Printer.ERROR_NONE) {
                    displayInfo("print success");
                } else {
                    String errorDes = getDescribe(error);
                    displayInfo("打印出错：" + errorDes);
                }
            }

            @Override
            public void onCrash() {
                stepList.clear();
                onDeviceServiceCrash();
            }
        };
        for (com.landicorp.android.eptapi.device.Printer.Step step : stepList) {
            progress.addStep(step);
        }
        try {
            progress.start();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    public String getDescribe(int error) {
        switch (error) {
        case com.landicorp.android.eptapi.device.Printer.ERROR_BMBLACK:
            return "黑标探测器检测到黑色信号";
        case com.landicorp.android.eptapi.device.Printer.ERROR_BUFOVERFLOW:
            return "缓冲模式下所操作的位置超出范围";
        case com.landicorp.android.eptapi.device.Printer.ERROR_BUSY:
            return "打印机处于忙状态";
        case com.landicorp.android.eptapi.device.Printer.ERROR_COMMERR:
            return "手座机状态正常，但通讯失败 (520针打特有返回值)";
        case com.landicorp.android.eptapi.device.Printer.ERROR_CUTPOSITIONERR:
            return "切纸刀不在原位 (自助热敏打印机特有返回值)";
        case com.landicorp.android.eptapi.device.Printer.ERROR_HARDERR:
            return "硬件错误";
        case com.landicorp.android.eptapi.device.Printer.ERROR_LIFTHEAD:
            return "打印头抬起 (自助热敏打印机特有返回值)";
        case com.landicorp.android.eptapi.device.Printer.ERROR_LOWTEMP:
            return "低温保护或AD出错 (自助热敏打印机特有返回值)";
        case com.landicorp.android.eptapi.device.Printer.ERROR_LOWVOL:
            return "低压保护";
        case com.landicorp.android.eptapi.device.Printer.ERROR_MOTORERR:
            return "打印机芯故障(过快或者过慢)";
        case com.landicorp.android.eptapi.device.Printer.ERROR_NOBM:
            return "没有找到黑标";
        case com.landicorp.android.eptapi.device.Printer.ERROR_NONE:
            return "正常状态，无错误";
        case com.landicorp.android.eptapi.device.Printer.ERROR_OVERHEAT:
            return "打印头过热";
        case com.landicorp.android.eptapi.device.Printer.ERROR_PAPERENDED:
            return "缺纸，不能打印";
        case com.landicorp.android.eptapi.device.Printer.ERROR_PAPERENDING:
            return "纸张将要用尽，还允许打印 (单步进针打特有返回值)";
        case com.landicorp.android.eptapi.device.Printer.ERROR_PAPERJAM:
            return "卡纸";
        case com.landicorp.android.eptapi.device.Printer.ERROR_PENOFOUND:
            return "自动定位没有找到对齐位置,纸张回到原来位置";
        case com.landicorp.android.eptapi.device.Printer.ERROR_WORKON:
            return "打印机电源处于打开状态";
        default:
            return "未知错误";
        }
    }
}

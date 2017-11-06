package cn.eas.national.ldapisample.data;

/**
 * Created by Czl on 2017/7/27.
 */

public interface Constants {
    interface Device {
        int DEVICE_MODULE_MAG_CARD = 0x00; // 磁条卡读卡器
        int DEVICE_MODULE_IC_CPU_CARD = 0x01; // 接触式cpu卡读卡器
        int DEVICE_MODULE_SYNC_CARD = 0x02; // 同步卡读卡器
        int DEVICE_MODULE_PSAM_CARD = 0x03; // psam卡读卡器
        int DEVICE_MODULE_RF_CPU_CARD = 0x04; // 非接触式cpu卡读卡器
        int DEVICE_MODULE_RF_M1_CARD = 0x05; // 非接触式m1卡读卡器
        int DEVICE_MODULE_ID_CARD = 0x06; // 二代证读卡器
        int DEVICE_MODULE_PRINTER = 0x07; // 打印机
        int DEVICE_MODULE_PINPAD = 0x08; // 密码键盘
        int DEVICE_MODULE_SERIALPORT = 0x09; // 串口
        int DEVICE_MODULE_CAMERA_SCANNER = 0x0A; // 摄像头扫码器
        int DEVICE_MODULE_INNERSCANNER = 0x0B; // 内置扫码器
        int DEVICE_MODULE_LED = 0x0C; // led灯
        int DEVICE_MODULE_BEEPER = 0x0D; // 蜂鸣器
        int DEVICE_MODULE_CASHBOX = 0x0E; // 钱箱
        int DEVICE_MODULE_MODEM = 0x0F; // modem
        int DEVICE_MODULE_C10_SUBSCREEN_DEVICE = 0x10; // C10客屏操作接口
        int DEVICE_MODULE_SCANNER = 0x11; // 外接扫码枪
        int DEVICE_MODULE_ALGORITHM = 0x12; // 算法
        int DEVICE_MODULE_SYSTEM = 0x13; // 系统接口
    }

    interface Pinpad {
        String DEVICE_INNER = "IPP";
        String DEVICE_EXTERNAL = "COM_EPP";

        int REGION_ID = 0;
        int KAP_ID = 0;
        
        int MAIN_KEY_INDEX = com.landicorp.android.eptapi.pinpad.Pinpad.KEYOFFSET_MAINKEY;
        int MAC_KEY_INDEX = com.landicorp.android.eptapi.pinpad.Pinpad.KEYOFFSET_MACKEY;
        int PIN_KEY_INDEX = com.landicorp.android.eptapi.pinpad.Pinpad.KEYOFFSET_PINKEY;
        int TD_KEY_INDEX = com.landicorp.android.eptapi.pinpad.Pinpad.KEYOFFSET_TDKEY;
        int ENC_DEC_KEY_INDEX = TD_KEY_INDEX + 1;

        int KEYTYPE_MAIN_KEY = com.landicorp.android.eptapi.pinpad.Pinpad.KEYTYPE_MAIN_KEY;
        int KEYTYPE_MAC_KEY = com.landicorp.android.eptapi.pinpad.Pinpad.KEYTYPE_MAC_KEY;
        int KEYTYPE_PIN_KEY = com.landicorp.android.eptapi.pinpad.Pinpad.KEYTYPE_PIN_KEY;
        int KEYTYPE_TD_KEY = com.landicorp.android.eptapi.pinpad.Pinpad.KEYTYPE_TD_KEY;
        int KEYTYPE_ENC_DEC_KEY = com.landicorp.android.eptapi.pinpad.Pinpad.KEYTYPE_ENC_DEC_KEY;
    }

    interface Scanner {
        int CAMERA_FRONT = 0x00;
        int CAMERA_BACK = 0x01;
    }

    interface SerialPort {
        String DEVICE_USBD = "USBD";
    }

    interface RFCard {
        String DEVICE_INNER = "inner";
        String DEVICE_EXTERNAL = "external";

        String DRIVER_NAME_PRO = "PRO";
        String DRIVER_NAME_S50 = "S50";
        String DRIVER_NAME_S70 = "S70";
        String DRIVER_NAME_CPU = "CPU";
    }

    interface Led {
        String DEVICE_INNER = "inner";
        String DEVICE_EXTERNAL = "external";
    }

    interface DualScreen {
        /** 客屏不可点击 */
        int SCREEN_UNTOUCHABLE = 0;
        /** 客屏可点击 */
        int SCREEN_TOUCHABLE = 1;
        /** 客屏按钮已启用 */
        int BUTTON_ENABLE = 1;
        /** 客屏按钮已禁用 */
        int BUTTON_DISABLE = 0;
        /** 客屏没有焦点 */
        int SCREEN_UNFOCUS = 0;
        /** 客屏有聚焦 */
        int SCREEN_FOCUS = 1;
    }
}

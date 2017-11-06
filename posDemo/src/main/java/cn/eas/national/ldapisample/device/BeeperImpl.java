package cn.eas.national.ldapisample.device;

import android.content.Context;

import com.landicorp.android.eptapi.device.Beeper;

/**
 * 支持具备蜂鸣器设备的终端，如A8/P990/W280PV3，C10/P960/W280P/W280PV2不支持该模块。
 */

public abstract class BeeperImpl extends BaseDevice {

    private Context context;

    public BeeperImpl(Context context) {
        super(context);
        this.context = context;
    }

    public void startBeep(int timeout) {
        Beeper.startBeep(timeout);
    }

    public void stopBeep() {
        Beeper.stopBeep();
    }
}

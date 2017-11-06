package cn.eas.national.ldapisample.presenter.impl;

import android.os.Handler;
import android.os.Looper;

import cn.eas.national.ldapisample.activity.BaseActivity;
import cn.eas.national.ldapisample.device.BeeperImpl;
import cn.eas.national.ldapisample.presenter.IBeeperPresenter;

/**
 * Created by Czl on 2017/7/23.
 */

public class BeeperPresenterImpl implements IBeeperPresenter{
    private BaseActivity view;
    private int[] beepList = new int[] { 0x01, 0x01, 0x01, 0x02 };
    private int current = 0;
    private Handler uiHandler = new Handler(Looper.getMainLooper());
    private Runnable beepRunnable = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };
    private BeeperImpl beeper;
    private boolean hasStarted = false;

    public BeeperPresenterImpl(BaseActivity activity) {
        this.view = activity;
        this.beeper = new BeeperImpl(activity) {
            @Override
            protected void onDeviceServiceCrash() {
                BeeperPresenterImpl.this.view.displayInfo("device service crash");
            }

            @Override
            protected void displayInfo(String info) {
                BeeperPresenterImpl.this.view.displayInfo(info);
            }
        };
    }

    @Override
    public void startBeep() {
        if (hasStarted) {
            stopBeep();
            hasStarted = false;
        }
        start();
    }

    private void start() {
        hasStarted = true;
        if (current == beepList.length) {
            current = 0;
        }
        int value = beepList[current];
        current++;
        if (value == 0x01) {
            beeper.startBeep(200);
            uiHandler.postDelayed(beepRunnable, 500);
        } else {
            beeper.startBeep(500);
            uiHandler.postDelayed(beepRunnable, 500);
        }
    }

    @Override
    public void stopBeep() {
        beeper.stopBeep();
        uiHandler.removeCallbacks(beepRunnable);
    }
}

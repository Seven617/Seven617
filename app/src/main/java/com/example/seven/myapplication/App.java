package com.example.seven.myapplication;

import android.app.Application;

import com.example.seven.myapplication.network.NetStateReceiver;
import com.jady.retrofitclient.HttpManager;


/**
 * Created by Seven on 2017-10-29
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /*开启网络广播监听*/
        NetStateReceiver.registerNetworkStateReceiver(this);
        HttpManager.init(this, "http://192.168.0.105:8580/");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        NetStateReceiver.unRegisterNetworkStateReceiver(this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}

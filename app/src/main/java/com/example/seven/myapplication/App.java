package com.example.seven.myapplication;

import android.app.Application;

import com.example.seven.myapplication.network.NetStateReceiver;
import com.jady.retrofitclient.HttpManager;


/**
 * Created by Seven on 2017-10-29
 */

public class App extends Application {

//    private static App app;
//
//    public static  App getApp(){
//        if(null == app){
//            app =new App();
//
//        }
//        return app;
//    }
//
//    public static void setApp(App app) {
//        App.app = app;
//    }

    private String userName;
    private String userTypeName;
    private String userlastLoginTime;
    private String usershopSn;
    private String username;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getUserlastLoginTime() {
        return userlastLoginTime;
    }

    public void setUserlastLoginTime(String userlastLoginTime) {
        this.userlastLoginTime = userlastLoginTime;
    }

    public String getUsershopSn() {
        return usershopSn;
    }

    public void setUsershopSn(String usershopSn) {
        this.usershopSn = usershopSn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        /*开启网络广播监听*/
        NetStateReceiver.registerNetworkStateReceiver(this);
        HttpManager.init(this, "http://192.168.9.145:8580/");
        setUserName("");
        setUserTypeName("");
        setUserlastLoginTime("");
        setUsershopSn("");
        setUsername("");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        NetStateReceiver.unRegisterNetworkStateReceiver(this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}

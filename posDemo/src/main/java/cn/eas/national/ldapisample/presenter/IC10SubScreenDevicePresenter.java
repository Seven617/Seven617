package cn.eas.national.ldapisample.presenter;

/**
 * Created by Czl on 2017/7/23.
 */

public interface IC10SubScreenDevicePresenter {
    void connect();
    void startAppOnSubScreen();
    void startActivityOnSubScreen();
    void setSubScreenApp();
    void removeSubScreenApp();
    void getSubScreenInfo();
    void sendDataToSubScreen();
    void disconnect();
}

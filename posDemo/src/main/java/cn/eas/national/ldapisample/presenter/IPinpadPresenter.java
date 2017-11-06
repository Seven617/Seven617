package cn.eas.national.ldapisample.presenter;

/**
 * Created by Czl on 2017/7/23.
 */

public interface IPinpadPresenter {
    void loadKey();
    void startOnlinePinEntry(String cardNo);
    void calcMac(byte[] data);
    void encryptMagTrack(byte[] data);
    void encryptData(byte[] data);
    void decryptData(byte[] data);
}

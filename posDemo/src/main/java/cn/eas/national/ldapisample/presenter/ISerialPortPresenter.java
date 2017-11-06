package cn.eas.national.ldapisample.presenter;

/**
 * Created by Czl on 2017/7/23.
 */

public interface ISerialPortPresenter {
    int init(int baud, int parity, int dataBits);
    int open();
    int read(byte[] buffer);
    int write(byte[] data);
    int close();
}

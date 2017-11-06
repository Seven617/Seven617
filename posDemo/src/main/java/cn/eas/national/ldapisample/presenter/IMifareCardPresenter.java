package cn.eas.national.ldapisample.presenter;

import com.landicorp.android.eptapi.utils.BytesBuffer;

/**
 * Created by Czl on 2017/7/23.
 */

public interface IMifareCardPresenter {
    void cardPower();
    void cardHalt();
    void authBlock(int blockNo, int keyType, byte[] key);
    void read(int blockNo, BytesBuffer buffer);
    void write(int blockNo, byte[] data);
    void addValue(int blockNo, int value);
    void reduceValue(int blockNo, int value);
    void exist();
}

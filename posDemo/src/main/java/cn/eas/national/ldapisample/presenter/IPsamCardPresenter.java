package cn.eas.national.ldapisample.presenter;

/**
 * Created by Czl on 2017/7/23.
 */

public interface IPsamCardPresenter {
    void cardPower(int slot);
    void cardHalt();
    void exchangeApdu();
    void exist();
}

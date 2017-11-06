package cn.eas.national.ldapisample.presenter.impl;

import cn.eas.national.ldapisample.activity.MagCardActivity;
import cn.eas.national.ldapisample.device.MagCardReaderImpl;
import cn.eas.national.ldapisample.presenter.IMagCardPresenter;

/**
 * Created by Czl on 2017/7/23.
 */

public class MagCardPresenterImpl implements IMagCardPresenter{
    private MagCardActivity view;
    private MagCardReaderImpl magCardReader;

    public MagCardPresenterImpl(MagCardActivity activity) {
        this.view = activity;
        this.view = view;
        this.magCardReader = new MagCardReaderImpl(activity) {
            @Override
            protected void displayMagCardInfo(String cardInfo) {
                MagCardPresenterImpl.this.view.displayInfo(cardInfo);
                MagCardPresenterImpl.this.view.finshSwipeCard();
            }

            @Override
            protected void onDeviceServiceCrash() {
                MagCardPresenterImpl.this.view.displayInfo("device service crash");
                MagCardPresenterImpl.this.view.finshSwipeCard();
            }

            @Override
            protected void displayInfo(String info) {
                MagCardPresenterImpl.this.view.displayInfo(info);
                MagCardPresenterImpl.this.view.finshSwipeCard();
            }
        };
    }

    @Override
    public void searchCard() {
        magCardReader.searchCard();
    }

    @Override
    public void stopSearch() {
        magCardReader.stopSearch();
    }
}
